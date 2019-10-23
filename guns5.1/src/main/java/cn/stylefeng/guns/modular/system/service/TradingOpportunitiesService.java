package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.core.util.Tools;
import cn.stylefeng.guns.modular.system.entity.AppUser;
import cn.stylefeng.guns.modular.system.entity.TradingOpportunities;
import cn.stylefeng.guns.modular.system.mapper.AppUserMapper;
import cn.stylefeng.guns.modular.system.mapper.TradingOpportunitiesMapper;
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
public class TradingOpportunitiesService extends ServiceImpl<TradingOpportunitiesMapper, TradingOpportunities> {

    /**
     * 修改用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:53
     */
    public void editUser(TradingOpportunities user) {
        this.updateById (user);
    }

    public Page<Map<String, Object>> selectTradingOpportunities(String name, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage ();
        return this.baseMapper.selectTradingOpportunities (page, name,beginTime, endTime);
    }

    public void addTradingOpportunities(TradingOpportunities user) {
        user.setId(Tools.getRandomCode(11,3));
        user.setCreateTime(DateUtils.getCurrDateTimeStr());
        this.baseMapper.insert (user);
    }

    public void setStatus(int id, String opportuntiesStatusTpl) {
        TradingOpportunities t = this.baseMapper.selectById(id);
        t.setOStatus(opportuntiesStatusTpl);
        this.baseMapper.updateById(t);
    }
}
