package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.entity.Order;
import com.shop.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation("创建订单")
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

    @ApiOperation("获取订单列表")
    @GetMapping
    public Result<List<Order>> getOrderList(@RequestHeader("X-User-Id") Long userId) {
        return Result.success(orderService.getOrderList(userId));
    }

    @ApiOperation("获取订单详情")
    @GetMapping("/{orderId}")
    public Result<Order> getOrderDetail(@PathVariable Long orderId) {
        return Result.success(orderService.getOrderDetail(orderId));
    }

    @ApiOperation("支付订单")
    @PutMapping("/{orderId}/pay")
    public Result<Void> payOrder(@PathVariable Long orderId,
                                 @RequestBody Map<String, Integer> params) {
        orderService.payOrder(orderId, params.get("payType"));
        return Result.success();
    }

    @ApiOperation("取消订单")
    @DeleteMapping("/{orderId}")
    public Result<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return Result.success();
    }
}
