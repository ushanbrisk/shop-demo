package com.shop.demo.dao.mapper;

import com.shop.demo.model.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单明细Mapper接口
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 根据订单ID查询订单明细列表
     *
     * @param orderId 订单ID
     * @return 订单明细列表
     */
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 插入订单明细
     *
     * @param orderItem 订单明细
     * @return 影响行数
     */
    int insert(OrderItem orderItem);

    /**
     * 批量插入订单明细
     *
     * @param orderItems 订单明细列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<OrderItem> orderItems);

    /**
     * 删除订单明细
     *
     * @param orderId 订单ID
     * @return 影响行数
     */
    int deleteByOrderId(@Param("orderId") Long orderId);
}
