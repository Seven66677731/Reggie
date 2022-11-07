package com.seven.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.reggie.pojo.Category;

public interface CategoryService  extends IService<Category> {
    void remove(Long id);
}
