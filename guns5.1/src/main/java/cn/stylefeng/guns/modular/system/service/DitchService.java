package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.core.util.Tools;
import cn.stylefeng.guns.modular.system.entity.*;
import cn.stylefeng.guns.modular.system.mapper.DitchMapper;
import cn.stylefeng.guns.modular.system.mapper.UserCashCouponMapper;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 渠道统计 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class DitchService extends ServiceImpl<DitchMapper, DitchCoupon> {

    public List<Map<String, Object>> selectDitchStatistics(String beginTime, String endTime) {
        List<Map<String, Object>> mapList = this.baseMapper.selectDitchStatistics(beginTime, endTime);
        if (mapList == null || mapList.size() < 1) {
            return null;
        }
        List<Map<String, Object>> moneyUserNumberList = this.baseMapper.selectMoneyUserNumber(beginTime, endTime);
        Map<String, Map<String, Object>> moneyUserNumberMap = mapListToMap(moneyUserNumberList);

        List<Map<String, Object>> moneyStatisticsList = this.baseMapper.selectMoneyStatistics(beginTime, endTime);
        Map<String, Map<String, Object>> moneyStatisticsMap = mapListToMap(moneyStatisticsList);

        List<Map<String, Object>> cashUserNumberList = this.baseMapper.selectCashUserNumber(beginTime, endTime);
        Map<String, Map<String, Object>> cashUserNumberMap = mapListToMap(cashUserNumberList);

        List<Map<String, Object>> cashStatisticsList = this.baseMapper.selectCashStatistics(beginTime, endTime);
        Map<String, Map<String, Object>> cashStatisticsMap = mapListToMap(cashStatisticsList);

        List<Map<String, Object>> payAllList = this.baseMapper.selectPayAll(beginTime, endTime);
        Map<String, Map<String, Object>> payAllMap = mapListToMap(payAllList);

        List<Map<String, Object>> paySuccessList = this.baseMapper.selectPaySuccess(beginTime, endTime);
        Map<String, Map<String, Object>> paySuccessMap = mapListToMap(paySuccessList);

        List<Map<String, Object>> payClosedList = this.baseMapper.selectPayClosed(beginTime, endTime);
        Map<String, Map<String, Object>> payClosedMap = mapListToMap(payClosedList);

        for (Map<String, Object> stringObjectMap : mapList) {
            putAllMap(moneyUserNumberMap, stringObjectMap);
            putAllMap(moneyStatisticsMap, stringObjectMap);
            putAllMap(cashUserNumberMap, stringObjectMap);
            putAllMap(cashStatisticsMap, stringObjectMap);
            putAllMap(payAllMap, stringObjectMap);
            putAllMap(paySuccessMap, stringObjectMap);
            putAllMap(payClosedMap, stringObjectMap);
        }
        return mapList;
    }

    private void putAllMap(Map<String, Map<String, Object>> map, Map<String, Object> stringObjectMap) {
        String ditchName = stringObjectMap.get("ditch").toString();
        if (map != null) {
            if (map.get(ditchName) != null) {
                stringObjectMap.putAll(map.get(ditchName));
            }
        }
    }

    private Map<String, Map<String, Object>> mapListToMap(List<Map<String, Object>> mapList) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        if (mapList == null || mapList.size() < 1) {
            return null;
        }
        for (Map<String, Object> stringObjectMap : mapList) {
            map.put(stringObjectMap.get("ditch").toString(), stringObjectMap);
        }
        return map;
    }
}
