/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.entity.CashCouponPushLog;
import cn.stylefeng.guns.modular.system.model.SymbolDto;
import cn.stylefeng.guns.modular.system.service.CashCouponPushLogService;
import cn.stylefeng.guns.modular.system.service.CashCouponService;
import cn.stylefeng.guns.modular.system.service.SymbolInfoService;
import cn.stylefeng.guns.modular.system.service.UserCashCouponService;
import cn.stylefeng.guns.modular.system.warpper.CashCouponWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代金券管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/user/cashcoupon")
public class UserCashCouponController extends BaseController {

    private static String PREFIX = "/modular/operation/usercashcoupon/";

    @Autowired
    private UserCashCouponService userCashCouponService;

    /**
     * 跳转到用户代金券列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "usercashcoupon.html";
    }

    /**
     * 查询用户代金券列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String timeLimit, @RequestParam(required = false) String loginName) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Page<Map<String, Object>> cashCoupon = userCashCouponService.selectUserCashCoupon(beginTime, endTime, loginName);
        return LayuiPageFactory.createPageInfo(cashCoupon);
    }

    /**
     * 跳转到用户代金券列表的页面（统计）
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/statistics")
    public String statisticsIndex() {
        return PREFIX + "usercashcoupon_statistics.html";
    }

    /**
     * 查询用户代金券列表(统计)
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/statistics/list")
    @Permission
    @ResponseBody
    public Object statisticsList(@RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Map<String, Object> cashCoupon = userCashCouponService.selectUserCashCouponStatistics(beginTime, endTime);
        Page<Map<String, Object>> cashCouponStatistics = new Page<>();
        List<Map<String, Object>> list= new ArrayList<>();
        list.add(cashCoupon);
        cashCouponStatistics.setRecords(list);
        cashCouponStatistics.setTotal(1);
        return LayuiPageFactory.createPageInfo(cashCouponStatistics);
    }

}
