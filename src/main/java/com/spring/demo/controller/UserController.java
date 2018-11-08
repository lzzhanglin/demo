package com.spring.demo.controller;

import com.spring.demo.entity.Resp;
import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/editProfile")
    public String toEditPage(HttpServletRequest request, @AuthenticationPrincipal UserDetails user, Model model) {
        String username = user.getUsername();
        if (Objects.equals("", username) || Objects.equals(null, username)) {
            throw new IllegalArgumentException("用户名为空");
        }
        User user1 = userService.queryUserProfile(username);
        request.setAttribute("user",user1);
//        model.addAttribute("username", username);
        return "/userCenter";
    }

    @RequestMapping("/saveProfile")
    @ResponseBody
    public Resp saveUserProfile(HttpServletRequest request) {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setNickname(request.getParameter("nickname"));
        user.setPhone(request.getParameter("phone"));
        user.setBirthday(request.getParameter("birthday"));
        user.setSchool(request.getParameter("school"));
        user.setHomeAddr(request.getParameter("homeAddr"));
        user.setDescribe(request.getParameter("describe"));
        boolean result = userService.saveUserProfile(user);
        if (result) {
            return new Resp("success", "save success");
        } else {
            return new Resp("failed", "save failed");
        }
    }
}
