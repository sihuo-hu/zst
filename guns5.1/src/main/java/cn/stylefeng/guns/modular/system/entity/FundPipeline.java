package cn.stylefeng.guns.modular.system.entity;

import cn.stylefeng.guns.core.util.DateUtils;
import cn.stylefeng.guns.core.util.Tools;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：资金流水日志模型
 *
 * @author Royal
 * @date 2019年06月18日 16:36:40
 */
@TableName("b_fund_pipeline")
@Data
public class FundPipeline implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 流水类型 pay 充值 withdraw 提现 buy 建仓 sell 平仓
     */
    @TableField("pipeline_type")
    private String pipelineType;
    /**
     *
     */
    @TableField("money")
    private BigDecimal money;
    /**
     *
     */
    @TableField("balance")
    private BigDecimal balance;
    /**
     *
     */
    @TableField("login_name")
    private String loginName;
    /**
     *
     */
    @TableField("order_id")
    private String orderId;
    /**
     *
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 收入或支出
     */
    @TableField("income_or_expenditure")
    private String incomeOrExpenditure;

    public FundPipeline() {
        super();
    }

    public FundPipeline(String pipelineType, BigDecimal money, BigDecimal balance, String loginName, String orderId, String incomeOrExpenditure) {
        this.id = Tools.getRandomCode(32,7);
        this.pipelineType = pipelineType;
        this.money = money;
        this.balance = balance;
        this.loginName = loginName;
        this.orderId = orderId;
        this.createTime = DateUtils.getCurrDateTimeStr();
        this.incomeOrExpenditure = incomeOrExpenditure;
    }
}