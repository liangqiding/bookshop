package com.shop.controller;

import com.shop.dao.UserMapper;
import com.shop.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shop.service.impl.BookServiceImpl;
import com.shop.service.impl.CardServiceImpl;
import com.shop.service.impl.CartServiceImp;
import com.shop.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartServiceImp cartServiceImp;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private CardServiceImpl cardService;

    //    购物车
    @RequestMapping("/cart")
    public String Cart(Model model, HttpSession session) {
        FKUser fkUser1 = new FKUser();
        fkUser1.setUsername(getUsername());
        FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
        Cart cart1 = new Cart();
        cart1.setCart_state("待结算");
        cart1.setCart_cardid(fkUser.getUser_cardid());
        List<Cart> cart = cartServiceImp.SelectAllCart(cart1);
        model.addAttribute("cart", cart);
        List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
        Cart cart2 = new Cart();
        cart2.setCart_sum(carts.size());
        double sumprice=0.0;
        for(Cart cart3:carts){
            sumprice=sumprice+cart3.getCart_book_order_price();
        }
        cart2.setSumprice(sumprice);
        model.addAttribute("sump", cart2);
        return "购物车";
    }

    @RequestMapping("/addcart")
    public String addCart(Model model, Integer bookid, @RequestParam(defaultValue = "1") Integer sum) {
        System.out.println("/addcart,bookid=" + bookid);
        FKUser fkUser1 = new FKUser();
        fkUser1.setUsername(getUsername());
        FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
        Book book = bookService.SelectBookById(bookid);
        Cart cart = new Cart();
//        生成子订单

        cart.setCart_cardid(fkUser.getUser_cardid());
        cart.setCart_sum(sum);
        cart.setCart_imgfile(book.getImgfile());
        cart.setCart_book_name(book.getName());
        cart.setCart_book_order_price(book.getPrice() * 0.9);
        cart.setCart_book_id(bookid);
        cart.setCart_one_price(book.getPrice());
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        cart.setCart_date(sf.format(d.getTime()));
        cart.setCart_state("待结算");
        cartServiceImp.CartInserts(cart);
        Cart cart2 = new Cart();
        cart2.setCart_book_order_price(0.0);
        model.addAttribute("sump", cart2);

        Cart cart1 = new Cart();
        cart1.setCart_cardid(fkUser.getUser_cardid());
        List<Cart> carts = cartServiceImp.SelectAllCart(cart1);
        System.out.println("casts:"+carts);

        model.addAttribute("cart", carts);


        List<Cart> cartss = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
        Cart cart3 = new Cart();
        cart3.setCart_sum(carts.size());
        double sumprice=0.0;
        for(Cart cart4:carts){
            sumprice=sumprice+cart4.getCart_book_order_price();
        }
        cart3.setSumprice(sumprice);
        model.addAttribute("sump", cart3);
        return "购物车";
    }

    //  购物车  结账/AjaxCartPay
    @RequestMapping("/AjaxCartPay")
    @ResponseBody
    public String AjaxCartPay() {
        FKUser fkUser1 = new FKUser();
        fkUser1.setUsername(getUsername());
        FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
        Integer user_cardid = fkUser.getUser_cardid();
        Card card1 = userMapper.SelectCardByCID(user_cardid);
        List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
        if (carts.size()<=0){
            return "请勾选要购买的商品";
        }
        try {
            String address = card1.getAddress();
            String name = card1.getName();
            String phone = card1.getPhone();
           if(address.equals("")||name.equals("")||phone.equals("")){
               return "请先绑定完整地址信息，再结算";
           }
        } catch (NullPointerException e) {
           return "请先绑定完整地址信息，再结算";
        }

        Order order = new Order();
        Cart cart = new Cart();

        //        设置时间格式
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

//     设置订单号
        Integer size = orderService.SelectAllOrder(null).size() + 22202110;
        Card card = new Card();
        Card cards = userMapper.SelectCardByCID(user_cardid);
        //2.生成订单
//    默认地址
        order.setAddress(cards.getAddress());
        order.setOrder_name(cards.getName());
        order.setPhone(cards.getPhone());

//    生成订单价格
        Double sumprice = 0.0;
        for (Cart cart2 : carts) {
            sumprice = sumprice + cart2.getCart_book_order_price();
        }
        order.setOrder_price(sumprice);
//    订单号
        order.setOrder_id(size);
        //        绑定用户信息
        order.setOrder_cardid(user_cardid);
        //当前日期
        order.setDate(sf.format(d.getTime()));
//    保存库存信息
        card.setSum(cards.getSum() + 1);
//    4 生成子订单
        cart.setCart_order_id(size);
        cart.setCart_cardid(user_cardid);
        cart.setCart_state("已结算");

        if (cartServiceImp.paycart(order, cart) > 0) {
            return "结算成功";
        }
        return "结算失败";
    }

    private String getUsername() {
        // 从SecurityContex中获得Authentication对象代表当前用户的信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);
        return username;
    }
}
