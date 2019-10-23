package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.FundPipeline;
import com.royal.entity.PaySwitch;
import com.royal.entity.UserAmount;
import com.royal.entity.enums.FundPipelineEnum;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;
import com.royal.service.IFundPipelineService;
import com.royal.service.IPaySwitchService;
import com.royal.service.IUserAmountService;
import com.royal.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.AmountRecord;
import com.royal.mapper.AmountRecordMapper;
import com.royal.service.IAmountRecordService;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 描述：资金记录 服务实现层
 *
 * @author Royal
 * @date 2019年03月05日 13:28:09
 */
@Service
@Transactional
public class AmountRecordServiceImpl extends BaseServiceImpl<AmountRecord> implements IAmountRecordService {
    private static final Logger log = LoggerFactory.getLogger(AmountRecordServiceImpl.class);
    private AmountRecordMapper amountRecordMapper;
    @Autowired
    private IUserAmountService userAmountService;
    @Autowired
    private IPaySwitchService paySwitchService;
    @Autowired
    private IFundPipelineService fundPipelineService;

    @Resource
    public void setBaseMapper(AmountRecordMapper mapper) {
        super.setBaseMapper(mapper);
        this.amountRecordMapper = mapper;
    }

    @Override
    public PageInfo<AmountRecord> getPageByAmount(AmountRecord vo, PageData pd) {
        Page<Object> ph = PageHelper.startPage(pd.getPage(), pd.getPageSize());
        Example ex = new Example(vo.getClass());
        ex.setOrderByClause("order_time DESC");
        ex.createCriteria().andEqualTo("amountType", vo.getAmountType()).andEqualTo("loginName", vo.getLoginName());
        PageInfo<AmountRecord> list = new PageInfo<AmountRecord>(amountRecordMapper.selectByExample(ex));
        return list;
    }

    /**
     * 清除60分钟前的订单
     *
     * @param date
     */
    @Override
    public void updatePayStatus(String date) {
        Example ex = new Example(AmountRecord.class);
        ex.createCriteria().andEqualTo("payStatus", "WAIT_BUYER_PAY").andEqualTo("amountType", "PAY")
                .andLessThanOrEqualTo("orderTime", date);
        AmountRecord ar = new AmountRecord();
        ar.setPayStatus("TRADE_CLOSED");
        amountRecordMapper.updateByExampleSelective(ar, ex);
    }

    /**
     * 查询30分钟前的订单
     */
    @Override
    public void selectPayStatus() {
        Example ex = new Example(AmountRecord.class);
        ex.createCriteria().andEqualTo("payStatus", "WAIT_BUYER_PAY").andEqualTo("amountType", "PAY")
                .andEqualTo("payNotifyStatus", 2)
                .andLessThanOrEqualTo("orderTime", DateUtils.getFormatDateTime(DateUtils.getDateBeforeOrAfterMinute(new Date(), -30)));
        List<AmountRecord> amountRecordList = amountRecordMapper.selectByExample(ex);
        if (amountRecordList != null && amountRecordList.size() > 0) {
            PayOrderUtit.putList(amountRecordList);
            Map<String, String> map = new HashMap<String, String>();
            List<PaySwitch> list = paySwitchService.getAll();
            for (PaySwitch paySwitch : list) {
                map.put(paySwitch.getPayCode(), paySwitch.getDitch());
            }
            for (AmountRecord amountRecord : amountRecordList) {
                String ditch = map.get(amountRecord.getPayWay());
                try {
                    if ("中付".equals(ditch)) {

                    } else if ("杭州尚实".equals(ditch)) {
                        hzshSelectOrder(amountRecord);
                    } else {
                        log.error("充值订单查询未找到对应渠道：" + amountRecord.toString());
                    }
                } catch (Exception e) {
                    log.error("查询订单出错" + amountRecord.toString(), e);
                }
            }

        }
    }

