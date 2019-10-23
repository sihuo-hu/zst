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
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TradingOpportunities;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.AppUserService;
import cn.stylefeng.guns.modular.system.service.TradingOpportunitiesService;
import cn.stylefeng.guns.modular.system.warpper.AppUserWrapper;
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
import java.util.Map;

/**
 * 交易机会管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/opportunities")
public class TradingOpportunitiesController extends BaseController {

    private static String PREFIX = "/modular/operation/opportunities/";

    @Autowired
    private TradingOpportunitiesService tradingOpportunitiesService;

    /**
     * 跳转到交易机会列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "opportunities.html";
    }


    /**
     * 查询交易机会列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String timeLimit) {
        //拼接查询条件
        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty (timeLimit)) {
            String[] split = timeLimit.split (" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Page<Map<String, Object>> users = tradingOpportunitiesService.selectTradingOpportunities ( name,beginTime,
                endTime);
        return LayuiPageFactory.createPageInfo (users);
    }

    /**
     * 跳转到添加交易机会
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/opportunities_add")
    public String addView() {
        return PREFIX + "opportunities_add.html";
    }
    /**
     * 添加交易机会
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(@Valid TradingOpportunities user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.tradingOpportunitiesService.addTradingOpportunities(user);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到编辑交易机会页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/opportunities_edit")
    public String userEdit(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        TradingOpportunities user = this.tradingOpportunitiesService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "opportunities_edit.html";
    }

    /**
     * 修改交易机会
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(@Valid TradingOpportunities user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.tradingOpportunitiesService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 获取交易机会详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getOpportunities")
    @ResponseBody
    public Object getOpportunities(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }
        TradingOpportunities user = this.tradingOpportunitiesService.getById(userId);
        Map<String, Object> map = BeanUtil.beanToMap(user);
        HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        return ResponseData.success(hashMap);
    }

    /**
     * 修改开关状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam int id,@RequestParam String opportuntiesStatusTpl) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.tradingOpportunitiesService.setStatus(id,opportuntiesStatusTpl);
        return SUCCESS_TIP;
    }
}
