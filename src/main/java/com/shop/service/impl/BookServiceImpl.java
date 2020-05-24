package com.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.Cart;
import com.shop.pojo.Order;
import com.shop.service.BookServices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BookServiceImpl implements BookServices {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private  BookBorrowImpl bookBorrow;
//    查询图书
@Autowired
private CartServiceImp cartServiceImp;
    public PageInfo<Book> SelectAll(Integer Pagenum, Integer Pagesize, Book book) {
        PageHelper.startPage(Pagenum, Pagesize);

        List<Book> a = userMapper.SelectAll(book);
        PageInfo<Book> bookPageInfo = new PageInfo<>(a);
        return bookPageInfo;
    }

    @Override
    public int Insertbook(Book book) {
        return userMapper.Insertbook(book);
    }

    @Override
    public int DeleteBook(Integer id) {
        return userMapper.DeleteBook(id);
    }

    @Override
    public List<Book> SelectBook(Book book) {
        return userMapper.SelectBook(book);
    }


    @Override
    public int UpdateBook(Book book) {
        return userMapper.UpdateBook(book);
    }

    @Override
    public Book SelectBookById(Integer id) {
        return userMapper.SelectBookById(id);
    }


//购买
    @Transactional
    public Integer BookBuy(Book book, Card card, Order order, Cart cart){
/**
 * 1.修改图书状态
 * 2.修改用户信息状态
 * 3.生成订单
 */
        bookService.UpdateBook(book);
        userMapper.UpdateCard(card);
//        生成大订单
        int a=bookBorrow.BorrowInsert(order);
//        生成子订单
        int b=cartServiceImp.CartInserts(cart);
        return a;
    }

}
