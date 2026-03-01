package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.vo.ProductVO;
import com.shop.demo.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 购物车Controller
 */
@Api(tags = "购物车管理")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @ApiOperation("获取购物车列表")
    @GetMapping
    public Result<List<ProductVO>> getCartList(@RequestHeader("X-User-Id") Long userId) {
        return Result.success(cartService.getCartList(userId));
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping
    public Result<Void> addToCart(@RequestHeader("X-User-Id") Long userId,
                                   @RequestBody Map<String, Object> params) {
        Long productId = Long.valueOf(params.get("productId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());
        cartService.addToCart(userId, productId, quantity);
        return Result.success();
    }

    @ApiOperation("更新购物车商品数量")
    @PutMapping("/{cartId}")
    public Result<Void> updateCartQuantity(@PathVariable Long cartId,
                                           @RequestBody Map<String, Integer> params) {
        cartService.updateCartQuantity(cartId, params.get("quantity"));
        return Result.success();
    }

    @ApiOperation("删除购物车商品")
    @DeleteMapping("/{cartId}")
    public Result<Void> deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
        return Result.success();
    }

    @ApiOperation("清空购物车")
    @DeleteMapping
    public Result<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return Result.success();
    }
}
