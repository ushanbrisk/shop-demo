package com.shop.demo.dao.mapper;

import com.shop.demo.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper {

    /**
     * 根据ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息
     */
    Order selectById(@Param("id") Long id);

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * 插入订单
     *
     * @param order 订单信息
     * @return 影响行数
     */
    int insert(Order order);

    /**
     * 更新订单
     *
     * @param order 订单信息
     * @return 影响行数
     */
    int updateById(Order order);

    /**
     * 删除订单（逻辑删除）
     *
     * @param id 订单ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
