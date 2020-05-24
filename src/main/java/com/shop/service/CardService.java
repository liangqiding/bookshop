package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.pojo.Card;

public interface CardService {
    int CardInsert(Card card);
    PageInfo<Card> SelectCardPageInfo(Integer Pagenum, Integer Pagesize,Integer card_id);
}
