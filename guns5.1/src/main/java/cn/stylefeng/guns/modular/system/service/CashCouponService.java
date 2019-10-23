package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.mapper.AccountMapper;
import cn.stylefeng.guns.modular.system.mapper.CashCouponMapper;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 代金券表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class CashCouponService extends ServiceImpl<CashCouponMapper, CashCoupon> {

    public Page<Map<String, Object>> selectCashCoupon(String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectCashCoupon (page,beginTime, endTime);
    }
    /**
     * 添加代金券
     * 1.判断是手动发放MANUAL还是自动发放AUTO,自动发放的话，生效日期固定为发放时间，手动发放如果为空，则也为发放时间
     * 2.自动发放下，pastDueMode只能选择按用户领取日期失效DIFFERENT
     * 3.统一失效UNIFY可以选择填写过期日期或过期天数，如果都填则取过期日期，按用户领取日期失效DIFFERENT则只能填写过期天数
     * 4.自动发放下，需选择发放条件，新用户NEW_CUSTOMER逻辑在后台处理，首次充值赠送FIRST_RECHARGE需要填写达标金额
     * @param cashCoupon
     */
    public void addCashCoupon(CashCoupon cashCoupon) {
        if(cashCoupon.getPastDueDay()==null&& StringUtils.isEmpty(cashCoupon.getPastDueTime())){
            throw new ServiceException(BizExceptionEnum.PAST_DUE_EROR);
        }
        if(cashCoupon.getGrantMode().equals("MANUAL")){
            cashCoupon.setGrantCondition(null);
            if(cashCoupon.getPastDueMode().equals("DIFFERENT")){
                cashCoupon.setPastDueTime(null);
                if(cashCoupon.getPastDueDay()==null){
                    throw new ServiceException(BizExceptionEnum.PAST_DUE_EROR);
                }
            }else{
                if(!StringUtils.isEmpty(cashCoupon.getPastDueTime())){
                    cashCoupon.setPastDueDay(null);
                }else{
                    String pastDueTime = DateUtils.getFormatDateTime(DateUtils.getDateBeforeOrAfter(cashCoupon.getPastDueDay()));
                    cashCoupon.setPastDueTime(pastDueTime);
                }
                if(!StringUtils.isEmpty(cashCoupon.getStartTime())){
                    Date startDate = DateUtils.StringToDate(cashCoupon.getStartTime());
                    Date pastDueDate = DateUtils.StringToDate(cashCoupon.getPastDueTime());
                    if(DateUtils.compare_date(startDate,pastDueDate)){
                        throw new ServiceException(BizExceptionEnum.PAST_DUE_EROR);
                    }
                    if(DateUtils.compare_date(new Date(),pastDueDate)){
                        throw new ServiceException(BizExceptionEnum.PAST_DUE_EROR);
                    }
                }

            }
        }else if(cashCoupon.getGrantMode().equals("AUTO")){
            cashCoupon.setStartTime(null);
            cashCoupon.setPastDueMode("DIFFERENT");
            cashCoupon.setPastDueTime(null);
        }
        cashCoupon.setCreateTime(DateUtils.getCurrDateTimeStr());
        this.baseMapper.insert(cashCoupon);
    }

    public void setStatus(String id, String checked) {
        this.baseMapper.setStatus(id,checked);
    }
}
