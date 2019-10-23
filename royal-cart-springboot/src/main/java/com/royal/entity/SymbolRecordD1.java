package com.royal.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：产品历史记录模型
 *
 * @author Royal
 * @date 2019年07月01日 16:44:20
 */
@Data
@Table(name = "r_symbol_record_d1")
public class SymbolRecordD1 implements Serializable {

    public SymbolRecordD1(String marketTime, String symbolCode, BigDecimal symbolOpen, BigDecimal symbolClose, BigDecimal symbolMin, BigDecimal symbolMax, BigDecimal symbolTurnover) {
        this.marketTime = marketTime;
        this.symbolCode = symbolCode;
        this.symbolOpen = symbolOpen;
        this.symbolClose = symbolClose;
        this.symbolMin = symbolMin;
        this.symbolMax = symbolMax;
        this.symbolTurnover = symbolTurnover;
    }

    public SymbolRecordD1() {
        super();
    }
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
     * 行情时间
     */
    @Column(name = "market_time")
    private String marketTime;
    /**
     *
     */
    @Column(name = "symbol_code")
    private String symbolCode;
    /**
     * 开
     */
    @Column(name = "symbol_open")
    private BigDecimal symbolOpen;
    /**
     * 收
     */
    @Column(name = "symbol_close")
    private BigDecimal symbolClose;
    /**
     * 低
     */
    @Column(name = "symbol_min")
    private BigDecimal symbolMin;
    /**
     * 高
     */
    @Column(name = "symbol_max")
    private BigDecimal symbolMax;
    /**
     * 成交量
     */
    @Column(name = "symbol_turnover")
    private BigDecimal symbolTurnover;


}