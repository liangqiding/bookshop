package com.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.OrderMapper;
import com.shop.dao.UserMapper;
import com.shop.pojo.Order;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    public PageInfo<Order> SelectOrderAllById(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> o=orderMapper.SelectOrderAllById(order);
        PageInfo<Order> pageInfoOrder=new PageInfo<>(o);
        return pageInfoOrder;
    }
    public List<Order> SelectOrderAllById(Order order){
        return orderMapper.SelectOrderAllById(order);
    }

    @Override
    public int UpdateOrder(Order order) {
        return orderMapper.UpdateOrder(order);
    }

    @Override
    public List<Order> SelectAllOrder(Order order) {
        return orderMapper.SelectAllOrder(order);
    }
    @Override
    public PageInfo<Order> SelectAllOrderPageInfo(Integer PageNum,Integer PageSize,Order order) {
        PageHelper.startPage(PageNum,PageSize);
        List<Order> orders = orderMapper.SelectAllOrder(order);
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;
    }

    @Override
    public Order SelectOrderByOrderID(Integer order_id) {
        return orderMapper.SelectOrderByOrderID(order_id);
    }

    @Override
    public List<Order> SelectOrderAndCart(Order order) {
        return orderMapper.SelectOrderAndCart(order);
    }


}
