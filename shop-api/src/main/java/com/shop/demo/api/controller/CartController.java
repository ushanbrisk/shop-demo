package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.vo.ProductVO;
import com.shop.demo.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 购物车Controller
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping
    public Result<List<ProductVO>> getCartList(@RequestHeader("X-User-Id") Long userId) {
        return Result.success(cartService.getCartList(userId));
    }

    @PostMapping
    public Result<Void> addToCart(@RequestHeader("X-User-Id") Long userId,
                                   @RequestBody Map<String, Object> params) {
        Long productId = Long.valueOf(params.get("productId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());
        cartService.addToCart(userId, productId, quantity);
        return Result.success();
    }

    @PutMapping("/{cartId}")
    public Result<Void> updateCartQuantity(@PathVariable Long cartId,
                                           @RequestBody Map<String, Integer> params) {
        cartService.updateCartQuantity(cartId, params.get("quantity"));
        return Result.success();
    }

    @DeleteMapping("/{cartId}")
    public Result<Void> deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
        return Result.success();
    }

    @DeleteMapping
    public Result<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return Result.success();
    }
}
