package com.shop.controller;
import com.shop.dao.UserMapper;
import com.shop.pojo.FKUser;
import com.shop.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
//注册
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private UserMapper userMapper;
//    private UserService userService;
    @PostMapping("register")
    public String login(HttpServletRequest request,HttpSession session,
            @RequestParam("loginNames") String loginName,
            @RequestParam("usernames") String username,
            @RequestParam("passwords") String password) {

if(loginName.equals("")||username.equals("")||password.equals("")) {
    System.out.println("输入异常");
    return "login";
}else {
    System.out.println("输入正常");
}
    System.out.println("zuce:" + "账号："+loginName +"用户名："+ username  +"密码"+password);
        FKUser fkUser = new FKUser();
        fkUser.setLoginName(loginName);



            if (userMapper.SelectFKUser(fkUser)!=null){
                request.setAttribute("zuces","注册失败：用户名已存在！");
                return "login";
            }


    String p = new BCryptPasswordEncoder().encode(password);
    fkUser.setPassword(p);

    fkUser.setUsername(username);
    int a= userMapper.InsertFKUser(fkUser);
        if(a>0){
    request.setAttribute("zuce","注册成功");
            System.out.println("注册成功");
}else {
    request.setAttribute("zuces","注册失败：输入错误");
            System.out.println("注册失败");
}
        // 重定向到到main请求redirect:
        return "login";
    }

@RequestMapping(value = "/login")
public String login(HttpSession session) {

    return "login";
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
