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
* 描述：用户模型
* @author Royal
* @date 2018年12月04日 14:19:32
*/
@Table(name="b_user")
@Data
public class User implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *登录名
    */
    @Column(name = "login_name")
    private String loginName;
    /**
    *密码
    */
    @Column(name = "password")
    private String password;
    /**
    *创建时间
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    *用户名
    */
    @Column(name = "user_name")
    private String userName;
    /**
     * 公钥
     */
    @Column(name="public_key")
    private String publicKey;

    /**
     * 昵称
     */
    @Column(name="nickname")
    private String nickname;

    /**
     * 头像
     */
    @Column(name="user_img")
    private String userImg;
    /**
     * 性别 0女 1男
     */
    @Column(name="gender")
    private String gender;
    /**
     * 出生日期
     */
    @Column(name="birthdate")
    private String birthdate;
    /**
     * 身份证号
     */
    @Column(name="id_number")
    private String idNumber;
    /**
     * 身份证正面图片地址
     */
    @Column(name="card_front")
    private String cardFront;
    /**
     * 身份证反面图片地址
     */
    @Column(name="card_reverse")
    private String cardReverse;

    /**
     * 审核状态 DONT_SUBMIT:未提交,NO_AUDIT:未审核,VERIFIED:审核通过,REJECTED:审核不通过
     */
    @Column(name="audit_status")
    private String auditStatus;
    /**
     * 银行卡号
     */
    @Column(name="bank_card")
    private String bankCard;
    /**
     * 开户行
     */
    @Column(name="bank_of_deposit")
    private String bankOfDeposit;

    /**
     * 分行
     */
    @Column(name="branch")
    private String branch;

    /**
     * 银行地址
     */
    @Column(name="bank_address")
    private String bankAddress;

    @Column(name="ry_token")
    private String ryToken;

    /**
     * 渠道
     */
    @Column(name="ditch")
    private String ditch;

    @Column(name="user_status")
    private String userStatus;
}