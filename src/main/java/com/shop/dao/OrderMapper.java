package com.shop.dao;

import com.shop.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public  interface OrderMapper {
    List<Order> SelectAllOrder(Order order);
    int BorrowInsert(Order order);
    //    查询订单
    List<Order> SelectOrderAllById(Order order);
    int UpdateOrder(Order order);
    Order SelectOrderByOrderID(Integer order_id);
    List<Order> SelectOrderAndCart(Order order);

}
