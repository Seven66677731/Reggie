package com.seven.reggie.dto;

import com.seven.reggie.pojo.Setmeal;
import com.seven.reggie.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
