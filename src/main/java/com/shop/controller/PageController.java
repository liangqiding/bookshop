package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.pojo.Book;
import com.shop.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PageController {
    @Autowired
    private BookServiceImpl bookService;
    protected Integer Pagenum;
    protected Integer Pages;

    @RequestMapping("/page")

    public String toString(Integer star, Integer num, Integer Pages, HttpServletRequest request,
                           HttpSession session, Model model) {
        System.out.println("num"+num);
//        清除模糊查询的值
//        session.removeAttribute("mohu");
        //判断起始页
        Book book = new Book();
        PageInfo pageInfo = bookService.SelectAll(1, 10, book);
        int pages = pageInfo.getPages();
        session.setAttribute("pages",pages);

        if(star!=null){
            Pagenum=1;
            session.setAttribute("Pagenum", Pagenum);
            request.setAttribute("Pagenum", Pagenum);



            request.setAttribute("class", "active");
            try {
                model.addAttribute("user", getUsername());
                model.addAttribute("role", getAuthority());
                List<Book> list = bookService.SelectAll(Pagenum, 10, book).getList();
                model.addAttribute("book",list);
            }catch (Exception e){
                System.out.println("USER为空");
            }
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")){

                return "book";
            }

            return "index";

        }
        //判断末页
        if(Pages!=null){
            Pagenum= (Integer) session.getAttribute("Pages");
            session.setAttribute("Pagenum", Pagenum);
            request.setAttribute("Pagenum", Pagenum);
            request.setAttribute("name", Pagenum);
            request.setAttribute("class", "active");
            try {
                model.addAttribute("user", getUsername());
                model.addAttribute("role", getAuthority());
                session.setAttribute("pages",pages);
                List<Book> list = bookService.SelectAll(pageInfo.getPages(), 10, book).getList();
                System.out.println(list);
                model.addAttribute("book",list);

            }catch (Exception e){
                System.out.println("末页异常");
            }
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")){

                return "book";
            }

            return "index";

        }


        if (session.getAttribute("Pages") != null) {
            Pages = (Integer) session.getAttribute("Pages");
        } else {
            Pages = 1;
        }

        if (num != null) {
            num = -1;
        } else {
            num = 1;
        }
        if (session.getAttribute("Pagenum") != null) {
            Pagenum = (Integer) session.getAttribute("Pagenum");
            Pagenum += num;
            if (Pagenum < 1) {
                Pagenum = 1;
            }
            if (Pages <= Pagenum) {
                Pagenum = Pages;
            }
        } else {
            Pagenum = 1;
        }
//        保存当前页码
        session.setAttribute("Pagenum", Pagenum);
        request.setAttribute("Pagenum", Pagenum);

//        request.setAttribute("name", Pagenum);
        request.setAttribute("class", "active");
        try {
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());
            List<Book> list = bookService.SelectAll(Pagenum, 10, book).getList();
            model.addAttribute("book",list);
        }catch (Exception e){
            System.out.println("USER为空");
        }

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")){

            return "book";
        }

        return "index";

    }





    private String getUsername(){
        // 从SecurityContex中获得Authentication对象代表当前用户的信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);
        return username;
    }

    /**
     * 获得当前用户权限
     * */
    private String getAuthority(){
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
}
