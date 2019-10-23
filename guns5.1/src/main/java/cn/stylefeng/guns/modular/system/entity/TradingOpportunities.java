package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 描述：交易机会模型
* @author Royal
* @date 2019年02月14日 21:09:16
*/
@TableName("b_trading_opportunities")
@Data
public class TradingOpportunities implements Serializable {

    /**
    *
    */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
    *专家头像
    */
    @TableField("user_img")
    private String userImg;
    /**
    *专家名字
    */
    @TableField("user_name")
    private String userName;

    /**
     *产品名称
     */
    @TableField("symbol_name")
    private String symbolName;
    /**
     *操作方式
     */
    @TableField("operating_mode")
    private String operatingMode;
    /**
    *发布时间
    */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    /**
    *标题
    */
    @TableField("title")
    private String title;
    /**
    *标题下方核心主题
    */
    @TableField("theme_text")
    private String themeText;
    /**
    *买涨百分比
    */
    @TableField("rise_percentage")
    private Integer risePercentage;
    /**
    *基本分析
    */
    @TableField("foundation_analysis")
    private String foundationAnalysis;
    /**
    *技术面分析
    */
    @TableField("technological_analysis")
    private String technologicalAnalysis;
    /**
    *技术面分析图片
    */
    @TableField("technological_analysis_img")
    private String technologicalAnalysisImg;

    /**
     * ENABLE启用 DISABLE停用
     */
    @TableField("opportunties_status")
    private String oStatus;

}