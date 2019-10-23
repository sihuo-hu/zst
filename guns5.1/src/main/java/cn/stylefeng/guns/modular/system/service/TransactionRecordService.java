package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TransactionRecord;
import cn.stylefeng.guns.modular.system.mapper.AppUserMapper;
import cn.stylefeng.guns.modular.system.mapper.TransactionRecordMapper;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class TransactionRecordService extends ServiceImpl<TransactionRecordMapper, TransactionRecord> {

    public Page<Map<String, Object>> selectTransactionRecords(Map<String,Object> map, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectTransactionRecords (page, map, beginTime, endTime);
    }

    public Map<String, Object> selectBuyStatistics(String beginTime, String endTime) {
        Map<String, Object> map = this.baseMapper.selectBuyStatistics (beginTime, endTime);
        return map;
    }

    public Map<String, Object> selectSellStatistics(String beginTime, String endTime) {
        Map<String, Object> map = this.baseMapper.selectSellStatistics (beginTime, endTime);
        map.putAll(this.baseMapper.selectSellStatisticsProfitOrderNumber (beginTime, endTime));
        map.putAll(this.baseMapper.selectSellStatisticsEqualityNumber (beginTime, endTime));
        return map;
    }
}
