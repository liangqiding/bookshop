package com.shop.pojo;

public class Cart {
private Integer id;
    private Integer cart_id;
    private String cart_book_name;
    private String cart_date;
    private Integer cart_book_id;
    private Double cart_book_order_price;
    private String cart_state;
    private Integer cart_order_id;
    private Integer cart_sum;
    private String cart_imgfile;
    private Integer cart_cardid;
    private Double cart_one_price;
    private Double sumprice;

    public Double getSumprice() {
        return sumprice;
    }

    public void setSumprice(Double sumprice) {
        this.sumprice = sumprice;
    }

    public Double getCart_one_price() {
        return cart_one_price;
    }

    public void setCart_one_price(Double cart_one_price) {
        this.cart_one_price = cart_one_price;
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getCart_cardid() {
        return cart_cardid;
    }

    public void setCart_cardid(Integer cart_cardid) {
        this.cart_cardid = cart_cardid;
    }

    public Integer getId() {
        return cart_id;
    }

    public void setId(Integer id) {
        this.cart_id = cart_id;
    }

    public String getCart_book_name() {
        return cart_book_name;
    }

    public void setCart_book_name(String cart_book_name) {
        this.cart_book_name = cart_book_name;
    }

    public String getCart_date() {
        return cart_date;
    }

    public void setCart_date(String cart_date) {
        this.cart_date = cart_date;
    }

    public Integer getCart_book_id() {
        return cart_book_id;
    }

    public void setCart_book_id(Integer cart_book_id) {
        this.cart_book_id = cart_book_id;
    }

    public Double getCart_book_order_price() {
        return cart_book_order_price;
    }

    public void setCart_book_order_price(Double cart_book_order_price) {
        this.cart_book_order_price = cart_book_order_price;
    }

    public String getCart_state() {
        return cart_state;
    }

    public void setCart_state(String cart_state) {
        this.cart_state = cart_state;
    }

    public Integer getCart_order_id() {
        return cart_order_id;
    }

    public void setCart_order_id(Integer cart_order_id) {
        this.cart_order_id = cart_order_id;
    }

    public Integer getCart_sum() {
        return cart_sum;
    }

    public void setCart_sum(Integer cart_sum) {
        this.cart_sum = cart_sum;
    }

    public String getCart_imgfile() {
        return cart_imgfile;
    }

    public void setCart_imgfile(String cart_imgfile) {
        this.cart_imgfile = cart_imgfile;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cart_id=" + cart_id +
                ", cart_book_name='" + cart_book_name + '\'' +
                ", cart_date='" + cart_date + '\'' +
                ", cart_book_id=" + cart_book_id +
                ", cart_book_order_price=" + cart_book_order_price +
                ", cart_state='" + cart_state + '\'' +
                ", cart_order_id=" + cart_order_id +
                ", cart_sum=" + cart_sum +
                ", cart_imgfile='" + cart_imgfile + '\'' +
                ", cart_cardid=" + cart_cardid +
                ", cart_one_price=" + cart_one_price +
                ", sumprice=" + sumprice +
                '}';
    }
}
