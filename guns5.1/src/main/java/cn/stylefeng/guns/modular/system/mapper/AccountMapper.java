package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.AccountRecord;
import cn.stylefeng.guns.modular.system.model.WithdrawExcel;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface AccountMapper extends BaseMapper<AccountRecord> {

    /**
     * 查询充值统计
     *
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String, Object> selectPayStatisticsAmountRecord(@Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 查询充值详情列表
     *
     * @param page
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    Page<Map<String, Object>> selectPayAmountRecord(@Param("page") Page page, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime);


    /**
     * 查询提现列表
     *
     * @param name
     * @param beginTime
     * @param endTime
     * @param batchId
     * @return
     */
    Page<Map<String, Object>> selectWithdrawAmountRecord(@Param("page") Page page, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("batchId") String batchId);

    /**
     * 提现统计
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String, Object> selectWithdrawStatisticsAmountRecord(@Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 根据ID查出需要提现的记录
     * @param idList
     * @return
     */
    List<WithdrawExcel> selectByIds(@Param("idList") List<String> idList);

    /**
     * 根据ID获取提现详情
     * @param id
     * @return
     */
    WithdrawExcel getWithdrawExcelById(@Param("id") String id);
}
