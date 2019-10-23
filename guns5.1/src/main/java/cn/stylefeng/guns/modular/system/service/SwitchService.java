package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.core.util.Tools;
import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.entity.Switch;
import cn.stylefeng.guns.modular.system.mapper.AccountMapper;
import cn.stylefeng.guns.modular.system.mapper.SwitchMapper;
import cn.stylefeng.guns.modular.system.model.WithdrawExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 开关管理 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class SwitchService extends ServiceImpl<SwitchMapper, Switch> {

    public Page<Map<String, Object>> selectSwitch(String ditch) {
        Page page = LayuiPageFactory.defaultPage();
        QueryWrapper<Switch> queryWrapper = new QueryWrapper<>();
        if ("未知".equals(ditch)) {
            queryWrapper.isNull("ditch");
        } else if (Tools.notEmpty(ditch)) {
            queryWrapper.eq("ditch", ditch);
        }
        return (Page<Map<String, Object>>) this.baseMapper.selectMapsPage(page, queryWrapper);
    }

    public void setStatus(String id, String checked, String switchType) {
        Switch appSwitch = this.baseMapper.selectById(id);
        if("transaction".equals(switchType)){
            appSwitch.setTransaction(checked);
        }else{
            appSwitch.setCapital(checked);
        }
        this.baseMapper.updateById(appSwitch);
    }

    public void addCashCoupon(Switch appSwitch) {
        appSwitch.setCreateTime(DateUtils.getCurrDateTimeStr());
        this.baseMapper.insert(appSwitch);
    }
}
