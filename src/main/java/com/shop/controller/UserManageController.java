package com.shop.controller;

import com.github.pagehelper.PageInfo;

import com.shop.dao.UserMapper;
import com.shop.pojo.Card;
import com.shop.pojo.FKUser;
import com.shop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserManageController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/usermanage")
    public String u(){
        return "usermanage";
    }

    @RequestMapping("/User")
    @ResponseBody
    public List findUsers(Integer Pagenum, HttpSession session, HttpServletRequest request
    )
    {
        PageInfo<Card> card=userService.SelectUserAllManagePageInfo();
//        List list=new List();
           List cards=card.getList();
        System.out.println(cards);
        return cards;
    }
    @RequestMapping("/UserAjax")
    @ResponseBody
    public Integer UserAjax(HttpSession session)
    {

        try{
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if (name.equals("anonymousUser")){
//                System.out.println("还没登陆！");
                return 0;
            }else{
//                System.out.println("已登陆，名为："+name);
                Object bookAjax = session.getAttribute("bookAjax");
                if (bookAjax!=null){
//                    拦截权限
                    session.removeAttribute("bookAjax");
                    return 2;
                }
                return 1;
            }
        }catch (Exception e){
            return 0;
        }

    }

    @RequestMapping("/useraddress")
    public String UserAddress(Model model, String name, String address, String phone, String email
    )
    {

           Card card = new Card();
            Integer user_cardid = 0;
            String names = SecurityContextHolder.getContext().getAuthentication().getName();
            FKUser fkUser = new FKUser();
            fkUser.setUsername(names);
//            获取当前用户信息
            FKUser fkUsers = userMapper.SelectFKUserByUserName(fkUser);


            card.setAddress(address);
            card.setEmail(email);

            card.setName(name);
            card.setPhone(phone);
            //        获取用户详细信息
            try {
                user_cardid = fkUsers.getUser_cardid();

            } catch (Exception e) {}
            if (user_cardid!=null){

            }else {

                System.out.println("新建个人信息");

                //    查询总数量
                List<Card> cards = userMapper.SelectAllCard();
                Integer size = cards.size() + 2019000;
                card.setCard_id(size);
                //        设置时间格式
                Date d = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//当前日期
                card.setCard_date(sf.format(d.getTime()));
//        对FKUser表绑定用户地址信息
                fkUser.setUser_cardid(size);
                fkUser.setUsername(names);
                userMapper.UpdateFKUser(fkUser);
//            建里用户信息表
                userMapper.CardInsert(card);

                model.addAttribute("address", address);
                model.addAttribute("name", name);
                model.addAttribute("phone", phone);
                return "address";
            }
//        更新用户地址信息
            card.setCard_id(user_cardid);
            userMapper.UpdateCard(card);

        Card card1 = userMapper.SelectCardByCID(user_cardid);
        String address1 = card1.getAddress();
        String name1 = card1.getName();
        String phone1 = card1.getPhone();
        model.addAttribute("address",address1);
        model.addAttribute("name",name1);
        model.addAttribute("phone",phone1);
        return "address";
    }
}
