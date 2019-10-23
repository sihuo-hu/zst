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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.state.ManagerStatus;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.modular.system.entity.*;
import cn.stylefeng.guns.modular.system.model.SymbolDto;
import cn.stylefeng.guns.modular.system.service.*;
import cn.stylefeng.guns.modular.system.warpper.AccountWrapper;
import cn.stylefeng.guns.modular.system.warpper.CashCouponWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代金券管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/cashcoupon")
public class CashCouponController extends BaseController {

    private static String PREFIX = "/modular/operation/cashcoupon/";

    @Autowired
    private CashCouponService cashCouponService;
    @Autowired
    private SymbolInfoService symbolInfoService;
    @Autowired
    private UserCashCouponService userCashCouponService;

    /**
     * 跳转到查看代金券列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "cashcoupon.html";
    }


    /**
     * 查询代金券列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty (timeLimit)) {
            String[] split = timeLimit.split (" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Page<Map<String, Object>> cashCoupon = cashCouponService.selectCashCoupon ( beginTime, endTime);
        Page wrapped = new CashCouponWrapper(cashCoupon).wrap ();
        return LayuiPageFactory.createPageInfo (wrapped);
    }

    /**
     * 去添加页面
     * @return
     */
    @Permission
    @RequestMapping("/cashcoupon_add")
    public String userEdit() {
        return PREFIX + "cashcoupon_add.html";
    }

    /**
     * 添加
     * @param cashCoupon
     * @param result
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(@Valid CashCoupon cashCoupon, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.cashCouponService.addCashCoupon(cashCoupon);
        return SUCCESS_TIP;
    }
    /**
     * 获取复选框内容
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getCheckbox")
    @ResponseBody
    public Object getCheckbox() {
        List<SymbolDto> symbolList = symbolInfoService.getSymbolCodeAndSymbolName();
        return ResponseData.success(symbolList);
    }

    /**
     * 修改代金券状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam String id,@RequestParam String checked) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.cashCouponService.setStatus(id,checked);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到发放页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/cashcoupon_push")
    public String cashcouponEdit(@RequestParam String id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "cashcoupon_push.html";
    }

    /**
     * 获取优惠券详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getCashcoupon")
    @ResponseBody
    public Object getCheckbox(String id) {
        CashCoupon cashCoupon = this.cashCouponService.getById(id);
        return ResponseData.success(cashCoupon);
    }

    /**
     * 发放
     * @param cashCouponPushLog
     * @param result
     * @return
     */
    @RequestMapping("/push")
    @ResponseBody
    public ResponseData push(@Valid CashCouponPushLog cashCouponPushLog, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser user = ShiroKit.getUser();
        cashCouponPushLog.setOperator(user.getName());
        cashCouponPushLog.setCashCouponId(cashCouponPushLog.getId());
        cashCouponPushLog.setId(null);
        cashCouponPushLog.setCreateTime(DateUtils.getCurrDateTimeStr());
        CashCoupon cashCoupon = this.cashCouponService.getById(cashCouponPushLog.getCashCouponId());
        this.userCashCouponService.saveList(cashCoupon,cashCouponPushLog);

        return SUCCESS_TIP;
    }

}
