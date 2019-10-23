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
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TransactionRecord;
import cn.stylefeng.guns.modular.system.service.AppUserService;
import cn.stylefeng.guns.modular.system.service.TransactionRecordService;
import cn.stylefeng.guns.modular.system.warpper.AppUserWrapper;
import cn.stylefeng.guns.modular.system.warpper.TransactionRecordWrapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP用户管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/transaction")
public class TransactionRecordController extends BaseController {

    private static String PREFIX = "/modular/operation/transaction/";

    @Autowired
    private TransactionRecordService transactionRecordService;

    /**
     * 跳转到持仓列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/buy")
    public String index() {
        return PREFIX + "transaction.html";
    }


    /**
     * 查询持仓列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/buy/list")
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
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("transactionStatus", 1);
        Page<Map<String, Object>> transactionRecords = transactionRecordService.selectTransactionRecords(map, beginTime, endTime);
        Page wrapped = new TransactionRecordWrapper(transactionRecords).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 跳转到持仓统计的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/buy/s")
    public String buyStatisticsIndex() {
        return PREFIX + "transaction_buy_statistics.html";
    }
    /**
     * 获取持仓统计
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/buy/statistics")
    @Permission
    @ResponseBody
    public Object buyStatistics(@RequestParam(required = false) String timeLimit) {
        //拼接查询条件
        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Map<String, Object> buyStatistics = transactionRecordService.selectBuyStatistics(beginTime, endTime);
        Page<Map<String, Object>> buyStatisticsPage = new Page<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(buyStatistics);
        buyStatisticsPage.setRecords(list);
        buyStatisticsPage.setTotal(1);
        return LayuiPageFactory.createPageInfo(buyStatisticsPage);
    }

    /**
     * 跳转到平仓列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/sell")
    public String sellIndex() {
        return PREFIX + "transaction_sell.html";
    }


    /**
     * 查询平仓列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/sell/list")
    @Permission
    @ResponseBody
    public Object sellList(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String timeLimit) {
        //拼接查询条件
        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("transactionStatus", 2);
        Page<Map<String, Object>> transactionRecords = transactionRecordService.selectTransactionRecords(map, beginTime, endTime);
        Page wrapped = new TransactionRecordWrapper(transactionRecords).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 跳转到平仓统计的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/sell/s")
    public String sellStatisticsIndex() {
        return PREFIX + "transaction_sell_statistics.html";
    }
    /**
     * 获取平仓统计
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/sell/statistics")
    @Permission
    @ResponseBody
    public Object sellStatistics(@RequestParam(required = false) String timeLimit) {
        //拼接查询条件
        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Map<String, Object> sellStatistics = transactionRecordService.selectSellStatistics(beginTime, endTime);
        Page<Map<String, Object>> sellStatisticsPage = new Page<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(sellStatistics);
        sellStatisticsPage.setRecords(list);
        sellStatisticsPage.setTotal(1);
        return LayuiPageFactory.createPageInfo(sellStatisticsPage);
    }

}
