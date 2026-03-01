package com.shop.demo.dao.mapper;

import com.shop.demo.model.entity.Product;
import com.shop.demo.model.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper接口
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据ID查询商品
     *
     * @param id 商品ID
     * @return 商品信息
     */
    Product selectById(@Param("id") Long id);

    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    List<Product> selectList();

    /**
     * 根据分类ID查询商品列表
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 搜索商品
     *
     * @param keyword 关键字
     * @return 商品列表
     */
    List<Product> search(@Param("keyword") String keyword);

    /**
     * 查询商品VO列表（带分类名称）
     *
     * @return 商品VO列表
     */
    List<ProductVO> selectVOList();

    /**
     * 根据ID查询商品VO
     *
     * @param id 商品ID
     * @return 商品VO
     */
    ProductVO selectVOById(@Param("id") Long id);

    /**
     * 插入商品
     *
     * @param product 商品信息
     * @return 影响行数
     */
    int insert(Product product);

    /**
     * 更新商品
     *
     * @param product 商品信息
     * @return 影响行数
     */
    int updateById(Product product);

    /**
     * 删除商品（逻辑删除）
     *
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
