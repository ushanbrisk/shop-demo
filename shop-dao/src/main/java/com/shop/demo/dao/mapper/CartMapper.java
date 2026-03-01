package com.shop.demo.dao.mapper;

import com.shop.demo.model.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车Mapper接口
 */
@Mapper
public interface CartMapper {

    /**
     * 根据用户ID查询购物车列表
     *
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<Cart> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和商品ID查询购物车项
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 购物车项
     */
    Cart selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 插入购物车项
     *
     * @param cart 购物车项
     * @return 影响行数
     */
    int insert(Cart cart);

    /**
     * 更新购物车项数量
     *
     * @param cart 购物车项
     * @return 影响行数
     */
    int updateById(Cart cart);

    /**
     * 删除购物车项
     *
     * @param id 购物车ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 清空用户购物车
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(@Param("userId") Long userId);
}
