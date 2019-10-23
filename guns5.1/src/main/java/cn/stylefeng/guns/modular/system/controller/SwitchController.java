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
import cn.stylefeng.guns.core.util.Tools;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.entity.CashCouponPushLog;
import cn.stylefeng.guns.modular.system.entity.Switch;
import cn.stylefeng.guns.modular.system.model.SymbolDto;
import cn.stylefeng.guns.modular.system.service.CashCouponService;
import cn.stylefeng.guns.modular.system.service.SwitchService;
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
import java.util.List;
import java.util.Map;

/**
 * 开关管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/switch")
public class SwitchController extends BaseController {

    private static String PREFIX = "/modular/operation/switch/";

    @Autowired
    private SwitchService switchService;


    /**
     * 跳转到查看开关列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "switch.html";
    }


    /**
     * 查询开关列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String ditch) {
        Page<Map<String, Object>> switchPage = switchService.selectSwitch ( ditch);
        if(switchPage.getRecords()!=null){
            for (Map<String, Object> record : switchPage.getRecords()) {
                if(Tools.isEmpty(record.get("ditch"))){
                    record.put("ditch","未知");
                }
            }
        }
        return LayuiPageFactory.createPageInfo (switchPage);
    }

    /**
     * 去添加页面
     * @return
     */
    @Permission
    @RequestMapping("/switch_add")
    public String userEdit() {
        return PREFIX + "switch_add.html";
    }

    /**
     * 添加
     * @param appSwitch
     * @param result
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(@Valid Switch appSwitch, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.switchService.addCashCoupon(appSwitch);
        return SUCCESS_TIP;
    }

    /**
     * 修改开关状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam String id,@RequestParam String checked,@RequestParam String switchType) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.switchService.setStatus(id,checked,switchType);
        return SUCCESS_TIP;
    }

}
