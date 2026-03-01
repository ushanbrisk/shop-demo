package com.shop.demo.service;

import com.shop.demo.model.entity.Order;
import com.shop.demo.model.entity.OrderItem;

import java.util.List;

/**
 * 订单Service接口
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId         用户ID
     * @param receiverName   收货人姓名
     * @param receiverPhone  收货人电话
     * @param receiverAddress 收货地址
     * @return 订单编号
     */
    String createOrder(Long userId, String receiverName, String receiverPhone, String receiverAddress);

    /**
     * 查询用户订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getOrderList(Long userId);

    /**
     * 查询订单详情
     *
     * @param orderId 订单ID
     * @return 订单信息（含明细）
     */
    Order getOrderDetail(Long orderId);

    /**
     * 支付订单
     *
     * @param orderId  订单ID
     * @param payType  支付方式
     */
    void payOrder(Long orderId, Integer payType);

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     */
    void cancelOrder(Long orderId);
}
