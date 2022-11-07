package com.seven.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.reggie.dto.SetmealDto;
import com.seven.reggie.pojo.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时保存套餐和菜品的关联信息
     */
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

    SetmealDto getWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
