package cn.stylefeng.guns.modular.system.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccountInfo {
    private String loginName;

    private String userName;

    private BigDecimal rechargeAmount;

    private BigDecimal withdrawAmount;

    private BigDecimal balance;

    private String bankCard;

    private String bankOfDeposit;

    private String branch;
}
