package com.seven.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.reggie.mapper.AddressBookMapper;
import com.seven.reggie.pojo.AddressBook;
import com.seven.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author 22600
 */

@Service

public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
