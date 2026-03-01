package com.shop.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.shop.demo.common.BusinessException;
import com.shop.demo.dao.mapper.*;
import com.shop.demo.model.entity.Cart;
import com.shop.demo.model.entity.Order;
import com.shop.demo.model.entity.OrderItem;
import com.shop.demo.model.entity.Product;
import com.shop.demo.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(Long userId, String receiverName, String receiverPhone, String receiverAddress) {
        // 查询用户购物车
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        if (cartList == null || cartList.isEmpty()) {
            throw new BusinessException("购物车为空");
        }

        // 校验库存并计算总价
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cart : cartList) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null) {
                throw new BusinessException("商品不存在: " + cart.getProductId());
            }
            if (product.getStock() < cart.getQuantity()) {
                throw new BusinessException("商品库存不足: " + product.getName());
            }
            if (product.getStatus() == 0) {
                throw new BusinessException("商品已下架: " + product.getName());
            }
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
        }

        // 生成订单编号
        String orderNo = "ORD" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss") + (int) (Math.random() * 10000);

        // 创建订单
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setTotalAmount(totalAmount);
        order.setStatus(0);
        orderMapper.insert(order);

        // 创建订单明细
        List<OrderItem> orderItems = cartList.stream().map(cart -> {
            Product product = productMapper.selectById(cart.getProductId());
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setProductImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setQuantity(cart.getQuantity());
            item.setTotalPrice(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            return item;
        }).collect(Collectors.toList());
        orderItemMapper.batchInsert(orderItems);

        // 扣减库存
        for (Cart cart : cartList) {
            Product product = productMapper.selectById(cart.getProductId());
            product.setStock(product.getStock() - cart.getQuantity());
            productMapper.updateById(product);
        }

        // 清空购物车
        cartMapper.deleteByUserId(userId);

        return orderNo;
    }

    @Override
    public List<Order> getOrderList(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public Order getOrderDetail(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId, Integer payType) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态不正确");
        }

        order.setStatus(1);
        order.setPayType(payType);
        order.setPayTime(DateUtil.date().toLocalDateTime());
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态不正确，无法取消");
        }

        // 恢复库存
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
        for (OrderItem item : orderItems) {
            Product product = productMapper.selectById(item.getProductId());
            product.setStock(product.getStock() + item.getQuantity());
            productMapper.updateById(product);
        }

        // 删除订单明细
        orderItemMapper.deleteByOrderId(orderId);

        // 删除订单
        orderMapper.deleteById(orderId);
    }
}