    @Override
    public void paySuccessRecord(AmountRecord amountRecord) {
        UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), amountRecord.getLoginName());
        userAmount.setBalance(amountRecord.getMoney().add(userAmount.getBalance()));
        userAmount.setRechargeAmount(amountRecord.getMoney().add(userAmount.getRechargeAmount()));
        userAmountService.update(userAmount);
        FundPipeline fundPipeline = new FundPipeline(FundPipelineEnum.PAY.getKey(), amountRecord.getMoney(), userAmount.getBalance(), userAmount.getLoginName(), amountRecord.getId(), FundPipelineEnum.INCOME.getKey());
        fundPipelineService.add(fundPipeline);
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mer_id", Constants.SSPayConstants.MER_ID);
        params.put("mer_trade_no", "0ea98649-9956-4d92-b12d-fe2c35287685");
        params.put("out_trade_no", "445220190430163203931949955499");
        params.put("sign", PayUtils.sign(params));
        System.out.println(HttpUtils.doPost(Constants.SSPayConstants.SELECT_URL_ADDRESS, JSONUtils.toJSONString(params), null));
    }

    //杭州尚实查询订单--------------------------------------------------------------------------------------------
    private void hzshSelectOrder(AmountRecord amountRecord) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mer_id", Constants.SSPayConstants.MER_ID);
        params.put("mer_trade_no", amountRecord.getId());
        params.put("out_trade_no", amountRecord.getPayNo());
        params.put("sign", PayUtils.sign(params));
        String json = HttpUtils.doPost(Constants.SSPayConstants.SELECT_URL_ADDRESS, JSONUtils.toJSONString(params), null);
        Map<String, Object> response = JSONUtils.toHashMap(json);
        if (!response.containsKey("sign")) {
            return;
        }
        String responseSign = response.get("sign").toString();
        response.remove("sign");
        String myResponseSign = PayUtils.sign(response);
        if (!responseSign.equals(myResponseSign)) {
            return;
        }
        if (!response.containsKey("totalAmount") || !response.containsKey("billStatus") || !response.containsKey("err_code")) {
            return;
        }
        if (!"SUCCESS".equals(response.get("err_code").toString())) {
            return;
        }
        String billStatus = response.get("billStatus").toString();
        if ("NEW_ORDER".equals(billStatus) || "WAIT_BUYER_PAY".equals(billStatus)) {
            return;
        }

        Integer titalAmount = Integer.getInteger(response.get("totalAmount").toString());
        //如果金额不匹配，则重新计算充值金额
        if (amountRecord.getSponsorMoney().compareTo(new BigDecimal(titalAmount.toString()).divide(new BigDecimal(100))) != 0) {
            BigDecimal price = amountRecord.getExchangeRate();
            amountRecord.setMoney(new BigDecimal(titalAmount.toString()).divide(new BigDecimal(100)).divide(price, 2,
                    BigDecimal.ROUND_HALF_UP));
        }
        amountRecord.setRmbMoney(new BigDecimal(titalAmount.toString()).divide(new BigDecimal(100)));
        amountRecord.setPayNotifyStatus(1);
        amountRecord.setPayStatus(billStatus);
        amountRecord.setPayTime(amountRecord.getOrderTime());
        this.update(amountRecord);
        if ("TRADE_SUCCESS".equals(amountRecord.getPayStatus())) {
            paySuccessRecord(amountRecord);
        }
    }

    //中付查询订单--------------------------------------------------------------------------------------------
    private void zfSelectOrder(AmountRecord amountRecord) {
        try {
            String json = Tools.unicodeToString(ZFPayUtil.selectOrder("e8250cb4fb2347c4a18c412cb219fb03"));
            Map<String, Object> response = JSONUtils.toHashMap(json);
            if (!response.get("status").toString().equals("1")) {
                return;
            }
            //如果金额不匹配，则重新计算充值金额
            if (amountRecord.getSponsorMoney().compareTo(new BigDecimal(response.get("total_fee").toString())) != 0) {
                BigDecimal price = amountRecord.getExchangeRate();
                amountRecord.setMoney(new BigDecimal(response.get("total_fee").toString()).divide(price, 2, BigDecimal.ROUND_HALF_UP));
            }
            amountRecord.setRmbMoney(new BigDecimal(response.get("total_fee").toString()));
            amountRecord.setPayNotifyStatus(1);
            amountRecord.setPayStatus("TRADE_SUCCESS");
            amountRecord.setPayTime(DateUtils.getCurrDateTimeStr());
            amountRecord.setPayNo(response.get("sdpayno").toString());
            this.update(amountRecord);
            paySuccessRecord(amountRecord);
        } catch (Exception e) {
            log.error("中付查询订单失败：", e);
        }
    }

}