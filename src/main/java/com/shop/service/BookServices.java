package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.Cart;
import com.shop.pojo.Order;

import java.util.List;

public interface BookServices {

    PageInfo<Book> SelectAll(Integer Pagenum, Integer Pagesize, Book book);

    int Insertbook(Book book);
    int DeleteBook(Integer id);
    List<Book> SelectBook(Book book);
    int UpdateBook(Book book);
    Book SelectBookById(Integer id);
    Integer BookBuy(Book book, Card card, Order order, Cart cart);
}
