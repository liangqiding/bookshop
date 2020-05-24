package com.shop.pojo;

import java.util.List;

public class Order {

    private Integer id;
    private String name;
    private String order_name;
    private String address;
    private String phone;
    private String date;
    private Integer book_id;
    private Integer order_id;
    private double order_price;
    private String returndate;
    private Integer longtime;
    private double price;
    private String state;
    private String post;
    private Integer order_cardid;
    private String imgfile;
    private  Integer sum;
    private List<Cart> CartList;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order_name='" + order_name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", date='" + date + '\'' +
                ", book_id=" + book_id +
                ", order_id=" + order_id +
                ", order_price=" + order_price +
                ", returndate='" + returndate + '\'' +
                ", longtime=" + longtime +
                ", price=" + price +
                ", state='" + state + '\'' +
                ", post='" + post + '\'' +
                ", order_cardid=" + order_cardid +
                ", imgfile='" + imgfile + '\'' +
                ", sum=" + sum +
                ", CartList=" + CartList +
                '}';
    }

    public List<Cart> getCartList() {
        return CartList;
    }

    public void setCartList(List<Cart> cartList) {
        CartList = cartList;
    }

    public String getImgfile() {
        return imgfile;
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public Integer getLongtime() {
        return longtime;
    }

    public void setLongtime(Integer longtime) {
        this.longtime = longtime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getOrder_cardid() {
        return order_cardid;
    }

    public void setOrder_cardid(Integer order_cardid) {
        this.order_cardid = order_cardid;
    }

}
