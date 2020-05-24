package com.shop.service.impl;

import com.shop.dao.CartMapper;
import com.shop.dao.OrderMapper;
import com.shop.pojo.Cart;
import com.shop.pojo.Order;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImp implements CartService {
@Autowired
private CartMapper cartMapper;
@Autowired
private OrderMapper orderMapper;
    @Override
    public int CartInserts(Cart cart) {
        return cartMapper.CartInserts(cart);
    }

    @Override
    public List<Cart> SelectAllCart(Cart cart) {
        return cartMapper.SelectAllCart(cart);
    }

    @Override
    public int UpdateCarts(Cart cart) {
        return cartMapper.UpdateCarts(cart);
    }

    @Override
    public Cart SelectCartById(Cart cart) {
        return cartMapper.SelectCartById(cart);
    }

    @Override
    public List<Cart> SelectAllCartByState(Integer cart_cardid) {
        return cartMapper.SelectAllCartByState(cart_cardid);
    }

//    购物车结账
    @Transactional
    public int paycart(Order order, Cart cart ){
//        1 生成订单
        orderMapper.BorrowInsert(order);
//        2 改变子订单状态
        int a=cartMapper.UpdateCartsPay(cart);

        return a;
    }

    @Override
    public int UpdateCartsPay(Cart cart) {
        return cartMapper.UpdateCartsPay(cart);
    }
}
