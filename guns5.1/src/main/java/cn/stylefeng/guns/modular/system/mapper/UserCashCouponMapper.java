package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.UserCashCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  代金券表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface UserCashCouponMapper extends BaseMapper<UserCashCoupon> {

    Page<Map<String, Object>> selectUserCashCoupon(@Param("page") Page page,@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("loginName") String loginName);

    Map<String, Object> selectUserCashCouponStatistics(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String, Object> selectUnused(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String, Object> selectUsed(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String, Object> selectPastDue(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String, Object> selectProfit(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Map<String, Object> selectLoss(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
