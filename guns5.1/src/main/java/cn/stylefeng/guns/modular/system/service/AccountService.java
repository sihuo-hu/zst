package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.mapper.AccountMapper;
import cn.stylefeng.guns.modular.system.model.WithdrawExcel;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资金管理 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountRecord> {

    /**
     * 查询用户充值列表
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    public Page<Map<String, Object>> selectPayAmountRecord(String name, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectPayAmountRecord (page, name, beginTime, endTime);
    }

    /**
     * 获取充值统计
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    public Map<String, Object> selectPayStatisticsAmountRecord(String name, String beginTime, String endTime) {
        return this.baseMapper.selectPayStatisticsAmountRecord ( name, beginTime, endTime);
    }

    /**
     * 提现详情
     * @param name
     * @param beginTime
     * @param endTime
     * @param batchId
     * @return
     */
    public Page<Map<String, Object>> selectWithdrawAmountRecord(String name, String beginTime, String endTime, String batchId) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectWithdrawAmountRecord ( page ,name, beginTime, endTime,batchId);
    }

    /**
     * 提现统计
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    public Map<String, Object> selectWithdrawStatisticsAmountRecord(String name, String beginTime, String endTime) {
        return this.baseMapper.selectWithdrawStatisticsAmountRecord ( name, beginTime, endTime);
    }

    /**
     * 根据ID查出需要提现的用户
     * @param idList
     * @return
     */
    public List<WithdrawExcel> selectByIds(List<String> idList) {
        return this.baseMapper.selectByIds(idList);
    }

    /**
     * 根据ID查询交易记录
     * @param idList
     * @return
     */
    public List<AccountRecord> selectAccountRecordByIds(List<String> idList,String payStatus) {
        QueryWrapper<AccountRecord> accountRecordQueryWrapper = new QueryWrapper<>();
        accountRecordQueryWrapper.in("id",idList).eq("pay_status",payStatus);
        return this.baseMapper.selectList(accountRecordQueryWrapper);
    }

    /**
     * 根据ID获取提现详情
     * @param id
     * @return
     */
    public WithdrawExcel getWithdrawExcelById(String id) {
        return this.baseMapper.getWithdrawExcelById(id);
    }

}
