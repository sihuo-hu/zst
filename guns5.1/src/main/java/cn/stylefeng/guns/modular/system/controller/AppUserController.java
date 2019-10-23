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
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.HttpUtils;
import cn.stylefeng.guns.core.util.PushUtil;
import cn.stylefeng.guns.core.util.ThreadPoolUtil;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.service.AppUserService;
import cn.stylefeng.guns.modular.system.warpper.AppUserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * APP用户管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/user")
public class AppUserController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(AppUserController.class);

    private static String PREFIX = "/modular/operation/user/";

    @Autowired
    private AppUserService appUserService;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }


    /**
     * 查询用户列表
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

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

//        DataScope dataScope = new DataScope (ShiroKit.getDeptDataScope ());
        Page<Map<String, Object>> users = appUserService.selectUsers(null, name, beginTime, endTime);
        Page wrapped = new AppUserWrapper(users).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 跳转到编辑用户页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        AppUser user = this.appUserService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到审核页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/user_audit")
    public String userAudit(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        AppUser user = this.appUserService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_audit.html";
    }
    /**
     * 修改用户状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/editStatus")
    @ResponseBody
    public ResponseData editStatus(@Valid AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Map<String,String> map = new HashMap<>();
        map.put("userStatus",user.getUserStatus());
        map.put("loginName",user.getLoginName());
        HttpUtils.doPost("http://localhost:8081/royal/login/refreshUserStatus",map);
        this.appUserService.editUserStatus(user);
        return SUCCESS_TIP;
    }
    /**
     * 修改用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(@Valid AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.appUserService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 审核用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/audit")
    @ResponseBody
    public ResponseData audit(@Valid AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        try {
            if ("VERIFIED".equals(user.getAuditStatus())) {
                ThreadPoolUtil.pushOne(user.getLoginName(), "亲爱的用户，您的认证已审核通过！代金券已发放至您的账户内，为您准备的见面礼~记得及时使用哦。");
            }else if("REJECTED".equals(user.getAuditStatus())){
                ThreadPoolUtil.pushOne(user.getLoginName(), "亲爱的用户，您的认证未审核通过！请登录app修改相关信息。");
            }
        } catch (Exception e) {
            log.error("推送失败：",e);
        }
        this.appUserService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }

        AppUser user = this.appUserService.getById(userId);
        Map<String, Object> map = BeanUtil.beanToMap(user);
        map.remove("password");

        HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);

        return ResponseData.success(hashMap);
    }

}
