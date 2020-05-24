package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.pojo.Card;

public interface UserServices {
    PageInfo<Card> SelectUserAllManagePageInfo();
}
