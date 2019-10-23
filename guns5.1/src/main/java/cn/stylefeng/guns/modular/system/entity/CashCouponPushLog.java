package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("b_cash_coupon_push_log")
@Data
public class CashCouponPushLog {
    /**
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 优惠券ID
     */
    @TableField("cash_coupon_id")
    private String cashCouponId;
    /**
     * 发放时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 操作人
     */
    @TableField("operator")
    private String operator;
    /**
     * ALL_USER全部  PART_USER部分
     */
    @TableField("target_population")
    private String targetPopulation;
    /**
     * 部分人的ID
     */
    @TableField("login_names")
    private String loginNames;
    @TableField("push_count")
    private Integer pushCount;

    @TableField(exist = false)
    private String startTimeNew;


}
