package com.atguigu.yygh.service;

import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-31-17:45
 */
public interface WeixinService {
    /**
     * 根据订单号下单，生成支付链接扫描二维码
     */
    Map createNative(Long orderId);
    /**
     * 根据订单号去微信第三方查询支付状态
     */
    Map<String,String> queryPayStatus(Long orderId, String paymentType);
    /***
     * 退款
     * @param orderId
     * @return
     */
    Boolean refund(Long orderId);
}
