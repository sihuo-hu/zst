package com.royal.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 描述：支付通道开关模型
* @author Royal
* @date 2019年07月08日 16:35:25
*/
@Data
@Table(name="p_pay_switch")
public class PaySwitch implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *支付类型
    */
    @Column(name = "pay_type")
    private String payType;
    /**
    *open开 close关
    */
    @Column(name = "pay_switch")
    private String paySwitch;
    /**
    *支付类型编码
    */
    @Column(name = "pay_code")
    private String payCode;
    /**
    *支付通道
    */
    @Column(name = "ditch")
    private String ditch;
    /**
    *是否支持小数 1支持 2不支持
    */
    @Column(name = "decimals")
    private Integer decimals;
    /**
    *
    */
    @Column(name = "max_money")
    private BigDecimal maxMoney;
    /**
    *
    */
    @Column(name = "min_money")
    private BigDecimal minMoney;
    /**
    *
    */
    @Column(name = "pay_url")
    private String payUrl;

}