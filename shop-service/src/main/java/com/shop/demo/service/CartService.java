package com.shop.demo.service;

import com.shop.demo.model.entity.Cart;
import com.shop.demo.model.vo.ProductVO;

import java.util.List;

/**
 * 购物车Service接口
 */
public interface CartService {

    /**
     * 查询用户购物车列表
     *
     * @param userId 用户ID
     * @return 购物车列表（带商品信息）
     */
    List<ProductVO> getCartList(Long userId);

    /**
     * 添加商品到购物车
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param quantity  数量
     */
    void addToCart(Long userId, Long productId, Integer quantity);

    /**
     * 更新购物车商品数量
     *
     * @param cartId  购物车ID
     * @param quantity 数量
     */
    void updateCartQuantity(Long cartId, Integer quantity);

    /**
     * 删除购物车商品
     *
     * @param cartId 购物车ID
     */
    void deleteCartItem(Long cartId);

    /**
     * 清空用户购物车
     *
     * @param userId 用户ID
     */
    void clearCart(Long userId);
}
