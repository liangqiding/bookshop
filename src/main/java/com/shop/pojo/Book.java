package com.shop.pojo;

public class Book {
    private int bookid;
    private  String name;
    private  double price;
    private  int keep;
   private String imgfile;
   private String zuozhe;
   private String state;
   private Integer book_class;
   private Integer ISBM;
   private String dict_class;
   private  String order_name;
   private Integer order_price;
   private  Integer book_out;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getKeep() {
        return keep;
    }

    public void setKeep(int keep) {
        this.keep = keep;
    }

    public String getImgfile() {
        return imgfile;
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
    }

    public String getZuozhe() {
        return zuozhe;
    }

    public void setZuozhe(String zuozhe) {
        this.zuozhe = zuozhe;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getBook_class() {
        return book_class;
    }

    public void setBook_class(Integer book_class) {
        this.book_class = book_class;
    }

    public Integer getISBM() {
        return ISBM;
    }

    public void setISBM(Integer ISBM) {
        this.ISBM = ISBM;
    }

    public String getDict_class() {
        return dict_class;
    }

    public void setDict_class(String dict_class) {
        this.dict_class = dict_class;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public Integer getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Integer order_price) {
        this.order_price = order_price;
    }

    public Integer getBook_out() {
        return book_out;
    }

    public void setBook_out(Integer book_out) {
        this.book_out = book_out;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", keep=" + keep +
                ", imgfile='" + imgfile + '\'' +
                ", zuozhe='" + zuozhe + '\'' +
                ", state=" + state +
                ", book_class=" + book_class +
                ", ISBM=" + ISBM +
                ", dict_class='" + dict_class + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_price=" + order_price +
                '}';
    }
}
