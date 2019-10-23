package com.royal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 描述：交易机会模型
 *
 * @author Royal
 * @date 2019年02月14日 21:09:16
 */
@Data
@Table(name = "b_trading_opportunities")
public class TradingOpportunities implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private String id;
    /**
     * 专家头像
     */
    @Column(name = "user_img")
    private String userImg;
    /**
     * 专家名字
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 产品名称
     */
    @Column(name = "symbol_name")
    private String symbolName;
    /**
     * 操作方式
     */
    @Column(name = "operating_mode")
    private String operatingMode;
    /**
     * 发布时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 标题下方核心主题
     */
    @Column(name = "theme_text")
    private String themeText;
    /**
     * 买涨百分比
     */
    @Column(name = "rise_percentage")
    private Integer risePercentage;
    /**
     * 基本分析
     */
    @Column(name = "foundation_analysis")
    private String foundationAnalysis;
    /**
     * 技术面分析
     */
    @Column(name = "technological_analysis")
    private String technologicalAnalysis;
    /**
     * 技术面分析图片
     */
    @Column(name = "technological_analysis_img")
    private String technologicalAnalysisImg;

    /**
     * ENABLE启用 DISABLE停用
     */
    @Column(name = "opportunties_status")
    private String opportuntiesStatus;

}