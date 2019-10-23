package cn.stylefeng.guns.modular.system.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawExcel {
    private String id;

    private String loginName;

    private String userName;

    private String orderTime;

    private BigDecimal sponsorMoney;

    private BigDecimal commission;

    private BigDecimal money;

    private String bankCard;

    private String bankOfDeposit;

    private String branch;

    private String errorMsg;

    private String payStatus;
}
