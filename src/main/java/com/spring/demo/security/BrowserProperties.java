package com.spring.demo.security;

public class BrowserProperties {
    private String loginPage = "/login";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
