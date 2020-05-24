package com.shop.pojo;

import java.io.Serializable;
import java.util.List;

public class FKUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String loginName;
	private String username;
    private String password;
   private Integer role_id;
   private  Integer user_cardid;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getUser_cardid() {
		return user_cardid;
	}

	public void setUser_cardid(Integer user_cardid) {
		this.user_cardid = user_cardid;
	}

	private List<FKRole> roles;

    
	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<FKRole> getRoles() {
		return roles;
	}

	public void setRoles(List<FKRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "FKUser [id=" + id + ", loginName=" + loginName + ", username=" + username + ", password=" + password
				+ ", roles=" + roles + "]";
	}

	

	

	

}
