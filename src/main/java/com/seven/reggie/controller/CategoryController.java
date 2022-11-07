package com.seven.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.reggie.common.R;
import com.seven.reggie.pojo.Category;
import com.seven.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 22600
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        //执行查询
        categoryService.page(pageInfo, lqw);

        return R.success(pageInfo);
    }

    /**
     * 根据id删除id
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除菜品：{}", ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 修改分类信息
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");

    }

    /**
     * 根据条件查询分类数据
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();

        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
