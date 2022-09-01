package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import model.order.PaymentInfo;
import model.order.RefundInfo;

/**
 * @author Hefei
 * @create 2022-08-31-21:07
 */
public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
