package com.shop.controller;

import com.shop.dao.UserMapper;
import com.shop.pojo.*;
import com.shop.service.OrderService;
import com.shop.service.impl.BookServiceImpl;
import com.shop.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BuyController {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogServiceImpl logService;
//    点击商品进入详情
    @RequestMapping("buy")
    public String buy(Integer id, Model model, HttpSession session){
        Integer user_cardid=0;
        Book book = bookService.SelectBookById(id);
        model.addAttribute("buybook",book);
        System.out.println(book.toString());
        //        判断登陆状态
        Integer loginstate=0;
        try{
            loginstate = (Integer) session.getAttribute("loginstate");
        }catch (Exception e){
        }
        System.out.println("personal:"+loginstate);
        if(loginstate!=null&&loginstate!=0){
            System.out.println("已登录的用户！");
        }else {
            System.out.println("您还没登陆！");
            session.setAttribute("loginajax",0);
            return "login";
        }
        return "introduction";
    }

    @RequestMapping("buymain")
    public String buymain(Integer id,Integer sum){

        return "introduction";
    }

//结算页面 统计价格
    @RequestMapping("pay")
    public String pay(Integer bookid,Model model,HttpSession session)
    {

        Book book = bookService.SelectBookById(bookid);
        model.addAttribute("buybook",book);
        //        判断登陆状态
        Integer loginstate=0;
        try{
            loginstate = (Integer) session.getAttribute("loginstate");
        }catch (Exception e){
        }
        if(loginstate!=null&&loginstate!=0){
            System.out.println("已登录的用户！");
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser = new FKUser();
            fkUser.setUsername(name);
            FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
            Integer user_cardid = fkUser1.getUser_cardid();
            Card card = userMapper.SelectCardByCID(user_cardid);
            System.out.println("card"+card);
            if(card!=null){
                model.addAttribute("address",card.getAddress());
                model.addAttribute("phone",card.getPhone());
                model.addAttribute("name",card.getName());
            }

            model.addAttribute("card",card);
        }else {
            System.out.println("您还没登陆！");
            session.setAttribute("loginajax",0);
            return "login";
        }
        return "pay";


    }
    @RequestMapping("/successOK")
    public String string(Integer order_id,Model model){
        Order order = orderService.SelectOrderByOrderID(order_id);
        model.addAttribute("order",order);
        return "success";
    }
//success 支付结算 提交订单页面
@RequestMapping("/success")
@ResponseBody
public Integer uccess(HttpSession session,Integer bookid,Model model,@RequestParam(defaultValue = "1",value = "sum") Integer sum)
{
    /**
     * 1.修改图书状态
     * 2.修改用户信息状态
     * 3.生成订单
     */
    //        获得图书价格和库存
//        判断登陆状态
    System.out.println("bookid"+bookid);
    Integer loginstate=0;
    try{
        loginstate = (Integer) session.getAttribute("loginstate");
    }catch (Exception e){
    }
    System.out.println("personal:"+loginstate);
    if(loginstate!=null&&loginstate!=0){
        System.out.println("已登录的用户！");
    }else {
        System.out.println("您还没登陆！");
        session.setAttribute("loginajax",0);
        return 0;
    }

    Book books = bookService.SelectBookById(bookid);
    int keep = books.getKeep();
    double price = books.getPrice();
    if(sum>keep){
        System.out.println("购买失败！");
        return 0;
    }
    Book book = new Book();
    //1.2 价格
    price=price*0.9*sum+10;

    //        验证地址信息   card
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    FKUser fkUser=new FKUser();
    fkUser.setUsername(name);
//            获取当前用户信息
    FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        获取用户详细信息
    int user_cardid=0;
try{
     user_cardid =fkUser1.getUser_cardid();
}catch (Exception e){
    System.out.println("没有绑定个人信息地址等信息");
    return 9;
}


    Order order = new Order();
    //        设置时间格式
    Date d = new Date();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

//     设置订单号
    Integer size=orderService.SelectAllOrder(null).size()+12201110;
    Card card = new Card();
    Card cards= userMapper.SelectCardByCID(user_cardid);
    //2.生成订单
//    默认地址
    System.out.println("cards"+cards.toString());
    order.setAddress(cards.getAddress());
    order.setOrder_name(cards.getName());
    order.setPhone(cards.getPhone());
    order.setSum(sum);
//    生成订单价格
    order.setOrder_price(price);
//    订单号
    order.setOrder_id(size);
    //        绑定用户信息
    order.setBook_id(bookid);
    order.setOrder_cardid(user_cardid);
    //当前日期
    order.setDate(sf.format(d.getTime()));
//    3.图书信息修改
//3.1.库存减少操作
    book.setKeep(keep-sum);
//    保存库存信息
    card.setSum(cards.getSum()+sum);
//    4 生成子订单
    Cart cart = new Cart();
    cart.setCart_book_id(bookid);
    cart.setCart_book_name(books.getName());
    cart.setCart_book_order_price(books.getPrice()*0.9);
    cart.setCart_date(sf.format(d.getTime()));
    cart.setCart_imgfile(books.getImgfile());
    cart.setCart_state("购物车");
    cart.setCart_sum(sum);
    cart.setCart_cardid(user_cardid);
    cart.setCart_one_price(books.getPrice());

    cart.setCart_order_id(size);
    if(bookService.BookBuy(book,card,order,cart)>0){
        System.out.println("购买成功");
    }

    model.addAttribute("price",price);
    model.addAttribute("address",cards.getAddress());
    model.addAttribute("phone",cards.getPhone());
    model.addAttribute("name",cards.getName());

    return size;
}


    //    /personal个人中心 显示订单信息 1. 待发货 2.待收货 3.交易成功 4.交易关闭
    @RequestMapping("/personalorder")
    public String personal2(Model model,HttpSession session,Integer order_id,Integer state){
//        判断登陆状态
        Integer loginstate=0;
        try{
            loginstate = (Integer) session.getAttribute("loginstate");
        }catch (Exception e){
        }
        System.out.println("personal:"+loginstate);
        if(loginstate!=null&&loginstate!=0){
            System.out.println("已登录的用户！");
        }else {
            System.out.println("您还没登陆！");
            session.setAttribute("loginajax",0);
            return "login";
        }

        if (order_id!=null){
            Order order = new Order();
            order.setOrder_id(order_id);
            order.setState("交易成功");
            orderService.UpdateOrder(order);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        FKUser fkUser=new FKUser();
        fkUser.setUsername(name);
//            获取当前用户信息
        FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        获取用户详细信息
        int user_cardid=0;
        try {
             user_cardid =fkUser1.getUser_cardid();
        }catch (Exception e){
            Order order = new Order();
            model.addAttribute("order",order);
            model.addAttribute("address","未设置");
            model.addAttribute("name","未设置");
            model.addAttribute("phone","未设置");

            return "orderinfo";
        }



        Card card = userMapper.SelectCardByCID(user_cardid);
        String address = card.getAddress();
        String name1 = card.getName();
        String phone = card.getPhone();
//        1. 待发货 2.待收货 3.交易成功 4.交易关闭
        Order order = new Order();
        order.setOrder_cardid(user_cardid);
        System.out.println("user_cardid:"+user_cardid);
        if(state!=null){
            session.setAttribute("stateAjax",state);
            if(state==1){
order.setState("待发货");
            }else if (state==2){
                order.setState("待收货");
            }else if (state==3){
                order.setState("交易成功");
            }else {
                order.setState("交易关闭");
            }
        }else {
            session.setAttribute("stateAjax",0);
        }

//        List<Order> list = orderService.SelectOrderAllById(1, 10,order).getList();
        List<Order> orders = orderService.SelectOrderAndCart(order);
        System.out.println("public String personal(Model model):"+orders);
        model.addAttribute("order",orders);
        model.addAttribute("address",address);
        model.addAttribute("name",name1);
        model.addAttribute("phone",phone);

        return "orderinfo";
    }

//个人中心
    @RequestMapping("/personal")
    public String personal(Model model,HttpSession session ){
        //        判断登陆状态
        Integer loginstate=0;
        try{
            loginstate = (Integer) session.getAttribute("loginstate");
        }catch (Exception e){
        }
        System.out.println("personal:"+loginstate);
        if(loginstate!=null&&loginstate!=0){
            System.out.println("已登录的用户！");
        }else {
            System.out.println("您还没登陆！");
            session.setAttribute("loginajax",0);
            return "login";
        }


        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        FKUser fkUser=new FKUser();
        fkUser.setUsername(name);
//            获取当前用户信息
        FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        获取用户详细信息
        int user_cardid =0;
        try{
            user_cardid =fkUser1.getUser_cardid();
        }catch (Exception e){
            System.out.println("用户未绑定详细信息");
            Order order = new Order();
            model.addAttribute("order",order);
            model.addAttribute("address","未设置");
            model.addAttribute("name","未设置");
            model.addAttribute("phone","未设置");
            return "个人中心";
        }

        Card card = userMapper.SelectCardByCID(user_cardid);
        String address = card.getAddress();
        String name1 = card.getName();
        String phone = card.getPhone();
        Order order = new Order();
        order.setOrder_cardid(user_cardid);
        List<Order> list = orderService.SelectOrderAllById(1, 10, order).getList();
        System.out.println("public String personal(Model model):"+list);
        model.addAttribute("order",list);
        model.addAttribute("address",address);
        model.addAttribute("name",name1);
        model.addAttribute("phone",phone);
        return "个人中心";
    }

    @RequestMapping("/address")
    public String address(Model model,HttpSession session){

        //        判断登陆状态
        Integer loginstate=0;
        try{
            loginstate = (Integer) session.getAttribute("loginstate");
        }catch (Exception e){
        }
        System.out.println("personal:"+loginstate);
        if(loginstate!=null&&loginstate!=0){
            System.out.println("已登录的用户！");
        }else {
            System.out.println("您还没登陆！");
            return "login";
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        FKUser fkUser=new FKUser();
        fkUser.setUsername(name);
//            获取当前用户信息
        FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        获取用户详细信息
        int user_cardid=0;
        try{user_cardid =fkUser1.getUser_cardid();
            Card card = userMapper.SelectCardByCID(user_cardid);
            String address = card.getAddress();
            String name1 = card.getName();
            String phone = card.getPhone();
            model.addAttribute("address",address);
            model.addAttribute("name",name1);
            model.addAttribute("phone",phone);

        }catch (Exception e){}

        return "address";
    }
//    /wuliu物流
    //个人中心
    @RequestMapping("wuliu")
    public String wuliu(Model model,HttpSession session ,Integer order_id) {
        //        判断登陆状态
        Integer loginstate = 0;
        try {
            loginstate = (Integer) session.getAttribute("loginstate");
        } catch (Exception e) {
        }
        System.out.println("personal:" + loginstate);
        if (loginstate != null && loginstate != 0) {
            System.out.println("已登录的用户！");
        } else {
            System.out.println("您还没登陆！");
            session.setAttribute("loginajax", 0);
            return "login";
        }

        Log log1 = new Log();

        if(logService.SelectLog(order_id)!=null) {
            Log log = logService.SelectLog(order_id);
            model.addAttribute("log", log);
        }else {
            log1.setLog_state("卖家正在发货");
            log1.setLogid(null);
            log1.setLog_id("更新中");
            log1.setLog_name("更新中");
            log1.setLog_date("卖家正在发货");
            model.addAttribute("log", log1);
        }


        return "物流";
    }
}
