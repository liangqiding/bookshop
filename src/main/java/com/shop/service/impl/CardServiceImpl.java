package com.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Card;
import com.shop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UserMapper userMapper;
    public int CardInsert(Card card) {
        return userMapper.CardInsert(card);
    }

    @Override
    public PageInfo<Card> SelectCardPageInfo(Integer Pagenum, Integer Pagesize,Integer card_id) {
        PageHelper.startPage(Pagenum,Pagesize);
        List<Card> u=userMapper.SelectCardPageInfo(card_id);
        PageInfo<Card> pageInfoCard=new PageInfo<>(u);
        return pageInfoCard;
    }
}
