package cn.stylefeng.guns.modular.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：用户代金券模型
 *
 * @author Royal
 * @date 2019年05月23日 19:41:18
 */
@TableName("b_user_cash_coupon")
@Data
public class DitchCoupon implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 代金券ID
     */
    @TableField("cash_coupon_id")
    private String cashCouponId;
    /**
     *
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 发放时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 过期时间
     */
    @TableField("past_due_time")
    private String pastDueTime;
    /**
     * 发放说明
     */
    @TableField("grant_explain")
    private String grantExplain;

    /**
     * 金额
     */
    @TableField("cc_money")
    private BigDecimal ccMoney;

    /**
     * 生效时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 代金券使用ID
     */
    @TableField("order_id")
    private String orderId;

    @TableField("cash_coupon_push_log_id")
    private String cashCouponPushLogId;

    @TableField(exist = false)
    private String userCreateTime;

    @TableField(exist = false)
    private String ccName;

    @TableField(exist = false)
    private String pushTime;

    @TableField(exist = false)
    private String useStatus;

    @TableField(exist = false)
    private BigDecimal profit;

    @TableField(exist = false)
    private String ditch;
}