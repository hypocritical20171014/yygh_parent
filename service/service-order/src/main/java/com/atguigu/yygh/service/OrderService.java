package com.atguigu.yygh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import model.order.OrderInfo;
import vo.order.OrderCountQueryVo;
import vo.order.OrderQueryVo;

import java.util.Map;

/**
 * @author Hefei
 * @create 2022-08-29-20:59
 */
public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId,Long patientId);
    //对订单列表进行分页查询
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);
    //获取订单详情
    OrderInfo getOrder(String orderId);

    //订单详情
    Map<String,Object> show(Long id);

    /**
     * 取消订单
     * @param orderId
     */
    Boolean cancelOrder(Long orderId);

    /**
     * 就诊提醒
     */
    void patientTips();

    /**
     * 订单统计
     */
    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
