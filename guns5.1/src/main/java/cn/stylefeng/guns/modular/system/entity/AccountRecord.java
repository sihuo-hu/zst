package cn.stylefeng.guns.modular.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述：资金记录模型
 *
 * @author Royal
 * @date 2019年03月05日 13:28:09
 */
@Data
@TableName("b_amount_record")
public class AccountRecord implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     *
     */
    @TableField( "login_name")
    private String loginName;
    /**
     * 金额，单位美元
     */
    @TableField( "money")
    private BigDecimal money;
    /**
     * 手续费
     */
    @TableField( "commission")
    private BigDecimal commission;
    /**
     * 支付单号
     */
    @TableField( "pay_no")
    private String payNo;
    /**
     * 支付状态 TRADE_SUCCESS-成功 WAIT_BUYER_PAY-交易创建，等待付款  NEW_ORDER-新订单 UNKNOWN-不明确的交易 TRADE_CLOSED-交易关闭 TRADE_REFUND-退货
     */
    @TableField( "pay_status")
    private String payStatus;
    /**
     * 交易类型 PAY-支付 WITHDRAWAL-提现
     */
    @TableField( "amount_type")
    private String amountType;
    /**
     * 支付时间
     */
    @TableField( "pay_time")
    private String payTime;
    /**
     * 下单时间
     */
    @TableField( "order_time")
    private String orderTime;
    /**
     * 人民币金额
     */
    @TableField( "rmb_money")
    private BigDecimal rmbMoney;
    /**
     * 支付说明
     */
    @TableField( "pay_msg")
    private String payMsg;
    /**
     * 支付方式 1:微信，2:支付宝，3:云闪付
     */
    @TableField( "pay_way")
    private String payWay;
    /**
     * 批次ID
     */
    @TableField( "batch_id")
    private String batchId;

    @TableField(exist = false)
    private String returnUrl;

}