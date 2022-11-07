package com.seven.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.reggie.common.R;
import com.seven.reggie.dto.SetmealDto;
import com.seven.reggie.pojo.Category;
import com.seven.reggie.pojo.Setmeal;
import com.seven.reggie.service.CategoryService;
import com.seven.reggie.service.SetmealDishService;
import com.seven.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息{}", setmealDto);

        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }


    /**
     * 套餐分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dishDtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Setmeal::getName, name);
        lqw.orderByDesc(Setmeal::getUpdateTime);

        //执行查询
        setmealService.page(pageInfo, lqw);

        //拷贝对象
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");


        //实际返回的page对象
        List<Setmeal> records = pageInfo.getRecords();


        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }

            return setmealDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @DeleteMapping
    @Transactional
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);

        setmealService.removeWithDish(ids);

        return R.success("套餐删除成功");
    }


    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        log.info("status:{}", status);
        log.info("ids:{}", ids);
        Setmeal setmeal = new Setmeal();
        for (Long id : ids) {
            setmeal.setId(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }

        return R.success("修改状态成功");
    }

    /**
     * 根据id查询对应的菜品信息和口味信息
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {


        SetmealDto setmealDto = setmealService.getWithDish(id);

        return R.success(setmealDto);
    }


    /**
     * 修改套餐信息
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("{}", setmealDto);
        setmealService.updateWithDish(setmealDto);
        return R.success("修改成功");
    }

    /**
     * 根据条件查询套餐数据
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        lqw.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lqw);
        return R.success(list);


    }
}
