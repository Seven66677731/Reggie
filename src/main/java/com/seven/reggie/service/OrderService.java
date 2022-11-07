package com.seven.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.reggie.pojo.Orders;


public interface OrderService extends IService<Orders> {

    void  submit(Orders orders);

}
