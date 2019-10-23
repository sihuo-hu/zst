package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：交易品种模型
 *
 * @author Royal
 * @date 2018年12月12日 17:05:27
 */
@TableName("b_symbol_info")
public class SymbolInfo implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField("symbol_code")
    private String symbolCode;
    /**
     *
     */
    @TableField("symbol_name")
    private String symbolName;
    /**
     * 1启用 2停用
     */
    @TableField("status")
    private Integer status;

    /**
     * 类别 1外汇 2贵金属 3原油 4全球指数
     */
    @TableField("symbol_type")
    private Integer symbolType;

    /**
     * 价格1
     */
    @TableField("unit_price_one")
    private Integer unitPriceOne;
    /**
     * 价格2
     */
    @TableField("unit_price_two")
    private Integer unitPriceTwo;
    /**
     * 价格3
     */
    @TableField("unit_price_three")
    private Integer unitPriceThree;
    /**
     * 数量1 如1盎司 20欧元
     */
    @TableField("quantity_one")
    private Integer quantityOne;
    /**
     * 数量2 如1盎司 20欧元
     */
    @TableField("quantity_two")
    private Integer quantityTwo;
    /**
     * 数量3 如1盎司 20欧元
     */
    @TableField("quantity_three")
    private Integer quantityThree;

    /**
     * 每份数量对应的手续费
     */
    @TableField("quantity_commission_charges_one")
    private BigDecimal quantityCommissionChargesOne;
    /**
     * 每份数量对应的手续费
     */
    @TableField("quantity_commission_charges_two")
    private BigDecimal quantityCommissionChargesTwo;
    /**
     * 每份数量对应的手续费
     */
    @TableField("quantity_commission_charges_three")
    private BigDecimal quantityCommissionChargesThree;
    /**
     * 每个数量的过夜费
     */
    @TableField("quantity_overnight_fee_one")
    private BigDecimal quantityOvernightFeeOne;
    /**
     * 每个数量的过夜费
     */
    @TableField("quantity_overnight_fee_two")
    private BigDecimal quantityOvernightFeeTwo;
    /**
     * 每个数量的过夜费
     */
    @TableField("quantity_overnight_fee_three")
    private BigDecimal quantityOvernightFeeThree;
    /**
     * 每个数量对应的波动价格
     */
    @TableField("quantity_price_fluctuation_one")
    private BigDecimal quantityPriceFluctuationOne;
    /**
     * 每个数量对应的波动价格
     */
    @TableField("quantity_price_fluctuation_two")
    private BigDecimal quantityPriceFluctuationTwo;
    /**
     * 每个数量对应的波动价格
     */
    @TableField("quantity_price_fluctuation_three")
    private BigDecimal quantityPriceFluctuationThree;

    /**
     * 挂单点位对应价格
     */
    @TableField("entry_orders")
    private BigDecimal entryOrders;

    /**
     * 1展示 2不展示
     */
    @TableField("symbol_show")
    private Integer symbolShow;

    /**
     * 今日最低
     */
    private BigDecimal minPrice;

    /**
     * 今日最高
     */
    private BigDecimal maxPrice;

    /**
     * 今开
     */
    private BigDecimal open;
    /**
     * 昨收
     */
    private BigDecimal close;

    private BigDecimal presentPrice;

    private boolean isTrading;

    public boolean isTrading() {
        return isTrading;
    }

    public void setTrading(boolean trading) {
        isTrading = trading;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getSymbolCode() {
        return this.symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }


    public String getSymbolName() {
        return this.symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }


    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(Integer symbolType) {
        this.symbolType = symbolType;
    }

    public Integer getUnitPriceOne() {
        return unitPriceOne;
    }

    public void setUnitPriceOne(Integer unitPriceOne) {
        this.unitPriceOne = unitPriceOne;
    }

    public Integer getUnitPriceTwo() {
        return unitPriceTwo;
    }

    public void setUnitPriceTwo(Integer unitPriceTwo) {
        this.unitPriceTwo = unitPriceTwo;
    }

    public Integer getUnitPriceThree() {
        return unitPriceThree;
    }

    public void setUnitPriceThree(Integer unitPriceThree) {
        this.unitPriceThree = unitPriceThree;
    }

    public Integer getQuantityOne() {
        return quantityOne;
    }

    public void setQuantityOne(Integer quantityOne) {
        this.quantityOne = quantityOne;
    }

    public Integer getQuantityTwo() {
        return quantityTwo;
    }

    public void setQuantityTwo(Integer quantityTwo) {
        this.quantityTwo = quantityTwo;
    }

    public Integer getQuantityThree() {
        return quantityThree;
    }

    public void setQuantityThree(Integer quantityThree) {
        this.quantityThree = quantityThree;
    }

    public BigDecimal getQuantityCommissionChargesOne() {
        return quantityCommissionChargesOne;
    }

    public void setQuantityCommissionChargesOne(BigDecimal quantityCommissionChargesOne) {
        this.quantityCommissionChargesOne = quantityCommissionChargesOne;
    }

    public BigDecimal getQuantityCommissionChargesTwo() {
        return quantityCommissionChargesTwo;
    }

    public void setQuantityCommissionChargesTwo(BigDecimal quantityCommissionChargesTwo) {
        this.quantityCommissionChargesTwo = quantityCommissionChargesTwo;
    }

    public BigDecimal getQuantityCommissionChargesThree() {
        return quantityCommissionChargesThree;
    }

    public void setQuantityCommissionChargesThree(BigDecimal quantityCommissionChargesThree) {
        this.quantityCommissionChargesThree = quantityCommissionChargesThree;
    }

    public BigDecimal getQuantityOvernightFeeOne() {
        return quantityOvernightFeeOne;
    }

    public void setQuantityOvernightFeeOne(BigDecimal quantityOvernightFeeOne) {
        this.quantityOvernightFeeOne = quantityOvernightFeeOne;
    }

    public BigDecimal getQuantityOvernightFeeTwo() {
        return quantityOvernightFeeTwo;
    }

    public void setQuantityOvernightFeeTwo(BigDecimal quantityOvernightFeeTwo) {
        this.quantityOvernightFeeTwo = quantityOvernightFeeTwo;
    }

    public BigDecimal getQuantityOvernightFeeThree() {
        return quantityOvernightFeeThree;
    }

    public void setQuantityOvernightFeeThree(BigDecimal quantityOvernightFeeThree) {
        this.quantityOvernightFeeThree = quantityOvernightFeeThree;
    }

    public BigDecimal getQuantityPriceFluctuationOne() {
        return quantityPriceFluctuationOne;
    }

    public void setQuantityPriceFluctuationOne(BigDecimal quantityPriceFluctuationOne) {
        this.quantityPriceFluctuationOne = quantityPriceFluctuationOne;
    }

    public BigDecimal getQuantityPriceFluctuationTwo() {
        return quantityPriceFluctuationTwo;
    }

    public void setQuantityPriceFluctuationTwo(BigDecimal quantityPriceFluctuationTwo) {
        this.quantityPriceFluctuationTwo = quantityPriceFluctuationTwo;
    }

    public BigDecimal getQuantityPriceFluctuationThree() {
        return quantityPriceFluctuationThree;
    }

    public void setQuantityPriceFluctuationThree(BigDecimal quantityPriceFluctuationThree) {
        this.quantityPriceFluctuationThree = quantityPriceFluctuationThree;
    }

    public BigDecimal getEntryOrders() {
        return entryOrders;
    }

    public void setEntryOrders(BigDecimal entryOrders) {
        this.entryOrders = entryOrders;
    }

    public Integer getSymbolShow() {
        return symbolShow;
    }

    public void setSymbolShow(Integer symbolShow) {
        this.symbolShow = symbolShow;
    }

    public BigDecimal getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(BigDecimal presentPrice) {
        this.presentPrice = presentPrice;
    }
}