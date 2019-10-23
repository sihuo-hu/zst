package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：开关模型
 *
 * @author Royal
 * @date 2019年02月21日 16:36:03
 */
@Data
@TableName("p_switch")
public class Switch implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * ANDROID/IOS
     */
    @TableField("platform")
    private String platform;
    /**
     * 渠道
     */
    @TableField("ditch")
    private String ditch;
    /**
     * 版本号
     */
    @TableField("versions")
    private String versions;
    /**
     * 交易
     */
    @TableField("transaction")
    private String transaction;
    /**
     * 充值
     */
    @TableField("capital")
    private String capital;

    @TableField("create_time")
    private String createTime;

}