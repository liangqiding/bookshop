package com.shop.service;

import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.Order;

public interface BookBorrow {
    int BorrowInsert(Order order);
    Integer BorrowBack(Book book, Card card, Order order);

}
