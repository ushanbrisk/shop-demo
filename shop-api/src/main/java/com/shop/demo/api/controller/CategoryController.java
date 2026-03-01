package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.entity.Category;
import com.shop.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类Controller
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> getCategoryList() {
        return Result.success(categoryService.getCategoryList());
    }

    @GetMapping("/{id}")
    public Result<Category> getCategory(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    public Result<Void> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
