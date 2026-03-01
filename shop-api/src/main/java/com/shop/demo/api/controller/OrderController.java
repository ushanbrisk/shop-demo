package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.entity.Order;
import com.shop.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping
    public Result<String> createOrder(@RequestHeader("X-User-Id") Long userId,
                                       @RequestBody Map<String, String> params) {
        String orderNo = orderService.createOrder(
                userId,
                params.get("receiverName"),
                params.get("receiverPhone"),
                params.get("receiverAddress")
        );
        return Result.success(orderNo);
    }

    @GetMapping
    public Result<List<Order>> getOrderList(@RequestHeader("X-User-Id") Long userId) {
        return Result.success(orderService.getOrderList(userId));
    }

    @GetMapping("/{orderId}")
    public Result<Order> getOrderDetail(@PathVariable Long orderId) {
        return Result.success(orderService.getOrderDetail(orderId));
    }

    @PutMapping("/{orderId}/pay")
    public Result<Void> payOrder(@PathVariable Long orderId,
                                 @RequestBody Map<String, Integer> params) {
        orderService.payOrder(orderId, params.get("payType"));
        return Result.success();
    }

    @DeleteMapping("/{orderId}")
    public Result<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return Result.success();
    }
}
