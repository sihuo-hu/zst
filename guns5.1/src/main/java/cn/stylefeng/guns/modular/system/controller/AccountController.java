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

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.*;
import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.entity.FundPipeline;
import cn.stylefeng.guns.modular.system.entity.UserAmount;
import cn.stylefeng.guns.modular.system.model.FundPipelineEnum;
import cn.stylefeng.guns.modular.system.model.UserAccountInfo;
import cn.stylefeng.guns.modular.system.model.WithdrawExcel;
import cn.stylefeng.guns.modular.system.service.AccountService;
import cn.stylefeng.guns.modular.system.service.FundPipelineService;
import cn.stylefeng.guns.modular.system.service.UserAccountService;
import cn.stylefeng.guns.modular.system.warpper.AccountWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 资金记录管理
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    private static String PREFIX = "/modular/operation/account/";

    @Autowired
    private AccountService accountService;
    @Autowired
    private FundPipelineService fundPipelineService;
    @Autowired
    private UserAccountService userAccountService;

    /*充值相关开始-----------------------------------------------------------------------------------------------*/

    /**
     * 跳转到充值列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/pay")
    public String payIndex() {
        return PREFIX + "account_pay.html";
    }


    /**
     * 查询充值列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/pay/list")
    @Permission
    @ResponseBody
    public Object payList(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Page<Map<String, Object>> accounts = accountService.selectPayAmountRecord(name, beginTime, endTime);
        Page wrapped = new AccountWrapper(accounts).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 跳转到充值统计列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/pay/s")
    public String paySIndex() {
        return PREFIX + "account_pay_statistics.html";
    }


    /**
     * 查询充值统计列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/pay/statistics")
    @Permission
    @ResponseBody
    public Object payStatisticsList(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Map<String, Object> accounts = accountService.selectPayStatisticsAmountRecord(name, beginTime, endTime);
        Page<Map<String, Object>> accountsStatistics = new Page<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(accounts);
        accountsStatistics.setRecords(list);
        accountsStatistics.setTotal(1);
        return LayuiPageFactory.createPageInfo(accountsStatistics);
    }

    /*充值相关结束-------------------------------------------------------------------------------------------------*/

    /*提现相关开始-----------------------------------------------------------------------------------------------*/

    /**
     * 跳转到提现列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw")
    public String withdrawIndex() {
        return PREFIX + "account_withdraw.html";
    }


    /**
     * 查询提现列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/list")
    @Permission
    @ResponseBody
    public Object withdrawList(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String timeLimit,
                               @RequestParam(required = false) String batchId) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Page<Map<String, Object>> accounts = accountService.selectWithdrawAmountRecord(name, beginTime, endTime, batchId);
        Page wrapped = new AccountWrapper(accounts).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 跳转到提现统计列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/s")
    public String withdrawSIndex() {
        return PREFIX + "account_pay_statistics.html";
    }


    /**
     * 查询提现统计列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/statistics")
    @Permission
    @ResponseBody
    public Object withdrawStatisticsList(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Map<String, Object> accounts = accountService.selectWithdrawStatisticsAmountRecord(name, beginTime, endTime);
        Page<Map<String, Object>> accountsStatistics = new Page<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(accounts);
        accountsStatistics.setRecords(list);
        accountsStatistics.setTotal(1);
        return LayuiPageFactory.createPageInfo(accountsStatistics);
    }

    /**
     * 导出Excel
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/exportExcel")
    @Permission
    public void exportExcel(String ids, HttpServletResponse response) {
        String batchId = DateUtils.getCurrDateTimeSeries();
        ids = ids.substring(0, ids.length() - 1);
        List<String> idList = Arrays.asList(ids.split(","));
        List<WithdrawExcel> list = accountService.selectByIds(idList);
        for (WithdrawExcel withdrawExcel : list) {
            withdrawExcel.setMoney(withdrawExcel.getSponsorMoney().subtract(withdrawExcel.getCommission()));
        }
        String[] columnNames = {"订单号", "手机号", "姓名", "申请时间", "发起金额（未扣除手续费）", "手续费", "应打款金额", "银行卡号", "开户行", "分行"};
        String fileName = batchId;
        ExportExcelUtil<WithdrawExcel> util = new ExportExcelUtil<WithdrawExcel>();
        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEl_FILE_2007);
        List<AccountRecord> accountRecordList = accountService.selectAccountRecordByIds(idList,"WAITING_PROCESS");
        for (AccountRecord accountRecord : accountRecordList) {
            accountRecord.setBatchId(batchId);
            accountRecord.setPayStatus("BEING_PROCESSED");
        }
        accountService.updateBatchById(accountRecordList);
    }

    /**
     * 提现完成
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/done")
    @Permission
    @ResponseBody
    public Object withdrawDone(@RequestParam(required = false) String ids) {
        ids = ids.substring(0, ids.length() - 1);
        System.out.println(ids);
        List<String> idList = Arrays.asList(ids.split(","));
        List<AccountRecord> accountRecordList = accountService.selectAccountRecordByIds(idList,"BEING_PROCESSED");
        List<FundPipeline> fundPipelineList = new ArrayList<>();
        List<String> loginNameList = new ArrayList<>();
        for (AccountRecord accountRecord : accountRecordList) {
            if (Tools.isEmpty(accountRecord.getBatchId()) || !accountRecord.getPayStatus().equals("BEING_PROCESSED")) {
                continue;
            }
            accountRecord.setPayStatus("TRADE_SUCCESS");
            accountRecord.setPayTime(DateUtils.getCurrDateTimeStr());
            QueryWrapper<UserAmount> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("login_name", accountRecord.getLoginName());
            UserAmount userAmount = userAccountService.getOne(queryWrapper);
            fundPipelineList.add(new FundPipeline(FundPipelineEnum.PAY.getKey(), accountRecord.getMoney(), userAmount.getBalance(), userAmount.getLoginName(), accountRecord.getId(), FundPipelineEnum.INCOME.getKey()));
            loginNameList.add(userAmount.getLoginName());
        }
        if (loginNameList.size() > 0) {
            ThreadPoolUtil.pushList(loginNameList, "您申请的提现已成功审核通过，欢迎再次使用。");
            accountService.updateBatchById(accountRecordList);
            fundPipelineService.saveBatch(fundPipelineList);
        }
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改提现状态的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/toUpdate")
    public String withdrawUpdateIndex() {
        return PREFIX + "account_withdraw_update.html";
    }

    /**
     * 获取提现基本信息
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/getInfo")
    @ResponseBody
    public Object withdrawGetInfo(@RequestParam(required = false) String id) {
        WithdrawExcel withdrawExcel = accountService.getWithdrawExcelById(id);
        if (withdrawExcel == null) {
            return ResponseData.error("无可更改状态的订单");
        }
        withdrawExcel.setMoney(withdrawExcel.getSponsorMoney().subtract(withdrawExcel.getCommission()));
        return ResponseData.success(withdrawExcel);
    }

    /**
     * 提现状态修改
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/update")
    @Permission
    @ResponseBody
    public Object withdrawUpdate(WithdrawExcel withdrawExcel) {
        AccountRecord accountRecord = accountService.getById(withdrawExcel.getId());
        if (accountRecord == null) {
            throw new ServiceException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        accountRecord.setPayStatus(withdrawExcel.getPayStatus());
        //等待处理
        if ("WAITING_PROCESS".equals(withdrawExcel.getPayStatus())) {
            accountRecord.setPayStatus("WAITING_PROCESS");
            accountService.updateById(accountRecord);
            //处理中
        } else if ("BEING_PROCESSED".equals(withdrawExcel.getPayStatus())) {
            accountRecord.setPayStatus("BEING_PROCESSED");
            accountService.updateById(accountRecord);
            //失败
        } else if ("FAILURE".equals(withdrawExcel.getPayStatus())) {
            accountRecord.setPayMsg(withdrawExcel.getErrorMsg());
            accountRecord.setPayStatus("FAILURE");
            accountService.updateById(accountRecord);
            QueryWrapper<UserAmount> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("login_name", accountRecord.getLoginName());
            UserAmount userAmount = userAccountService.getOne(queryWrapper);
            userAmount.setWithdrawAmount(userAmount.getWithdrawAmount().subtract(accountRecord.getMoney()));
            userAmount.setBalance(userAmount.getBalance().add(accountRecord.getMoney()));
            userAccountService.updateById(userAmount);

            //成功
        } else if ("TRADE_SUCCESS".equals(withdrawExcel.getPayStatus())) {
            accountRecord.setPayStatus("TRADE_SUCCESS");
            QueryWrapper<UserAmount> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("login_name", accountRecord.getLoginName());
            UserAmount userAmount = userAccountService.getOne(queryWrapper);
            fundPipelineService.save(new FundPipeline(FundPipelineEnum.PAY.getKey(), accountRecord.getMoney(), userAmount.getBalance(), userAmount.getLoginName(), accountRecord.getId(), FundPipelineEnum.INCOME.getKey()));
            ThreadPoolUtil.pushOne(userAmount.getLoginName(), "您申请的提现已成功审核通过，欢迎再次使用。");
            accountRecord.setPayTime(DateUtils.getCurrDateTimeStr());
            accountService.updateById(accountRecord);
        } else {
            throw new ServiceException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        return SUCCESS_TIP;
    }

    /**
     * 跳转到查看账户信息的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/toAccountInfo")
    public String withdrawToAccountInfoIndex() {
        return PREFIX + "account_info.html";
    }

    /**
     * 获取账户信息
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/withdraw/accountInfo")
    @Permission
    @ResponseBody
    public Object withdrawGetAccountInfo(@RequestParam(required = false) String loginName) {
        UserAccountInfo userAccountInfo = userAccountService.getAccountInfoById(loginName);
        if (userAccountInfo == null) {
            throw new ServiceException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        return ResponseData.success(userAccountInfo);
    }
    /*提现相关结束-------------------------------------------------------------------------------------------------*/


}
