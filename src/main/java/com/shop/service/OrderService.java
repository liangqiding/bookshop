package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.pojo.Order;

import java.util.List;


public interface OrderService {
    PageInfo<Order> SelectOrderAllById(Integer pageNum, Integer pageSize, Order order);
    List<Order> SelectOrderAllById(Order order);
    int UpdateOrder(Order order);
    List<Order> SelectAllOrder(Order order);
    PageInfo<Order> SelectAllOrderPageInfo(Integer PageNum,Integer PageSize,Order order);
    Order SelectOrderByOrderID(Integer order_id);
    List<Order> SelectOrderAndCart(Order order);

}
