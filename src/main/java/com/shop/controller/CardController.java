package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Card;
import com.shop.pojo.FKUser;
import com.shop.service.impl.CardServiceImpl;
import com.shop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CardController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CardServiceImpl cardService;
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("/CardAjax")
    @ResponseBody
    public int CardAjax(HttpSession session){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        FKUser fkUser=new FKUser();
        fkUser.setUsername(name);
        int c=0;
        if (session.getAttribute("AjaxCrad")!=null){
            session.removeAttribute("AjaxCrad");
            return 3;
        }
        try{
            Integer user_cardid = userMapper.SelectFKUserByUserName(fkUser).getUser_cardid();
            if (user_cardid==null){
                c=1;
            }
        }catch (Exception e){
            System.out.println("还没有绑定借阅卡");
            c=1;
        }
        return c;
    }

    //办理借阅证Bcard
    @RequestMapping("Bcard")
    public String BorrowCard(){
        return "card";
    }
    @RequestMapping("BcardMain")
    public String BorrowCardMain(HttpSession session, Card card) {
//    查询总借阅卡数量
        List<Card> cards = userMapper.SelectAllCard();
        Integer size=cards.size()+2019000;
        card.setCard_id(size);
        //        设置时间格式
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//当前日期
        card.setCard_date(sf.format(d.getTime()));
//        对FKUser表绑定借阅卡号信息
        String name=SecurityContextHolder.getContext().getAuthentication().getName();
        FKUser fkUser=new FKUser();
        fkUser.setUser_cardid(size);
        fkUser.setUsername(name);
        userMapper.UpdateFKUser(fkUser);
        if (cardService.CardInsert(card)>0){
            session.setAttribute("AjaxCrad",3);
        }
        return "bookstudent";
    }

//    cardmanage
@RequestMapping("/cardmanage")
public String cardmanage(HttpSession session) {

    return "cardmanage";
}


    //    /Bcards我的借阅证
@RequestMapping("/Bcards")
@ResponseBody
public List Bcards(HttpSession session) {
    Integer user_cardid=0;
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser = new FKUser();
            fkUser.setUsername(name);
             user_cardid = userMapper.SelectFKUserByUserName(fkUser).getUser_cardid();
        }catch (Exception e){
            System.out.println("没找到借阅卡ID");
        }
    PageInfo<Card> card=cardService.SelectCardPageInfo(1,5,user_cardid);
//        List list=new List();
    List cards=card.getList();
    System.out.println(cards);
return cards;
    }

}
