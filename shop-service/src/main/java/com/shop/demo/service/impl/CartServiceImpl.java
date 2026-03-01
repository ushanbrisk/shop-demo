package com.shop.demo.service.impl;

import com.shop.demo.common.BusinessException;
import com.shop.demo.dao.mapper.CartMapper;
import com.shop.demo.dao.mapper.ProductMapper;
import com.shop.demo.model.entity.Cart;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.vo.ProductVO;
import com.shop.demo.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车Service实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> getCartList(Long userId) {
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        return cartList.stream().map(cart -> {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null) {
                return null;
            }
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);
            vo.setStock(cart.getQuantity());
            return vo;
        }).filter(vo -> vo != null).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(Long userId, Long productId, Integer quantity) {
        // 检查商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        // 检查商品是否下架
        if (product.getStatus() == 0) {
            throw new BusinessException("商品已下架");
        }

        // 检查库存
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }

        // 检查购物车是否已存在该商品
        Cart existCart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (existCart != null) {
            // 更新数量
            existCart.setQuantity(existCart.getQuantity() + quantity);
            cartMapper.updateById(existCart);
        } else {
            // 新增购物车项
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cartMapper.insert(cart);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCartQuantity(Long cartId, Integer quantity) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null) {
            throw new BusinessException("购物车项不存在");
        }

        // 检查库存
        Product product = productMapper.selectById(cart.getProductId());
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }

        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCartItem(Long cartId) {
        cartMapper.deleteById(cartId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        cartMapper.deleteByUserId(userId);
    }
}
