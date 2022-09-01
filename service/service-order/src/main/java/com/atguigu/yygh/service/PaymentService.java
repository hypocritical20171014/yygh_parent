package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import model.order.OrderInfo;
import model.order.PaymentInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-31-17:14
 */
public interface PaymentService extends IService<PaymentInfo> {
    /**
     * 保存交易记录
     * @param order
     * @param paymentType 支付类型（1：微信 2：支付宝）
     */
    void savePaymentInfo(OrderInfo order, Integer paymentType);

    void paySuccess(String outTradeNo, Integer paymentType, Map<String, String> paramMap);

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
