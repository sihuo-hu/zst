package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述：交易记录表模型
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
@TableName("b_transaction_record")
@Data
public class TransactionRecord implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;
    /**
     *
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 产品编码
     */
    @TableField("symbol_code")
    private String symbolCode;
    /**
     * 建仓时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 手数
     */
    @TableField("lot")
    private Integer lot;
    /**
     * 建仓成本
     */
    @TableField("unit_price")
    private Integer unitPrice;
    /**
     * 金额
     */
    @TableField("money")
    private Integer money;
    /**
     * 过夜费
     */
    @TableField("overnight_fee")
    private BigDecimal overnightFee;
    /**
     * 手续费
     */
    @TableField("commission_charges")
    private BigDecimal commissionCharges;
    /**
     * 交易状态 1建仓 2平仓 3挂单 4取消
     */
    @TableField("transaction_status")
    private Integer transactionStatus;
    /**
     * 当时的指数（价格）
     */
    @TableField("exponent")
    private BigDecimal exponent;
    /**
     * 平仓价格
     */
    @TableField("close_out_price")
    private BigDecimal closeOutPrice;
    /**
     * 挂单价格
     */
    @TableField("entry_orders_price")
    private BigDecimal entryOrdersPrice;
    /**
     * 挂单浮动点位
     */
    @TableField("error_range")
    private Integer errorRange;
    /**
     * 挂单最低价
     */
    @TableField("entry_orders_strat_price")
    private BigDecimal entryOrdersStratPrice;
    /**
     * 挂单最高价
     */
    @TableField("entry_orders_end_price")
    private BigDecimal entryOrdersEndPrice;
    /**
     * 平仓时间
     */
    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 止损指数（价格）
     */
    @TableField("stop_loss_exponent")
    private BigDecimal stopLossExponent;
    /**
     * 止盈指数（价格）
     */
    @TableField("stop_profit_exponent")
    private BigDecimal stopProfitExponent;
    /**
     * 止损点数
     */
    @TableField("stop_loss_count")
    private Integer stopLossCount;
    /**
     * 止盈点数
     */
    @TableField("stop_profit_count")
    private Integer stopProfitCount;

    /**
     * 1买涨 2买跌
     */
    @TableField("ransaction_type")
    private Integer ransactionType;

    /**
     * 1过夜 2不过夜
     */
    @TableField("is_overnight")
    private Integer isOvernight;
    /**
     * 盈利
     */
    @TableField("profit")
    private BigDecimal profit;
    /**
     * 挂单时间
     */
    @TableField("entry_orders_time")
    private Date entryOrdersTime;
    /**
     * 用户代金券ID
     */
    @TableField("user_cash_coupon_id")
    private String userCashCouponId;
    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;
    /**
     * 数量
     */
    @TableField("sell_status")
    private String sellStatus;

    private String symbolName;

}