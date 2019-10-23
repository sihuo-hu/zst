package com.royal.entity.json;

import lombok.Data;

@Data
public class ZFPayNotify {
    /**
     * 金额
     */
    private String amount;
    /**
     * 时间
     */
    private String datetime;
    /**
     * 商户号
     */
    private String memberid;
    /**
     *  商户订单号
     */
    private String orderid;
    /**
     * 支付渠道
     */
    private String paytype;
    /**
     * CODE编码
     */
    private String returncode;
    /**
     *  附加字段
     */
    private String attach;
    /**
     * 签名
     */
    private String sign;
    /**
     * 平台订单号
     */
    private String HForderid;

}
