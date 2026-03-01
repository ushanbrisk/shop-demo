package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.vo.ProductVO;
import com.shop.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品Controller
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping
    public Result<List<ProductVO>> getProductList() {
        return Result.success(productService.getProductList());
    }

    @GetMapping("/{id}")
    public Result<ProductVO> getProduct(@PathVariable Long id) {
        return Result.success(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryId}")
    public Result<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return Result.success(productService.getProductListByCategory(categoryId));
    }

    @GetMapping("/search")
    public Result<List<Product>> searchProducts(@RequestParam String keyword) {
        return Result.success(productService.searchProducts(keyword));
    }

    @PostMapping
    public Result<Void> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success();
    }
}
