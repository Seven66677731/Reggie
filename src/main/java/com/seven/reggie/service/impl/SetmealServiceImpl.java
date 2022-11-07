package com.seven.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.reggie.common.CustomException;
import com.seven.reggie.dto.SetmealDto;
import com.seven.reggie.mapper.SetmealMapper;
import com.seven.reggie.pojo.Setmeal;
import com.seven.reggie.pojo.SetmealDish;
import com.seven.reggie.service.SetmealDishService;
import com.seven.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 22600
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品的关联信息
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保持套餐的基本信息，操作setmeal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        //保存套餐和菜品的关联信息
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }


    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(Setmeal::getId, ids);
        lqw1.eq(Setmeal::getStatus, 1);

        if (this.count(lqw1) > 0) {
            throw new CustomException("套餐正在售卖中，无法删除");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(lqw2);

    }

    @Override
    public SetmealDto getWithDish(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(lqw);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }


    @Override
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        //保存套餐和菜品的关联信息
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.updateBatchById(setmealDishes);

    }
}