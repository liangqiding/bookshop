package com.shop.service.impl;

import com.shop.dao.UserMapper;
import com.shop.pojo.FKRole;
import com.shop.pojo.FKUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import java.util.List;

/**
 * 需要实现UserDetailsService接口
 * 因为在Spring Security中我们配置相关参数需要UserDetailsService类型的数据
 * */
@Service
public class UserService implements UserDetailsService{

	// 注入持久层接口UserMapper
	@Autowired
    UserMapper userMapper;
	
	// 实现接口中的loadUserByUsername方法，通过该方法查询到对应的用户
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 调用持久层接口findByLoginName方法查找用户，此处的传进来的参数实际是loginName
		FKUser fkUser = userMapper.SecurityselectFKUser(username);
//		System.out.println("user = " + fkUser);
        if (fkUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }else {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("user",fkUser);
        }

        System.out.println(userMapper.SecurityselectFKUser(username).toString());
        // 创建List集合，用来保存用户权限，GrantedAuthority对象代表赋予给当前用户的权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 获得当前用户权限集合
        Integer id=fkUser.getRole_id();
       fkUser.setRoles(userMapper.SelectRole(id));
        List<FKRole> roles = fkUser.getRoles();
        for (FKRole role : roles) {
        	// 将关联对象Role的authority属性保存为用户的认证权限
        	authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        // 此处返回的是org.springframework.security.core.userdetails.User类，该类是Spring Security内部的实现
        return new User(fkUser.getUsername(), fkUser.getPassword(), authorities);
	}


}
