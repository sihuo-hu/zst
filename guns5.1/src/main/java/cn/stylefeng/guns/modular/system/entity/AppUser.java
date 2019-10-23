package cn.stylefeng.guns.modular.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 描述：用户模型
* @author Royal
* @date 2018年12月04日 14:19:32
*/
@Data
@TableName("b_user")
public class AppUser implements Serializable {

    /**
    *
    */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;
    /**
    *登录名
    */
    @TableField("login_name")
    private String loginName;
    /**
    *密码
    */
    @TableField("password")
    private String password;
    /**
    *创建时间
    */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    *用户名
    */
    @TableField("user_name")
    private String userName;
    /**
     * 公钥
     */
    @TableField("public_key")
    private String publicKey;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField("user_img")
    private String userImg;
    /**
     * 性别 0女 1男
     */
    @TableField("gender")
    private String gender;
    /**
     * 出生日期
     */
    @TableField("birthdate")
    private String birthdate;
    /**
     * 身份证号
     */
    @TableField("id_number")
    private String idNumber;
    /**
     * 身份证正面图片地址
     */
    @TableField("card_front")
    private String cardFront;
    /**
     * 身份证反面图片地址
     */
    @TableField("card_reverse")
    private String cardReverse;

    /**
     * 审核状态 DONT_SUBMIT:未提交,NO_AUDIT:未审核,VERIFIED:审核通过,REJECTED:审核不通过
     */
    @TableField("audit_status")
    private String auditStatus;
    /**
     * 银行卡号
     */
    @TableField("bank_card")
    private String bankCard;
    /**
     * 开户行
     */
    @TableField("bank_of_deposit")
    private String bankOfDeposit;
    /**
     * 分行
     */
    @TableField("branch")
    private String branch;

    @TableField("user_status")
    private String userStatus;

}