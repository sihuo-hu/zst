package com.royal.entity.json;

import lombok.Data;

/**
 * 中付支付请求体
 */
@Data
public class ZFPayRequest {

    /**
     * 平台分配商户号
     */
    private String zfcno;
    /**
     * 上送订单号唯一, 字符长度32
     */
    private String zfmorderno;
    /**
     * 时间戳,精确到秒(中国时区)
     */
    private String zfaddtime;
    /**
     * 参考附录1说明
     */
    private String paytype;
    /**
     * 服务端返回地址.（POST返回数据）
     */
    private String notifyurl;
    /**
     * 页面跳转返回地址（POST返回数据）
     */
    private String returnurl;
    /**
     * 商品金额,两位小数
     */
    private String zfsamount;
    /**
     * 请看MD5签名字段格式
     */
    private String autograph;

    /**
     * 附加字段
     */
    private String remark;
}
