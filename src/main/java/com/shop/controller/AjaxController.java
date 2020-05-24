package com.shop.controller;

import com.shop.dao.UserMapper;
import com.shop.pojo.Cart;
import com.shop.pojo.FKUser;
import com.shop.service.impl.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class AjaxController {
    @Autowired
    private CartServiceImp cartServiceImp;
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("SelectAjax")
    public Integer SelectAjax(HttpSession session){
        try {
            if(session.getAttribute("mohu")!=null){
                return 1;
            }
        }catch (Exception e){

        }
        try {
            if(session.getAttribute("book_class")!=null){
                return 1;
            }
        }catch (Exception e){
        }
        return 0;
    }
//    /LoginAjax
@RequestMapping("/LoginAjax")
public Integer LoginAjax(HttpSession session){

    Integer loginajax = (Integer) session.getAttribute("loginajax");
    session.removeAttribute("loginajax");

        if(loginajax!=null&&loginajax==0){
            return 0;
        }

    return 1;
}
///AjaxOrderP
@RequestMapping("/AjaxOrderP")
public Integer AjaxOrderP(HttpSession session){
    int stateAjax=0;
    if(session.getAttribute("stateAjax")!=null) {
         stateAjax = (Integer) session.getAttribute("stateAjax");
    }
    return stateAjax;
}
///AjaxCart 购物车数量判断
@RequestMapping("/AjaxCart")
public Cart AjaxCart(Model model,Integer sum,Integer id){
    System.out.println("sum="+sum+"  id="+id);
    FKUser fkUser1 = new FKUser();
    fkUser1.setUsername(getUsername());
    FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
    Cart cart = new Cart();
    cart.setCart_id(id);
    Cart cart1 = cartServiceImp.SelectCartById(cart);
    cart.setCart_cardid(fkUser.getUser_cardid());
    System.out.println("ccccccc"+cart1);
if(sum!=null) {
    cart.setCart_sum(sum);
    cart.setCart_book_order_price(cart1.getCart_one_price()*sum*0.9);
}else {
    cart.setCart_sum(cart1.getCart_sum());
    cart.setCart_book_order_price(cart1.getCart_book_order_price());
}
    cart.setCart_one_price(cart1.getCart_one_price());
    int i = cartServiceImp.UpdateCarts(cart);


    List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
    Double sumprice=0.0;
    for (Cart cart2:carts) {
         sumprice=sumprice+cart2.getCart_book_order_price();
    }

    cart.setSumprice(sumprice);

    return cart;
}
//String cart_state,购物订单状态判断
@RequestMapping("/AjaxCartOrder")
public Cart AjaxCartOrder(Model model,String cart_state,Integer id,Integer all){
    System.out.println("cart_state:"+cart_state+id);
    FKUser fkUser1 = new FKUser();
    fkUser1.setUsername(getUsername());
    FKUser fkUser = userMapper.SelectFKUserByUserName(fkUser1);
    Cart cart = new Cart();
    if(all!=null){
        cart.setCart_cardid(fkUser.getUser_cardid());
        cart.setCart_state(cart_state);
        int i = cartServiceImp.UpdateCarts(cart);
        List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
        Double sumprice=0.0;
        for (Cart cart2:carts) {
            sumprice=sumprice+cart2.getCart_book_order_price();
        }
        cart.setSumprice(sumprice);
        cart.setCart_sum(carts.size());
        return cart;
    }

    cart.setCart_id(id);
    cart.setCart_state(cart_state);
    int i = cartServiceImp.UpdateCarts(cart);
    List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser.getUser_cardid());
    Double sumprice=0.0;
    for (Cart cart2:carts) {
        sumprice=sumprice+cart2.getCart_book_order_price();
    }
    cart.setSumprice(sumprice);
    cart.setCart_sum(carts.size());
    return cart;
}


    private String getUsername() {
        // 从SecurityContex中获得Authentication对象代表当前用户的信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);
        return username;
    }
}
