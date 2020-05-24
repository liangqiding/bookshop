package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.FKUser;
import com.shop.pojo.Log;
import com.shop.pojo.Order;
import com.shop.service.impl.LogServiceImpl;
import com.shop.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class OrderController {
//    Order
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogServiceImpl logService;
    @RequestMapping("/order")
    public String order(Model model){
        model.addAttribute("LoginName",getnane());
        model.addAttribute("User_cardid",getUser_cardid());
        int user_cardid=0;
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser=new FKUser();
            fkUser.setUsername(name);
            user_cardid = userMapper.SelectFKUserByUserName(fkUser).getUser_cardid();
        }catch (Exception e){
            System.out.println("order：您还没登陆!");
        }
        Order order1 = new Order();
        order1.setOrder_cardid(user_cardid);

        PageInfo<Order> o=orderService.SelectOrderAllById(1,100,order1);
//        显示所有订单
//        List order=o.getList();
//        PageInfo<Order> pageInfo = orderService.SelectAllOrderPageInfo(1, 20,null);
        Order orders = new Order();
        //        所有订单
        List<Order> orderss = orderService.SelectOrderAndCart(orders);

        orders.setState("待发货");
          List<Order> orders1 = orderService.SelectOrderAndCart(orders);
        model.addAttribute("order1",orders1);
        orders.setState("待收货");
        List<Order> list2 =  orderService.SelectOrderAndCart(orders);
        model.addAttribute("order2",list2);
        orders.setState("交易成功");
        List<Order> list3 =  orderService.SelectOrderAndCart(orders);
        model.addAttribute("order3",list3);
        orders.setState("交易失败");
        List<Order> list4 =  orderService.SelectOrderAndCart(orders);
        model.addAttribute("order4",list4);
        model.addAttribute("order",orderss);

        return "order";
    }

    @RequestMapping("orders")
    @ResponseBody
    public List Order(HttpSession session, Model model){
     int user_cardid=0;
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser=new FKUser();
            fkUser.setUsername(name);
          user_cardid = userMapper.SelectFKUserByUserName(fkUser).getUser_cardid();
        }catch (Exception e){
            System.out.println("您还没登陆!");
        }
        Order order1 = new Order();
        order1.setOrder_cardid(user_cardid);
       PageInfo<Order> o=orderService.SelectOrderAllById(1,100,order1);
        List order=o.getList();
        System.out.println("OrderController");
        return order;
    }

//    /updatewuliu发货/取消订单
@RequestMapping("/updatewuliu")

public String updatewuliu(HttpSession session, Model model, Log log, Integer cancal){
    int user_cardid=0;
    Order order = new Order();
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    FKUser fkUser=new FKUser();
    fkUser.setUsername(name);
    try {
        user_cardid = userMapper.SelectFKUserByUserName(fkUser).getUser_cardid();
    }catch (Exception e){
        System.out.println("您还没登记个人信息!");
    }
    System.out.println("log:"+log);
    //       //当前日期 设置时间格式
    if(log!=null) {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        log.setLog_state("运输中");
        log.setLog_date(sf.format(d.getTime()));
//    新建物流订单
        if (logService.LogInsert(log) > 0) {
            System.out.println("发货成功");
            order.setOrder_id(log.getLog_orderid());
            order.setState("待收货");
//          更改订单物流信息
            orderService.UpdateOrder(order);
        }
        //        显示所以订单
    }
    if(cancal!=null){
        order.setState("交易失败");
        order.setOrder_id(cancal);
        orderService.UpdateOrder(order);
    }
    Order order1 = new Order();
    List<Order> orders = orderService.SelectOrderAndCart(order);
    model.addAttribute("order",orders);
    return "order";
}








    private String getnane() {

        try {
            String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
            return loginName;
        }catch (Exception e){

        }

        return null;
    }
    private Integer getUser_cardid() {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser1 = new FKUser();
            fkUser1.setUsername(name);
            FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
            Integer user_cardid = fkUser.getUser_cardid();
            return user_cardid;
        }catch (Exception e){

        }
        return  0;
    }
}
