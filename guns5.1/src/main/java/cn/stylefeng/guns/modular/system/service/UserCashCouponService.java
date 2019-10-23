package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.core.util.Tools;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.entity.CashCouponPushLog;
import cn.stylefeng.guns.modular.system.entity.UserCashCoupon;
import cn.stylefeng.guns.modular.system.mapper.CashCouponPushLogMapper;
import cn.stylefeng.guns.modular.system.mapper.UserCashCouponMapper;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户代金券 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class UserCashCouponService extends ServiceImpl<UserCashCouponMapper, UserCashCoupon> {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private CashCouponPushLogService cashCouponPushLogService;

    public void saveList(CashCoupon cashCoupon, CashCouponPushLog cashCouponPushLog) {
        String uuid = Tools.getRandomCode(32,7);
        String startTime = DateUtils.getCurrDateTimeStr();
        if (cashCoupon.getGrantMode() == "MANUAL" && cashCoupon.getStartTime() == null && !StringUtils.isEmpty(cashCouponPushLog.getStartTimeNew())) {
            startTime = cashCouponPushLog.getStartTimeNew();
        } else if (cashCoupon.getGrantMode() == "MANUAL" && !StringUtils.isEmpty(cashCoupon.getStartTime())) {
            startTime = cashCoupon.getStartTime();
        }
        String pastDueTime = DateUtils.getCurrDateTimeStr();
        if (cashCoupon.getPastDueMode().equals("DIFFERENT")) {
            pastDueTime = DateUtils.getFormatDateTime(DateUtils.getDateBeforeOrAfter(cashCoupon.getPastDueDay()));
        } else {
            pastDueTime = cashCoupon.getPastDueTime();
        }
        List<UserCashCoupon> userCashCouponList = new ArrayList<UserCashCoupon>();
        if (cashCouponPushLog.getTargetPopulation().equals("ALL_USER")) {
            List<AppUser> userList = appUserService.list();
            if (userList != null) {
                for (AppUser appUser : userList) {
                    UserCashCoupon userCashCoupon = setUserCashCoupon(cashCoupon.getId(), cashCoupon.getCcMoney(), appUser.getLoginName(), pastDueTime, startTime,uuid);
                    userCashCouponList.add(userCashCoupon);
                }
            }
            cashCouponPushLog.setPushCount(userCashCouponList.size());
        } else {
            String[] loginNames = cashCouponPushLog.getLoginNames().split(",");
            if (loginNames == null || loginNames.length < 1) {
                cashCouponPushLog.setPushCount(0);
                return;
            }
            for (String loginName : loginNames) {
                UserCashCoupon userCashCoupon = setUserCashCoupon(cashCoupon.getId(), cashCoupon.getCcMoney(), loginName, pastDueTime, startTime,uuid);
                userCashCouponList.add(userCashCoupon);
            }
            cashCouponPushLog.setPushCount(loginNames.length);
        }
        this.saveBatch(userCashCouponList);
        cashCouponPushLog.setId(uuid);
        this.cashCouponPushLogService.save(cashCouponPushLog);
    }

    private UserCashCoupon setUserCashCoupon(String id, BigDecimal ccMoney, String loginName, String pastDueTime, String startTime,String cashCouponPushLogId) {
        UserCashCoupon userCashCoupon = new UserCashCoupon();
        userCashCoupon.setCashCouponId(id);
        userCashCoupon.setCcMoney(ccMoney);
        userCashCoupon.setCreateTime(DateUtils.getCurrDateTimeStr());
        userCashCoupon.setLoginName(loginName);
        userCashCoupon.setPastDueTime(pastDueTime);
        userCashCoupon.setStartTime(startTime);
        userCashCoupon.setCashCouponPushLogId(cashCouponPushLogId);
        return userCashCoupon;
    }

    public Page<Map<String, Object>> selectUserCashCoupon(String beginTime, String endTime, String loginName) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectUserCashCoupon (page,beginTime, endTime,loginName);
    }

    public Map<String, Object> selectUserCashCouponStatistics(String beginTime, String endTime) {
        Map<String, Object> map = this.baseMapper.selectUserCashCouponStatistics (beginTime, endTime);
        map.putAll(this.baseMapper.selectUnused(beginTime, endTime));
        map.putAll(this.baseMapper.selectUsed(beginTime, endTime));
        map.putAll(this.baseMapper.selectPastDue(beginTime, endTime));
        map.putAll(this.baseMapper.selectProfit(beginTime, endTime));
        map.putAll(this.baseMapper.selectLoss(beginTime, endTime));
        return map;
    }
}
