package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* 描述：代金券模型
* @author Royal
* @date 2019年05月23日 19:35:09
*/
@TableName("b_cash_coupon")
public class CashCoupon implements Serializable {

    /**
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
    */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    /**
    * 名称
    */
    @TableField("cc_name")
    private String ccName;
    /**
    * 金额
    */
    @TableField("cc_money")
    private BigDecimal ccMoney;
    /**
    *使用范围ID
    */
    @TableField("cc_scope_id")
    private String ccScopeId;
    /**
    *发放方式  AUTO自动，MANUAL手动
    */
    @TableField("grant_mode")
    private String grantMode;
    /**
    *统一过期时间
    */
    @TableField("past_due_time")
    private String pastDueTime;
    /**
    *统一过期天数
    */
    @TableField("past_due_day")
    private Integer pastDueDay;
    /**
    *说明
    */
    @TableField("cc_explain")
    private String ccExplain;

    /**
     * 过期方式 UNIFY统一失效 DIFFERENT按用户领取日期失效
     */
    @TableField("past_due_mode")
    private String pastDueMode;
    /**
    *生效时间
    */
    @TableField("start_time")
    private String startTime;
    /**
    *
    */
    @TableField("create_time")
    private String createTime;
    /**
    *发放条件，自动发放才需要此处   NEW_CUSTOMER新用户  FIRST_RECHARGE首次充值赠送
    */
    @TableField("grant_condition")
    private String grantCondition;
    /**
    *达标金额
    */
    @TableField("amount_to_mark")
    private BigDecimal amountToMark;
    /**
     *ENABLE:启用,DISABLE:禁用
     */
    @TableField("cc_status")
    private String ccStatus;

    public String getCcStatus() {
        return ccStatus;
    }

    public void setCcStatus(String ccStatus) {
        this.ccStatus = ccStatus;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


     public String getCcName() {
        return this.ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }


     public BigDecimal getCcMoney() {
        return this.ccMoney;
    }

    public void setCcMoney(BigDecimal ccMoney) {
        this.ccMoney = ccMoney;
    }


     public String getCcScopeId() {
        return this.ccScopeId;
    }

    public void setCcScopeId(String ccScopeId) {
        this.ccScopeId = ccScopeId;
    }


     public String getGrantMode() {
        return this.grantMode;
    }

    public void setGrantMode(String grantMode) {
        this.grantMode = grantMode;
    }


     public String getPastDueTime() {
        return this.pastDueTime;
    }

    public void setPastDueTime(String pastDueTime) {
        this.pastDueTime = pastDueTime;
    }


    public Integer getPastDueDay() {
        return this.pastDueDay;
    }

    public void setPastDueDay(Integer pastDueDay) {
        this.pastDueDay = pastDueDay;
    }

    public String getCcExplain() {
        return ccExplain;
    }

    public void setCcExplain(String ccExplain) {
        this.ccExplain = ccExplain;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


     public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


     public String getGrantCondition() {
        return this.grantCondition;
    }

    public void setGrantCondition(String grantCondition) {
        this.grantCondition = grantCondition;
    }


     public BigDecimal getAmountToMark() {
        return this.amountToMark;
    }

    public void setAmountToMark(BigDecimal amountToMark) {
        this.amountToMark = amountToMark;
    }

    public String getPastDueMode() {
        return pastDueMode;
    }

    public void setPastDueMode(String pastDueMode) {
        this.pastDueMode = pastDueMode;
    }

    @Override
    public String toString() {
        return "CashCoupon{" +
                "id='" + id + '\'' +
                ", ccName='" + ccName + '\'' +
                ", ccMoney=" + ccMoney +
                ", ccScopeId='" + ccScopeId + '\'' +
                ", grantMode='" + grantMode + '\'' +
                ", pastDueTime='" + pastDueTime + '\'' +
                ", pastDueDay=" + pastDueDay +
                ", ccExplain='" + ccExplain + '\'' +
                ", pastDueMode=" + pastDueMode +
                ", startTime='" + startTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", grantCondition='" + grantCondition + '\'' +
                ", amountToMark=" + amountToMark +
                '}';
    }
}