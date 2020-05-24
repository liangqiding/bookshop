package com.shop.controller;

import com.shop.pojo.Book;

import com.shop.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;


@Controller
public class BookAlterController {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    //添加图书
    @PostMapping(value = "/update")
    public String upload(HttpServletRequest request,
                         @RequestParam("name") String name,
                         @RequestParam("price") Integer price,
                         @RequestParam("keep") Integer keep,
                         @RequestParam("zuozhe") String zuozhe,
                         @RequestParam("book_class") Integer book_class,
                         @RequestParam("state") String state,
                         @RequestParam("file") MultipartFile file) throws Exception {


        // GF
        System.out.println("name = " + name + price + keep + zuozhe);
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            // 上传文件路径
//            String path = request.getServletContext().getRealPath(
//                    "/upload/");
//            System.out.println("path = " + path);
//            // 上传文件名
            String filename = file.getOriginalFilename();
            System.out.println(filename);


            //F:\IDEA\g2\target\classes\static\img
//        获取项目路径
            File path1 = new File(ResourceUtils.getURL("classpath:").getPath());

            System.out.println("path1=" + path1);

            // 判断路径是否存在，如果不存在就创建一个
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path1 + File.separator + "static" + File.separator + "img" + File.separator + filename));

//            数据保存到数据中
            Book book = new Book();
            book.setState(state);
            book.setBook_class(book_class);
            book.setName(name);
            book.setPrice(price);
            book.setKeep(keep);
            book.setZuozhe(zuozhe);
            book.setImgfile(filename);
            int a = bookServiceImpl.Insertbook(book);

            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")) {
                System.out.println("BOOKSTUDENT执行了");
                return "bookstudent";
            }

            return "book";
        } else {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")) {
                System.out.println("BOOKSTUDENT执行了");
                return "bookstudent";
            }
            System.out.println("添加失败！");
            return "book";
        }


    }

    //    修改图书
    @RequestMapping("/update")

    public String update(Integer id, HttpServletRequest request, HttpSession session) {
        System.out.println("获取id值为" + id);
        session.setAttribute("bookid", id);
        try{
            Book book = bookServiceImpl.SelectBookById(id);
            String name = book.getName();
            String zuozhe = book.getZuozhe();
            double price = book.getPrice();
            Integer keep = book.getKeep();
            Integer book_out=book.getBook_out();
            Integer book_class=book.getBook_class();
            String state = book.getState();
            if (state.equals("可借阅")){
                request.setAttribute("state1","selected");
                request.setAttribute("state2","false");
            }else {
                request.setAttribute("state2","selected");
                request.setAttribute("state1","false");
            }
            request.setAttribute("bookname", name);
            request.setAttribute("bookzuozhe", zuozhe);
            request.setAttribute("bookprice", price);
            request.setAttribute("bookkeep", keep);
            request.setAttribute("book_out", book_out);
            System.out.println(book_class);
            for (int num = 1; num <=23 ; num++) {
                if(num==book_class) {
                    request.setAttribute("s"+String.valueOf(num), "selected");
                }else {
                    request.setAttribute("s"+String.valueOf(num), "false");
                }
            }
        }catch (Exception e){
            System.out.println("发现修改异常！");
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")){
                System.out.println("BOOKSTUDENT执行了");
                return "bookstudent";
            }
            return "book";
        }

        return "alterbook";
    }


    //    修改图书
    @PostMapping(value = "/updatemain")
    public String updatemain(HttpServletRequest request, HttpSession session,
                             @RequestParam("name") String name,
                             @RequestParam("price")   Integer price,
                             @RequestParam("keep")  Integer keep,
                             @RequestParam("zuozhe") String zuozhe,
                             @RequestParam("state") String state,
                             Integer book_out,
                             @RequestParam("book_class") Integer book_class,
                             MultipartFile file) throws Exception {

        int id = (Integer) session.getAttribute("bookid");
//        原图书信息
        Book book = new Book();
        book = bookServiceImpl.SelectBookById(id);
        System.out.println("book的值为" + book.toString());

        Book books = new Book();
        books.setBookid(id);

        System.out.println("keep="+keep);
        books.setName(name);
        books.setZuozhe(zuozhe);
        if (price!=null) {
            books.setPrice(price);
        }else {
            books.setPrice(book.getPrice());
        }
        if (keep!=null) {
            books.setKeep(keep);
        }else {
            books.setKeep(book.getKeep());
        }
        if(book_out!=null){
            books.setBook_out(book_out);
        }
        books.setState(state);
        books.setBook_class(book_class);


        if (!file.isEmpty()) {
//            // 上传文件路径
//            String path = request.getServletContext().getRealPath(
//                    "/upload/");
//            System.out.println("path = " + path);

            // 上传文件名
            String filename = file.getOriginalFilename();
            System.out.println(filename);


            //F:\IDEA\g2\target\classes\static\img
//        获取项目路径
            File path1 = new File(ResourceUtils.getURL("classpath:").getPath());


            File filepath = new File(path1, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            System.out.println(path1);
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path1 + File.separator + "static" + File.separator + "img" + File.separator + filename));

            books.setImgfile(filename);

        } else {
            books.setImgfile(book.getImgfile());
        }
//执行更新操作
        if (bookServiceImpl.UpdateBook(books) > 0) {
            System.out.println("更新成功");
            session.setAttribute("Update",1);
        } else {
            System.out.println("更新失败");
            session.setAttribute("Update",2);
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_STUDENT]")) {
            System.out.println("BOOKSTUDENT执行了");
            return "bookstudent";
        }


        return "book";
    }
//select 异步请求
    @RequestMapping("/UpdateBooks")
    @ResponseBody
    public Integer select(HttpSession session){
        Integer update=0;
        try{
            update = (Integer) session.getAttribute("Update");
            session.removeAttribute("Update");
            System.out.println("update:"+update);
        }catch (Exception e){
            update=2;
        }

        if(update!=null&&update==1){
            return 1;
        }else if(update!=null&&update==2){
        System.out.println("Update执行了"+update);
        return 2;}
        else return 3;
    }



}
