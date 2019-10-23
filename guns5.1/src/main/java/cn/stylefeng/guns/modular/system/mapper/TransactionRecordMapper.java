package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TransactionRecord;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {

    Page<Map<String, Object>> selectTransactionRecords(@Param("page")Page page, @Param("map")Map<String,Object> map, @Param("beginTime")String beginTime, @Param("endTime")String endTime);

    Map<String, Object> selectBuyStatistics(@Param("beginTime")String beginTime, @Param("endTime")String endTime);

    Map<String, Object> selectSellStatistics(@Param("beginTime")String beginTime, @Param("endTime")String endTime);

    Map<String, Object> selectSellStatisticsProfitOrderNumber(@Param("beginTime")String beginTime, @Param("endTime")String endTime);

    Map<String, Object> selectSellStatisticsEqualityNumber(@Param("beginTime")String beginTime, @Param("endTime")String endTime);
}
