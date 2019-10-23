package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.CashCoupon;
import cn.stylefeng.guns.modular.system.entity.SymbolInfo;
import cn.stylefeng.guns.modular.system.mapper.CashCouponMapper;
import cn.stylefeng.guns.modular.system.mapper.SymbolInfoMapper;
import cn.stylefeng.guns.modular.system.model.SymbolDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class SymbolInfoService extends ServiceImpl<SymbolInfoMapper, SymbolInfo> {


    public List<SymbolDto> getSymbolCodeAndSymbolName() {
        return this.baseMapper.getSymbolCodeAndSymbolName();
    }
}
