package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.entity.SymbolInfo;
import cn.stylefeng.guns.modular.system.model.SymbolDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  产品表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface SymbolInfoMapper extends BaseMapper<SymbolInfo> {


    List<SymbolDto> getSymbolCodeAndSymbolName();
}
