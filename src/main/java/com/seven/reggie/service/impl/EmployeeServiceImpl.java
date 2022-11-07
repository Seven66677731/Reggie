package com.seven.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.reggie.mapper.EmployeeMapper;
import com.seven.reggie.pojo.Employee;
import com.seven.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author 22600
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
