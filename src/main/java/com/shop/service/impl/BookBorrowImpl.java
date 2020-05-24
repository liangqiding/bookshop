package com.shop.service.impl;

import com.shop.dao.OrderMapper;
import com.shop.dao.UserMapper;
import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.Order;
import com.shop.service.BookBorrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class BookBorrowImpl implements BookBorrow {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private CartServiceImp cartServiceImp;
 

    @Override
    public int BorrowInsert(Order order) {
        return orderMapper.BorrowInsert(order);
    }

    //借阅
    @Transactional
    public Integer Borrow(Book book, Order order, Card card) {

        int b = 0;

        try {
            bookService.UpdateBook(book);
            userMapper.UpdateCard(card);
            b = BorrowInsert(order);
//            int a = 1 / 0;
        } catch (Exception e) {
            System.out.println("捕捉异常/0");
            b = 0;
//            手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new RuntimeException();
        }
        System.out.println("userMapper.UpdateCard(card);" + b);
        return b;
    }
//    购买
    @Override
    public Integer BorrowBack(Book book,Card card,Order order){
/**
 *       1.更新图书信息
 *       2.更新借阅卡信息
 *       3.更新订单信息
 */
        bookService.UpdateBook(book);
        userMapper.UpdateCard(card);
        int a=orderService.UpdateOrder(order);
        return a;
    }

}
