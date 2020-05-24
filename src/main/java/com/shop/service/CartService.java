package com.shop.service;

import com.shop.pojo.Cart;
import com.shop.pojo.Order;

import java.util.List;

public interface CartService {
    int CartInserts(Cart cart);
    List<Cart> SelectAllCart(Cart cart);
    int UpdateCarts(Cart cart);
    Cart SelectCartById(Cart cart);
    List<Cart> SelectAllCartByState(Integer cart_cardid);
    int paycart(Order order, Cart cart );
    int UpdateCartsPay(Cart cart);
}
