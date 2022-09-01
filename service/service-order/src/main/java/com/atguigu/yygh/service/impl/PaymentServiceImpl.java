package com.atguigu.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.HospitalFeignClient;
import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.HttpRequestHelper;
import com.atguigu.yygh.mapper.PaymentInfoMapper;
import com.atguigu.yygh.service.OrderService;
import com.atguigu.yygh.service.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import enums.OrderStatusEnum;
import enums.PaymentStatusEnum;
import model.order.OrderInfo;
import model.order.PaymentInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.order.SignInfoVo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-31-17:15
 */
@Service
public class PaymentServiceImpl extends
        ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HospitalFeignClient hospitalFeignClient;
    /**
     * 保存交易记录
     *
     * @param orderInfo
     * @param paymentType 支付类型（1：微信 2：支付宝）
     */
    @Override
    public void savePaymentInfo(OrderInfo orderInfo, Integer paymentType) {
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderInfo.getId());
        queryWrapper.eq("payment_type", paymentType);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) return;
        // 保存交易记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());
        String subject = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + "|" + orderInfo.getHosname() + "|" + orderInfo.getDepname() + "|" + orderInfo.getTitle();
        paymentInfo.setSubject(subject);
        paymentInfo.setTotalAmount(orderInfo.getAmount());
        baseMapper.insert(paymentInfo);
    }

    //更新订单状态
    @Override
    public void paySuccess(String outTradeNo, Integer paymentType, Map<String, String> paramMap) {
        //1.根据订单编号得到支付记录
        PaymentInfo paymentInfo = this.getPaymentInfo(outTradeNo, paymentType);
        if (null == paymentInfo) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        if (paymentInfo.getPaymentStatus() != PaymentStatusEnum.UNPAID.getStatus()) {
            return;
        }
        //2.更新支付信息,修改支付状态
        PaymentInfo paymentInfoUpd = new PaymentInfo();
        paymentInfoUpd.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfoUpd.setTradeNo(paramMap.get("transaction_id"));
        paymentInfoUpd.setCallbackTime(new Date());
        paymentInfoUpd.setCallbackContent(paramMap.toString());
        this.updatePaymentInfo(outTradeNo, paymentInfoUpd);
        //3.获取订单信息，修改订单状态
        OrderInfo orderInfo = orderService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderService.updateById(orderInfo);
        //5.调用医院接口，通知更新支付状态
        SignInfoVo signInfoVo
                = hospitalFeignClient.getSignInfoVo(orderInfo.getHoscode());
        if (null == signInfoVo) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hoscode", orderInfo.getHoscode());
        reqMap.put("hosRecordId", orderInfo.getHosRecordId());
        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
        String sign = HttpRequestHelper.getSign(reqMap, signInfoVo.getSignKey());
        reqMap.put("sign", sign);
        JSONObject result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.getApiUrl() + "/order/updatePayStatus");
        if (result.getInteger("code") != 200) {
            throw new YyghException(result.getString("message"), ResultCodeEnum.FAIL.getCode());
        }

    }

    @Override
    public PaymentInfo getPaymentInfo(Long orderId, Integer paymentType) {
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        queryWrapper.eq("payment_type", paymentType);
        return baseMapper.selectOne(queryWrapper);
    }

    private void updatePaymentInfo(String outTradeNo, PaymentInfo paymentInfoUpd) {
        QueryWrapper<PaymentInfo> wr = new QueryWrapper<>();
        wr.eq("out_trade_no",outTradeNo);
        baseMapper.update(paymentInfoUpd,wr);
    }

    private PaymentInfo getPaymentInfo(String outTradeNo, Integer paymentType) {
        QueryWrapper<PaymentInfo> qu = new QueryWrapper<>();
        qu.eq("out_trad_no",outTradeNo);
        qu.eq("payment_type",paymentType);
        return baseMapper.selectOne(qu);

    }
}
