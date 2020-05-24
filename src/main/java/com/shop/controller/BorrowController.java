package com.shop.controller;

import com.shop.dao.UserMapper;
import com.shop.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.FKUser;
import com.shop.pojo.Order;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class BorrowController{
    @Autowired
    private BookBorrowImpl bookBorrowImol;
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private CardServiceImpl cardServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderServiceImpl orderService;
//借阅
    @RequestMapping("Borrow")
    public String Borrow(Integer id, HttpServletRequest request, HttpSession session) {
        try{session.setAttribute("bookid", id);
            Book book= bookServiceImpl.SelectBookById(id);
            String name=book.getName();
            String zuozhe=book.getZuozhe();
            double price=book.getPrice();
            Integer keep=book.getKeep();
            request.setAttribute("bookname",name);
            request.setAttribute("bookzuozhe",zuozhe);
            request.setAttribute("bookprice",price);
            request.setAttribute("bookkeep",keep);}
            catch (Exception e){
            System.out.println("借阅模块出错了！");
        }

        return "borrow";
    }

    //    borrowmain
    @RequestMapping("borrowmain")
    public String BorrowMain(Order order, Integer selectclass, Integer selectdata, HttpSession session, Model model) {
        String userid = "11";
        Integer user_id=0;
        Integer id=0;
        Integer sum=0;
        Integer user_cardid=0;
        System.out.println("order_name"+order.getOrder_name());
        if (session.getAttribute("bookid")!=null) {
            id = (int) session.getAttribute("bookid");
        }
        try {
            //        验证借阅卡号
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser=new FKUser();
            fkUser.setUsername(name);
//            获取当前用户信息
            FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        获取借阅数量
            user_cardid =fkUser1.getUser_cardid();
            System.out.println(user_cardid);
             sum = userMapper.SelectCardByCID(user_cardid).getSum();

        }catch (Exception e){
            System.out.println("借阅失败");
            session.setAttribute("Borrow",2);
            return "bookstudent";
        }

//设置更新借阅总数
        Card card = new Card();
        card.setSum(sum+1);
        card.setCard_id(user_cardid);



//        设置时间格式
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//当前日期
        order.setDate(sf.format(d.getTime()));
//        设置orderid
        order.setBook_id(id);
        order.setOrder_cardid(user_cardid);

//     设置订单号
        Integer size=orderService.SelectAllOrder(null).size()+2020000;
        order.setOrder_id(size);

        if (selectdata == 1) {
            order.setLongtime(7);
            order.setDate(sf.format(d.getTime()));
            order.setReturndate(sf.format(d.getTime() + 7 * 24 * 60 * 60 * 1000L));
        } else if (selectdata == 2) {
            order.setLongtime(14);
            order.setDate(sf.format(d.getTime()));
            order.setReturndate(sf.format(d.getTime() + 14 * 24 * 60 * 60 * 1000L));
        } else if (selectdata == 3) {
            order.setLongtime(30);
            order.setDate(sf.format(d.getTime()));
            order.setReturndate(sf.format(d.getTime() + 30 * 24 * 60 * 60 * 1000L));
        } else {
            order.setLongtime(60);
            order.setDate(sf.format(d.getTime()));
            order.setReturndate(sf.format(d.getTime() + 60 * 24 * 60 * 60 * 1000L));
        }
        if (selectclass != 1) {
            order.setOrder_price(10);
            order.setPost("是");

        }else {
            order.setPost("否");
            order.setAddress("自取");
            order.setOrder_price(0);
        }

        Book book1 = bookServiceImpl.SelectBookById(id);
        if( bookServiceImpl.SelectBookById(id).getKeep() > 0) {
            System.out.println("bookServiceImpl");
            Book book = new Book();
            if (book1.getKeep()==1){
                book.setState("不可借阅");
            }
            book.setBookid(id);
            book.setKeep(book1.getKeep()-1);
            System.out.println("库存："+book.getKeep());
            book.setBook_out(book1.getBook_out()+1);
//            开始执行修改数据库
            Integer b= bookBorrowImol.Borrow(book, order, card);

            if(b>0) {
                session.setAttribute("Borrow",1);
                    System.out.println("借阅成功！" + b);
                }else {
                System.out.println("借阅失败！" + b);
                session.setAttribute("Borrow",2);
            }
        }else {
            System.out.println("借阅失败！");
            session.setAttribute("Borrow",2);
            return "bookstudent";
        }

        return "bookstudent";
    }
//    /BorrowBooks
    @RequestMapping("/BorrowAjax")
    @ResponseBody
    public int BorrowBooks(HttpSession session){
        int borrow=0;
        try{
            borrow = (Integer) session.getAttribute("Borrow");
            session.removeAttribute("Borrow");
        }catch (Exception e){
            borrow=0;
        }
        return borrow;
    }

//    归还图书BorrowBack
@RequestMapping("/BorrowBack")
public String BorrowBack(Integer order_cardid,Integer book_id,HttpSession session,Model model,HttpServletRequest request ){
    System.out.println("orderid:"+order_cardid+"   bookid:"+book_id);
    Order order = new Order();
    order.setOrder_cardid(order_cardid);
    order.setState("已归还");
    Card card = new Card();
    card.setCard_id(order_cardid);
    Integer sum = userMapper.SelectCardByCID(order_cardid).getSum();
    if (sum<1){
        session.setAttribute("BackAJAX",2);
        model.addAttribute("LoginName",getnane());
        model.addAttribute("User_cardid",getUser_cardid());
        System.out.println("归还失败");
        System.out.println("你无须归还图书！");
        return "order";
    }
    card.setSum(sum-1);
    Book book = new Book();
    Book book1 = userMapper.SelectBookById(book_id);
    Integer book_out = book1.getBook_out();
    int keep = book1.getKeep();
    book.setBook_out(book_out-1);
    book.setState("可借阅");
    book.setKeep(keep+1);

    Integer integer = bookBorrowImol.BorrowBack(book, card, order);
    if(integer>0){
        session.setAttribute("BackAJAX",1);
        System.out.println("归还成功");
        model.addAttribute("LoginName",getnane());
        model.addAttribute("User_cardid",getUser_cardid());

    }else {
        session.setAttribute("BackAJAX",2);
        System.out.println("归还sbai");
        model.addAttribute("LoginName",getnane());
        model.addAttribute("User_cardid",getUser_cardid());
        request.setAttribute("LoginName",getnane());
    }
    return "order";
}

    @RequestMapping("/BorrowBackAJAX")
    @ResponseBody
    public Integer BorrowBackAJAX(HttpSession session){
    Integer backAJAX=0;
    try {
      backAJAX = (Integer) session.getAttribute("BackAJAX");
      session.removeAttribute("BackAJAX");
    }catch (Exception e){
        backAJAX=0;
    }
        System.out.println("BackAJAX="+backAJAX);
        return backAJAX;
    }


    public String getnane() {

        try {
            String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
            return loginName;
        }catch (Exception e){

        }

       return null;
    }
    public Integer getUser_cardid() {
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
