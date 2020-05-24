package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Book;
import com.shop.pojo.Card;
import com.shop.pojo.Cart;
import com.shop.pojo.FKUser;
import com.shop.service.impl.BookServiceImpl;
import com.shop.service.impl.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class index {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CartServiceImp cartServiceImp;

    // 映射"/"请求
    @RequestMapping("/")
    public String index(Model model, HttpSession session) {


//        return "登录";
        PageInfo<Book> pageInfo = bookService.SelectAll(1, 10, null);
//        总页数
        int pages = pageInfo.getPages();


        List<Book> list = pageInfo.getList();
        session.setAttribute("pages", pages);
        model.addAttribute("Pagenum", 1);
        model.addAttribute("book", list);
        return "index";
    }

    @RequestMapping("/return")
    public String index(HttpSession session, HttpServletRequest request, Model model) {
        try {
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("return+USER为空");
        }
        request.setAttribute("Pagenum", 1);
        request.setAttribute("class", "active");
        session.removeAttribute("mohu");
        session.removeAttribute("book_class");
        session.setAttribute("Pagenum", 1);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")) {
            System.out.println("BOOKSTUDENT执行了");
            return "bookstudent";
        }
        System.out.println("return执行了");
        return "book";
    }

    @RequestMapping("/returnbookstudent")
    public String returnbookstudent(HttpSession session, HttpServletRequest request, Model model) {
        try {
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("return+USER为空");
        }
        request.setAttribute("Pagenum", 1);
        request.setAttribute("class", "active");
        session.removeAttribute("mohu");
        session.removeAttribute("book_class");
        session.setAttribute("Pagenum", 1);
        PageInfo<Book> bookPageInfo = bookService.SelectAll(1, 10, null);
        int pages = bookPageInfo.getPages();
        List<Book> list = bookPageInfo.getList();
        model.addAttribute("book", list);
        session.setAttribute("Pages", pages);
        model.addAttribute("Pagenum", 1);
        return "index";

    }

    @RequestMapping("bookstudent")
    public String bookstudent(HttpSession session) {
        try {
            session.setAttribute("user", getUsername());
            session.setAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("USER为空");
        }
        return "bookstudent";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        try {
            session.setAttribute("user", getUsername());
            session.setAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("/indexUSER为空");
        }
        PageInfo<Book> bookPageInfo = bookService.SelectAll(1, 10, null);
        int pages = bookPageInfo.getPages();
        List list = bookPageInfo.getList();
        model.addAttribute("book", list);
        session.setAttribute("pages", pages);
        model.addAttribute("Pagenum", 1);

        FKUser fkUser = new FKUser();
        fkUser.setUsername(getUsername());
        FKUser fkUser1 = userMapper.SelectFKUserByUserName(fkUser);
//        判断用户登陆状态
        if (getUsername().equals("anonymousUser")) {

            session.setAttribute("loginstate", 0);
            return "index";
        } else {
            session.setAttribute("loginstate", 1);
        }

          if(fkUser1.getUser_cardid()!=null){}else {
            session.setAttribute("user_cardid", fkUser1.getUser_cardid());


//                  用户还没登记个人信息
//                    注册用户信息
            System.out.println("新建个人信息");
            Card card = new Card();
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
            fkUser.setUsername(getUsername());
            userMapper.UpdateFKUser(fkUser);
//            建里用户信息表
            userMapper.CardInsert(card);
        }
        List<Cart> carts = cartServiceImp.SelectAllCartByState(fkUser1.getUser_cardid());
          model.addAttribute("msg",carts.size());
        return "index";
    }

    @RequestMapping("book")
    public String book(HttpSession session) {
        try {
            session.setAttribute("user", getUsername());
            session.setAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("USER为空");
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")) {
            System.out.println("您没有权限访问！");
            session.setAttribute("bookAjax", 0);
            return "index";
        }

        return "book";
    }

    //    /accessDenied
    @RequestMapping("accessDenied")
    public String accessDenied(Model model) {
        try {
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());
        } catch (Exception e) {
            System.out.println("USER为空");
        }

        return "accessDenied";
    }

    private String getUsername() {
        // 从SecurityContex中获得Authentication对象代表当前用户的信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);
        return username;
    }

    /**
     * 获得当前用户权限
     */
    private String getAuthority() {
        // 获得Authentication对象，表示用户认证信息。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = new ArrayList<String>();
        // 将角色名称添加到List集合
        for (GrantedAuthority a : authentication.getAuthorities()) {
            roles.add(a.getAuthority());
        }
        System.out.println("role = " + roles);
        return roles.toString();
    }

    @RequestMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, Integer index, Model model) {
        // Authentication是一个接口，表示用户认证信息
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<Book> list = bookService.SelectAll(1, 10, null).getList();
            model.addAttribute("book", list);
            // 如果用户认知信息不为空，注销
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }


            // 重定向到login页面
            if (index != null) {
                return "index";
            }
        } catch (Exception e) {
        }

        return "redirect:/login?logout";
    }

    @RequestMapping("我的信息")
    public String msg(Model model) {
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "bookstudent";
    }

    @RequestMapping(value = "/loginerror")
    public String login(HttpServletRequest request) {
        request.setAttribute("zuces", "登陆失败：输入错误");
        System.out.println("登陆失败");
        return "login";
    }
}
