package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.entity.UserAmount;
import cn.stylefeng.guns.modular.system.mapper.AccountMapper;
import cn.stylefeng.guns.modular.system.mapper.UserAmountMapper;
import cn.stylefeng.guns.modular.system.model.UserAccountInfo;
import cn.stylefeng.guns.modular.system.model.WithdrawExcel;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
public class UserAccountService extends ServiceImpl<UserAmountMapper, UserAmount> {

    public UserAccountInfo getAccountInfoById(String loginName) {
        return this.baseMapper.getAccountInfoById(loginName);
    }
}
