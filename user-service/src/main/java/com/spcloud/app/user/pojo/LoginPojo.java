package com.spcloud.app.user.pojo;

public class LoginPojo {

    private String username;
    private String pwd;
    private String cx;
    private String a;
    private String pagerandom;
    private String captchToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getPagerandom() {
        return pagerandom;
    }

    public void setPagerandom(String pagerandom) {
        this.pagerandom = pagerandom;
    }

    public String getCaptchToken() {
        return captchToken;
    }

    public void setCaptchToken(String captchToken) {
        this.captchToken = captchToken;
    }
}
