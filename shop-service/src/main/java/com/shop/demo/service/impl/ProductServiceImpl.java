package com.shop.demo.service.impl;

import com.shop.demo.common.BusinessException;
import com.shop.demo.dao.mapper.ProductMapper;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.vo.ProductVO;
import com.shop.demo.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品Service实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> getProductList() {
        return productMapper.selectVOList();
    }

    @Override
    public ProductVO getProductById(Long id) {
        ProductVO product = productMapper.selectVOById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        return product;
    }

    @Override
    public List<Product> getProductListByCategory(Long categoryId) {
        return productMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productMapper.search(keyword);
    }

    @Override
    public void addProduct(Product product) {
        product.setStatus(1);
        productMapper.insert(product);
    }

    @Override
    public void updateProduct(Product product) {
        Product exist = productMapper.selectById(product.getId());
        if (exist == null) {
            throw new BusinessException("商品不存在");
        }
        productMapper.updateById(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product exist = productMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException("商品不存在");
        }
        productMapper.deleteById(id);
    }
}
