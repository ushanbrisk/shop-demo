package com.shop.demo.service;

import com.shop.demo.model.entity.Product;
import com.shop.demo.model.vo.ProductVO;

import java.util.List;

/**
 * 商品Service接口
 */
public interface ProductService {

    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    List<ProductVO> getProductList();

    /**
     * 根据ID查询商品
     *
     * @param id 商品ID
     * @return 商品信息
     */
    ProductVO getProductById(Long id);

    /**
     * 根据分类ID查询商品列表
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> getProductListByCategory(Long categoryId);

    /**
     * 搜索商品
     *
     * @param keyword 关键字
     * @return 商品列表
     */
    List<Product> searchProducts(String keyword);

    /**
     * 添加商品
     *
     * @param product 商品信息
     */
    void addProduct(Product product);

    /**
     * 更新商品
     *
     * @param product 商品信息
     */
    void updateProduct(Product product);

    /**
     * 删除商品
     *
     * @param id 商品ID
     */
    void deleteProduct(Long id);
}
