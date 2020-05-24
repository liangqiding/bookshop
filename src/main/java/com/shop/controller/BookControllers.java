package com.shop.controller;


import com.github.pagehelper.PageInfo;

import com.shop.pojo.Book;

import com.shop.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
public class BookControllers {
    @Autowired
    private BookServiceImpl bookServiceImpl;


    protected Integer Pagenum;

    /**
     * 图书信息异步数据处理类
     */
    @RequestMapping("/Book")
    public List findBooks(Integer Pagenum, HttpSession session, Model model
    ) {
//		HttpSession sessionName = request.getSession();
        try {
            session.setAttribute("user", getUsername());
            session.setAttribute("role", getAuthority());
        }catch (Exception e){
            System.out.println("return+USER为空");
        }

//模糊查询判断
        String name = null;
        Book book = new Book();
        if (session.getAttribute("mohu") != null) {
            System.out.println("模糊执行了");
            name = (String) session.getAttribute("mohu");
            session.removeAttribute("Pagenum");
        }

        List list = new ArrayList();
        PageInfo<Book> a = new PageInfo<>();

//		       判断分类查询
        if (session.getAttribute("book_class") != null) {
            System.out.println("分类查询执行了");
            session.removeAttribute("Pagenum");
            book.setBook_class((Integer) session.getAttribute("book_class"));
        }
//设置模糊查询的值，默认为null
        book.setName(name);
        //判断当前页
        if (session.getAttribute("Pagenum") != null) {
            Pagenum = (Integer) session.getAttribute("Pagenum");

        } else {
            Pagenum = 1;
            session.setAttribute("Pagenum", Pagenum);
        }

        a = bookServiceImpl.SelectAll(Pagenum, 10, book);
        list = a.getList();

        //判断总页数
        Integer Pages = a.getPages();

        System.out.println("Pages" + Pages);
        session.setAttribute("Pages", Pages);

//        disabled隐藏
        model.addAttribute("book",list);

        return list;


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

