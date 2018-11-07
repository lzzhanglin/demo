package com.spring.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/editProfile")
    public String toEditPage(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        request.setAttribute("username",username);
        return "/userCenter";
    }
}
