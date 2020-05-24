package com.shop.dao;

import com.shop.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int CartInserts(Cart cart);
    List<Cart> SelectAllCart(Cart cart);
    int UpdateCarts(Cart cart);
    Cart SelectCartById(Cart cart);
    List<Cart> SelectAllCartByState(Integer cart_cardid);
    int UpdateCartsPay(Cart cart);
}
