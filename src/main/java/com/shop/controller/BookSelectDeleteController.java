package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.dao.UserMapper;
import com.shop.pojo.Book;
import com.shop.pojo.Dict;
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
public class BookSelectDeleteController {
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private UserMapper userMapper;
    //    分类查找图书

    @RequestMapping("/selectclass")

    public String selectclass(Integer book_class, HttpServletRequest request, HttpSession session,Model model) {
        session.setAttribute("book_class",book_class);
        session.removeAttribute("mohu");
        Book book = new Book();
        book.setBook_class(book_class);
        Dict dict = userMapper.SelectDict(book_class);
        System.out.println(dict.toString());
        try {
            PageInfo<Book> bookPageInfo = bookServiceImpl.SelectAll(1, 10, book);
            int pages = bookPageInfo.getPages();
            List<Book> list =bookPageInfo.getList();
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());

            String dict_class = dict.getDict_class();
            model.addAttribute("dict_class","当前类别为："+dict_class);
            System.out.println("类别为："+dict_class);
            model.addAttribute("book",list);

            session.setAttribute("pages",pages);
            model.addAttribute("Pagenum", 1);
        }catch (Exception e){
            System.out.println("/selectclass异常");
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")){

            return "book";
        }
        return "index";
    }
    //模糊查询
    @RequestMapping("/selectbook")

    public String select(String name, HttpServletRequest request, HttpSession session, Model model) {
        System.out.println("获取name值为" + name);
        session.removeAttribute("book_class");
        try {
            model.addAttribute("user", getUsername());
            model.addAttribute("role", getAuthority());
        }catch (Exception e){
            System.out.println("USER为空");
        }
        session.setAttribute("mohu", name);
        session.removeAttribute("Pagenum");
        request.setAttribute("Pagenum", 1);
        request.setAttribute("class", "active");
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")){
            return "book";
        }
        Book book = new Book();
        book.setName(name);
        PageInfo<Book> bookPageInfo = bookServiceImpl.SelectAll(1, 10, book);
        List<Book> list = bookPageInfo.getList();
        int pages = bookPageInfo.getPages();

        model.addAttribute("book",list);
        model.addAttribute("msg","搜索  “"+name+"”  找到"+list.size()+"个结果：");
        session.setAttribute("pages",pages);
        model.addAttribute("Pagenum", 1);
        return "index";

    }
    //删除图书

    @RequestMapping("/delete")
    public String index(Integer id) {
        System.out.println("获取ID值为" + id);

        if (bookServiceImpl.DeleteBook(id) > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败");
        }

        return "book";
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
