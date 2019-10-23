package com.royal.util;

import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PayRequest;
import com.royal.entity.json.Result;
import com.royal.entity.json.ZFPayNotify;
import com.royal.entity.json.ZFPayRequest;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 中付支付
 */
public class ZFPayUtil {
    /**
     * 发起支付
     *
     * @param payWay    支付宝	alipay
     *                  微信扫码	weixin
     *                  微信H5	wxh5
     *                  在线网银	wangyin
     *                  支付宝H5	alipayh5
     *                  快捷支付	kuaijie
     *                  银联扫码	ylsm
     * @param money     美元
     * @param uuid      ID
     * @param returnUrl 支付回调
     * @return
     * @throws Exception
     */
    public static String pay(String payWay, BigDecimal money, String uuid, String returnUrl) throws Exception {

        ZFPayRequest zfPayRequest = new ZFPayRequest();
        zfPayRequest.setZfcno(Constants.ZFPayConstants.MER_ID);
        zfPayRequest.setZfmorderno(uuid);
        zfPayRequest.setZfaddtime(DateUtils.getUnix());
        zfPayRequest.setPaytype(payWay);
        zfPayRequest.setNotifyurl(Constants.ZFPayConstants.NOTIFY_URL);
        zfPayRequest.setReturnurl(returnUrl);
        zfPayRequest.setZfsamount(money.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        zfPayRequest.setAutograph(sign(zfPayRequest));
        String json = HttpUtils.doPost(Constants.ZFPayConstants.URL_ADDRESS, JSONUtils.objectToMapString(zfPayRequest));
        return json;
    }

    public static String selectOrder(String orderId) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        map.put("customerid",Constants.ZFPayConstants.MER_ID);
        map.put("sdorderno",orderId);
        map.put("reqtime",DateUtils.getCurrDateTimeSeries());
        map.put("sign",orderSign(map));
        String json = HttpUtils.doPost(Constants.ZFPayConstants.SELECT_URL_ADDRESS, map);
        return json;
    }

    public static String formatParam(ZFPayRequest zfPayRequest) {
        String params = "";
        StringBuffer sb = new StringBuffer();
        if (Tools.notEmpty(zfPayRequest.getZfcno())) {
            sb.append("zfcno=" + zfPayRequest.getZfcno());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getNotifyurl())) {
            sb.append("notifyurl=" + zfPayRequest.getNotifyurl());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getZfaddtime())) {
            sb.append("zfaddtime=" + zfPayRequest.getZfaddtime());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getPaytype())) {
            sb.append("paytype=" + zfPayRequest.getPaytype());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getReturnurl())) {
            sb.append("returnurl=" + zfPayRequest.getReturnurl());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getZfmorderno())) {
            sb.append("zfmorderno=" + zfPayRequest.getZfmorderno());
            sb.append("&");
        }
        if (Tools.notEmpty(zfPayRequest.getZfsamount())) {
            sb.append("zfsamount=" + zfPayRequest.getZfsamount());
            sb.append("&");
        }
        sb.append("token=");
        sb.append(Constants.ZFPayConstants.TOKEN);
        params = sb.toString();
        System.out.println("需要签名的串：" + params);
        return params;
    }

    public static String sign(ZFPayRequest zfPayRequest) {
        String sign = MD5.md5(formatParam(zfPayRequest));
        return sign.toUpperCase();
    }

    public static String orderSign(Map<String,String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("customerid=" + map.get("customerid"));
        sb.append("&");
        sb.append("sdorderno=" + map.get("sdorderno"));
        sb.append("&");
        sb.append("reqtime=" + map.get("reqtime"));
        sb.append("&");
        sb.append(Constants.ZFPayConstants.TOKEN);
        String sign = MD5.md5(sb.toString());
        return sign.toLowerCase();
    }
    public static void main(String[] args) {
        String uuid = Tools.getRandomCode(32, 7);
        try {
            String json = (String) pay("alipay", new BigDecimal("18"), uuid, "http://www.baidu.com");
            System.out.println("返回：" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println(Tools.unicodeToString(selectOrder("e8250cb4fb2347c4a18c412cb219fb03")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //e8250cb4fb2347c4a18c412cb219fb03


        //amount=0.00&
        // datetime=1562573445&
        // memberid=1000020028&
        // orderid=7e7df2e9f2b1451c9bbee1074fdee0e5&
        // paytype=alipay&
        // returncode=0&
        // attach=null&
        // sign=F2A55BBAD5260AE92149A04080A689FC
//        ZFPayNotify zFPayNotify = new ZFPayNotify();
//        zFPayNotify.setAmount("0.00");
//        zFPayNotify.setDatetime("1562573445");
//        zFPayNotify.setMemberid("1000020028");
//        zFPayNotify.setOrderid("7e7df2e9f2b1451c9bbee1074fdee0e5");
//        zFPayNotify.setPaytype("alipay");
//        zFPayNotify.setReturncode("0");
//        zFPayNotify.setSign("F2A55BBAD5260AE92149A04080A689FC");
//        System.out.println(verifySign(zFPayNotify));
    }

    /**
     * 验签
     *
     * @param zFPayNotify
     * @return
     */
    public static boolean verifySign(ZFPayNotify zFPayNotify) {
        if (Tools.isEmpty(zFPayNotify.getAmount(), zFPayNotify.getDatetime(), zFPayNotify.getMemberid(),
                zFPayNotify.getOrderid(), zFPayNotify.getPaytype(), zFPayNotify.getReturncode(),zFPayNotify.getSign())) {
            return false;
        }
        String params = "";
        StringBuffer sb = new StringBuffer();
        sb.append("amount=" + zFPayNotify.getAmount());
        sb.append("&");
        sb.append("datetime=" + zFPayNotify.getDatetime());
        sb.append("&");
        sb.append("memberid=" + zFPayNotify.getMemberid());
        sb.append("&");
        sb.append("orderid=" + zFPayNotify.getOrderid());
        sb.append("&");
        sb.append("paytype=" + zFPayNotify.getPaytype());
        sb.append("&");
        sb.append("returncode=" + zFPayNotify.getReturncode());
        sb.append("&");
        sb.append("token=" + Constants.ZFPayConstants.TOKEN);
        params = sb.toString();
        System.out.println("需要签名的串：" + params);
        String sign = MD5.md5(params).toUpperCase();
        System.out.println("SIGN:"+sign);
        return zFPayNotify.getSign().equals(sign);
    }
}
