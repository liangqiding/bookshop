package com.shop.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Card;
import com.shop.service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

   @Autowired
    private UserMapper userMapper;

    public PageInfo<Card> SelectUserAllManagePageInfo(){
        PageHelper.startPage(1,100);
        List<Card> c=userMapper.SelectUserAllManagePageInfo();
        PageInfo<Card> cardPageInfo=new PageInfo<>(c);
        return cardPageInfo;
    }
}
