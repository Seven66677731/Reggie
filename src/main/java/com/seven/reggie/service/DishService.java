package com.seven.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.reggie.dto.DishDto;
import com.seven.reggie.pojo.Dish;

public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish，dish_flavor
     */
    void saveWithFlavor(DishDto dishDto);


    /**
     * 根据id查询对应的菜品信息和对应的口味信息
     */
    DishDto  getByIdWithFlavor(Long id);

    /**
     * 更新对应的菜品信息和对应的口味信息
     */
    void updateWithFlavor(DishDto dishDto);
}
