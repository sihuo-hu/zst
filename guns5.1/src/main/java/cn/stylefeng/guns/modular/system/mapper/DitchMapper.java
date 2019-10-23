package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.DitchCoupon;
import cn.stylefeng.guns.modular.system.entity.UserCashCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  渠道表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface DitchMapper extends BaseMapper<DitchCoupon> {

    List<Map<String, Object>> selectDitchStatistics(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectMoneyUserNumber(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectMoneyStatistics(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectCashUserNumber(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectCashStatistics(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectPayAll(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectPaySuccess(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String, Object>> selectPayClosed(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
