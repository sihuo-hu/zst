package com.royal.controller;

import com.royal.entity.*;
import com.royal.entity.enums.FundPipelineEnum;
import com.royal.entity.enums.PayStatusEnum;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.*;
import com.royal.service.*;
import com.royal.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：资金记录控制层
 *
 * @author Royal
 * @date 2019年03月05日 13:28:09
 */
@Controller
@RequestMapping("/amountRecord")
public class AmountRecordController extends BaseController {

    @Autowired
    private IAmountRecordService amountRecordService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAmountService userAmountService;
    @Autowired
    private IFundPipelineService fundPipelineService;
    @Autowired
    private IPaySwitchService paySwitchService;

    /**
     * 描述：查询充值渠道列表
     */
    @RequestMapping(value = "/getPayType")
    @ResponseBody
    public Result getPayType() {
        List<PaySwitch> list = paySwitchService.getAll();
        return new Result(list);
    }


    /**
     * 描述：查询充值及提现记录
     * amountType ：PAY-支付 WITHDRAWAL-提现
     *
     * @param vo
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Result findById(AmountRecord vo) throws Exception {
        try {
            String loginName = JwtUtil.getUser(getRequest());
            vo.setLoginName(loginName);
            PageData pd = this.getPageData();
            PageInfo<AmountRecord> list = amountRecordService.getPageByAmount(vo, pd);
            if (list.getList() != null && list.getList().size() > 0) {
                User user = userService.findByLoginName(new User(), loginName);
                for (AmountRecord amountRecord : list.getList()) {
                    amountRecord.setBankAddress(user.getBankAddress());
                    amountRecord.setBankCard(user.getBankCard());
                    amountRecord.setBankOfDeposit(user.getBankOfDeposit());
                    amountRecord.setBranch(user.getBranch());
                    amountRecord.setPayStatusStr(PayStatusEnum.getValueByKey(amountRecord.getPayStatus()));
                }
            }
            return new Result(list);
        } catch (Exception e) {
            logger.error("UserAmount出异常了", e);
            return new Result(e);
        }

    }


    /**
     * 描述：提现
     * 提现流程：
     * 1.用户发起提现
     * 2.检查是否实名
     * 3.检查是否绑定银行卡
     * 4.收取提现金额1%手续费（最低1美元，10美元封顶）
     * 5.1～2工作日到账（转出账号可以配置）
     * 6.10美元起
     *
     * @param amountRecord 资金记录id
     */
    @RequestMapping(value = "/withdraw")
    @ResponseBody
    public Result withdraw(AmountRecord amountRecord) {
        try {
            User user = userService.findByLoginName(new User(), JwtUtil.getUser(getRequest()));
            //目前只需要提交了审核资料就行
            if (user.getAuditStatus().equals("DONT_SUBMIT")) {
                return new Result(ResultEnum.DONT_SUBMIT);
//            } else if (user.getAuditStatus ().equals ("NO_AUDIT")) {
//                return new Result (ResultEnum.NO_AUDIT);
//            } else if (user.getAuditStatus ().equals ("REJECTED")) {
//                return new Result (ResultEnum.REJECTED);
//            } else if (user.getAuditStatus ().equals ("VERIFIED")) {
            } else {
                if (Tools.isEmpty(user.getBankCard())) {
                    return new Result(ResultEnum.NOT_BANK_CARD);
                }
                if (amountRecord.getMoney().compareTo(new BigDecimal("10")) < 0) {
                    return new Result(ResultEnum.MIN_MONEY_ERROR);
                }
                UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), JwtUtil.getUser(getRequest()));
                if (userAmount.getBalance().compareTo(amountRecord.getMoney()) < 0) {
                    return new Result(ResultEnum.AMOUNT_INSUFFICIENT_BALANCE);
                }
                String price = Tools.getPrice();
                BigDecimal commission = null;
                if (amountRecord.getMoney().compareTo(new BigDecimal("100")) <= 0) {
                    commission = new BigDecimal(price).setScale(2,
                            BigDecimal.ROUND_HALF_UP);
                    amountRecord.setSponsorMoney(amountRecord.getMoney().multiply(new BigDecimal(price)).setScale(2,
                            BigDecimal.ROUND_HALF_UP));
                    amountRecord.setCommission(commission);
                } else if (amountRecord.getMoney().compareTo(new BigDecimal("100")) > 0 && amountRecord.getMoney().compareTo(new BigDecimal("1000")) < 0) {
                    commission =
                            amountRecord.getMoney().multiply(new BigDecimal(price)).multiply(new BigDecimal("0.01"))
                                    .setScale(2, BigDecimal.ROUND_HALF_UP);
                    amountRecord.setSponsorMoney(amountRecord.getMoney().multiply(new BigDecimal(price)).setScale(2,
                            BigDecimal.ROUND_HALF_UP));
                    amountRecord.setCommission(commission);
                } else {
                    commission = new BigDecimal("10").multiply(new BigDecimal(price)).setScale(2,
                            BigDecimal.ROUND_HALF_UP);
                    amountRecord.setSponsorMoney(amountRecord.getMoney().multiply(new BigDecimal(price)).setScale(2,
                            BigDecimal.ROUND_HALF_UP));
                    amountRecord.setCommission(commission);
                }
                userAmount.setWithdrawAmount(userAmount.getWithdrawAmount().add(amountRecord.getMoney()));
                userAmount.setBalance(userAmount.getBalance().subtract(amountRecord.getMoney()));
                userAmountService.update(userAmount);
                amountRecord.setExchangeRate(new BigDecimal(price));
                amountRecord.setLoginName(JwtUtil.getUser(getRequest()));
                amountRecord.setId(Tools.getUUId());
                amountRecord.setPayStatus("WAITING_PROCESS");
                amountRecord.setOrderTime(DateUtils.getCurrDateTimeStr());
                amountRecord.setAmountType("WITHDRAWAL");
                amountRecordService.add(amountRecord);
                return new Result(commission);
            }
//            return new Result (ResultEnum.PARAMETER_ERROR);
        } catch (Exception e) {
            logger.error("AmountRecord出异常了", e);
            return new Result(e);
        }
    }


    /* 杭州尚实支付的开始--------------------------------------------------------------------------------------------- */

    /**
     * 描述：充值
     *
     * @param amountRecord 资金记录id
     */
    @RequestMapping(value = "/pay")
    @ResponseBody
    public Result pay(AmountRecord amountRecord) throws Exception {
        try {
            String uuid = Tools.getUUId();
            String price = Tools.getPrice();
            Object obj = PayUtils.pay(amountRecord.getPayWay(), amountRecord.getMoney(), price, uuid, amountRecord.getReturnUrl());
            if (obj instanceof Result) {
                return (Result) obj;
            }
            Map<String, Object> response = (Map<String, Object>) obj;
            amountRecord.setExchangeRate(new BigDecimal(price));
            amountRecord.setSponsorMoney(amountRecord.getMoney().multiply(new BigDecimal(price)).setScale(2,
                    BigDecimal.ROUND_HALF_UP));
            amountRecord.setId(uuid);
            amountRecord.setPayNotifyStatus(2);
            amountRecord.setOrderTime(DateUtils.getCurrDateTimeStr());
            amountRecord.setPayNo(response.get("out_trade_no").toString());
            amountRecord.setAmountType("PAY");
            amountRecord.setLoginName(JwtUtil.getUser(getRequest()));
            amountRecord.setPayMsg(response.get("err_msg").toString());
            amountRecord.setPayStatus("WAIT_BUYER_PAY");
            amountRecordService.add(amountRecord);
            return new Result(response);
        } catch (Exception e) {
            logger.error("充值出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 支付回调
     *
     * @param payNotify
     * @return
     */
    @RequestMapping(value = "/payNotify")
    @ResponseBody
    public Result payNotify(@RequestBody PayNotify payNotify) {
        logger.info("请求开始===参数:" + payNotify.toString());
        Map<String, Object> response = JSONUtils.toHashMap(payNotify);
        String responseSign = response.get("sign").toString();
        response.remove("sign");
        String myResponseSign = PayUtils.sign(response);
        if (!responseSign.equals(myResponseSign)) {
            return new Result(ResultEnum.PAY_SIGN_ERROR);
        }
        AmountRecord amountRecord = amountRecordService.findById(payNotify.getMer_trade_no());
        if (amountRecord == null || Tools.isEmpty(amountRecord.getId()) || amountRecord.getPayNotifyStatus() == 1) {
            return new Result(true);
        }
        //如果金额不匹配，则重新计算充值金额
        //得到实际支付RMB金额
        BigDecimal rmbMoney = new BigDecimal(payNotify.getTotalAmount().toString()).divide(new BigDecimal(100));
        if (amountRecord.getSponsorMoney().compareTo(rmbMoney) != 0) {
            BigDecimal price = amountRecord.getExchangeRate();
            amountRecord.setMoney(new BigDecimal(payNotify.getTotalAmount().toString()).divide(new BigDecimal(100)).divide(price, 2,
                    BigDecimal.ROUND_HALF_UP));
        }
        amountRecord.setRmbMoney(new BigDecimal(payNotify.getTotalAmount().toString()).divide(new BigDecimal(100)));

        if ("TRADE_SUCCESS".equals(payNotify.getBillStatus()) || "TRADE_CLOSED".equals(payNotify.getBillStatus())) {
            amountRecord.setPayNotifyStatus(1);
        }
        amountRecord.setPayStatus(payNotify.getBillStatus());
        amountRecord.setPayNo(payNotify.getOut_trade_no());
        amountRecord.setPayTime(payNotify.getPayTime());
        amountRecordService.update(amountRecord);
        if ("TRADE_SUCCESS".equals(amountRecord.getPayStatus())) {
            amountRecordService.paySuccessRecord(amountRecord);
        }
        return new Result(true);
    }

    /* 杭州尚实支付的结束--------------------------------------------------------------------------------------------- */

    /* 中付支付的开始--------------------------------------------------------------------------------------------- */

    /**
     * 描述：充值
     *
     * @param amountRecord 资金记录id
     */
    @RequestMapping(value = "/zf/pay")
    @ResponseBody
    public Result zfpay(AmountRecord amountRecord) throws Exception {
        try {
            String uuid = Tools.getRandomCode(32, 7);
            String price = Tools.getPrice();
            String json = ZFPayUtil.pay(amountRecord.getPayWay(), amountRecord.getMoney(), uuid, amountRecord.getReturnUrl());
            if (json.indexOf("http://") < 0) {
                String[] codeAndMsg = json.split(",");
                return new Result(codeAndMsg[0], codeAndMsg[1]);
            }
            amountRecord.setExchangeRate(new BigDecimal(price));
            amountRecord.setSponsorMoney(amountRecord.getMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
            amountRecord.setMoney(amountRecord.getMoney().divide(new BigDecimal(price), 2, BigDecimal.ROUND_HALF_UP));
            amountRecord.setId(uuid);
            amountRecord.setPayNotifyStatus(2);
            amountRecord.setOrderTime(DateUtils.getCurrDateTimeStr());
            amountRecord.setAmountType("PAY");
            amountRecord.setLoginName(JwtUtil.getUser(getRequest()));
            amountRecord.setPayStatus("WAIT_BUYER_PAY");
            amountRecordService.add(amountRecord);

            return new Result(json);
        } catch (Exception e) {
            logger.error("充值出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 支付回调
     *
     * @param zFPayNotify
     * @return
     */
    @RequestMapping(value = "/zf/payNotify")
    @ResponseBody
    public String zfpayNotify(ZFPayNotify zFPayNotify) {
        //amount=0.00&
        // datetime=1562573445&
        // memberid=1000020028&
        // orderid=7e7df2e9f2b1451c9bbee1074fdee0e5&
        // paytype=alipay&
        // returncode=0&
        // attach=null&
        // sign=F2A55BBAD5260AE92149A04080A689FC
        if (ZFPayUtil.verifySign(zFPayNotify)) {

        } else {
            return "failure";
        }
        AmountRecord amountRecord = amountRecordService.findById(zFPayNotify.getMemberid());
        if (amountRecord == null || Tools.isEmpty(amountRecord.getId()) || amountRecord.getPayNotifyStatus() == 1) {
            return "failure";
        }
        if (PayOrderUtit.put(amountRecord)) {
            //如果金额不匹配，则重新计算充值金额
            if (amountRecord.getSponsorMoney().compareTo(new BigDecimal(zFPayNotify.getAmount())) != 0) {
                BigDecimal price = amountRecord.getExchangeRate();
                amountRecord.setMoney(new BigDecimal(zFPayNotify.getAmount()).divide(price, 2, BigDecimal.ROUND_HALF_UP));
            }
            amountRecord.setRmbMoney(new BigDecimal(zFPayNotify.getAmount()));
            amountRecord.setPayNotifyStatus(1);
            if ("1".equals(zFPayNotify.getReturncode())) {
                amountRecord.setPayStatus("TRADE_SUCCESS");
                amountRecord.setPayTime(DateUtils.getFormatDateTime(new Date(Long.valueOf(zFPayNotify.getDatetime() + "000"))));
            } else {
                amountRecord.setPayStatus("TRADE_CLOSED");
            }
            amountRecord.setPayNo(zFPayNotify.getHForderid());
            amountRecordService.update(amountRecord);
            if ("TRADE_SUCCESS".equals(amountRecord.getPayStatus())) {
                amountRecordService.paySuccessRecord(amountRecord);
            }
            PayOrderUtit.del(amountRecord);
        }
        return "success";
    }

    /* 中付支付的结束--------------------------------------------------------------------------------------------- */
}