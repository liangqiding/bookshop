package com.shop.dao;


import com.shop.pojo.*;
import org.apache.ibatis.annotations.Mapper;



import java.util.List;
@Mapper
public interface UserMapper {
//    public User find(@Param("usercode")String  usercode,@Param("password" )String  password );
public User find(String  usercode, String  password );

Dict SelectDict(Integer dict_id);
    List<Book> SelectAll(Book book);
    List<Card>  SelectCardPageInfo(Integer card_id);


   int Insertbook(Book book);
   int DeleteBook(Integer bookid);
   List<Book> SelectBook(Book book);
   int UpdateBook(Book book);
   Book SelectBookById(Integer bookid);


    int CardInsert(Card card);

//    Security
    FKUser SecurityselectFKUser(String Login_name);
    List SelectRole(Integer id);
//    祖册
    int InsertFKUser(FKUser fkUser);
//    查询用户
    FKUser SelectFKUser(FKUser fkUser);
    List<Book_keep> SelectAllBook_keep();
    int UpdateFKUser(FKUser fkUser);
//    查询用户
    FKUser SelectFKUserByUserName(FKUser fkUser);
    List<Card> SelectAllCard();

//    更新Card
    int UpdateCard(Card card);
    Card SelectCardByCID(Integer card_id);
//    查询所有用户及借阅卡及权限
    List<Card> SelectUserAllManagePageInfo();
}
