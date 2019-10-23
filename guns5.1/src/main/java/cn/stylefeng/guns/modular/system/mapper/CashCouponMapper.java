package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  代金券表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface CashCouponMapper extends BaseMapper<CashCoupon> {


    Page<Map<String, Object>> selectCashCoupon(@Param("page")Page page,@Param("beginTime") String beginTime, @Param("endTime")String endTime);

    void setStatus(@Param("id") String id,@Param("status") String checked);
}
