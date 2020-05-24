package com.shop.pojo;



public class User {

    private Integer id;
    private String usercode;
    private String password;
    private String loginName;
    private String roles;
    private Integer user_cardid;

    public Integer getCard_id() {
        return user_cardid;
    }

    public void setCard_id(Integer card_id) {
        this.user_cardid = card_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usercode='" + usercode + '\'' +
                ", password='" + password + '\'' +
                ", loginName='" + loginName + '\'' +
                ", roles='" + roles + '\'' +
                ", card_id=" + user_cardid +
                '}';
    }
}
