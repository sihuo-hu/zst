package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TradingOpportunities;
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
public interface TradingOpportunitiesMapper extends BaseMapper<TradingOpportunities> {

    Page<Map<String, Object>> selectTradingOpportunities(@Param("page")Page page, @Param("name")String name, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}
