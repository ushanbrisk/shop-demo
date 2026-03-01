package com.shop.demo.service;

import com.shop.demo.model.entity.Category;

import java.util.List;

/**
 * 分类Service接口
 */
public interface CategoryService {

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    List<Category> getCategoryList();

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    Category getCategoryById(Long id);

    /**
     * 添加分类
     *
     * @param category 分类信息
     */
    void addCategory(Category category);

    /**
     * 更新分类
     *
     * @param category 分类信息
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);
}
