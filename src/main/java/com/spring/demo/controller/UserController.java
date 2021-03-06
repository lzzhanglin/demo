package com.spring.demo.controller;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Resp;
import com.spring.demo.entity.SearchUser;
import com.spring.demo.entity.User;
import com.spring.demo.mapper.ArticleMapper;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.UserService;
import com.spring.demo.service.serviceImpl.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ArticleMapper articleMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/editProfile")
    public String toEditPage(HttpServletRequest request, @AuthenticationPrincipal UserDetails user, Model model) {
        String username = user.getUsername();
        if (Objects.equals("", username) || Objects.equals(null, username)) {
            throw new IllegalArgumentException("用户名为空");
        }
        User user1 = userService.queryUserProfile(username);
        request.setAttribute("user",user1);
//        model.addAttribute("username", username);
        return "userCenter";
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

    @RequestMapping("/changePwd")
    public String changePassword(HttpServletRequest request) {
        return "changePwd";
    }

    @RequestMapping("/changePwdPost")
    @ResponseBody
    public Resp changePwdPost(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String newPwdR = request.getParameter("newPwdR");
        if (!Objects.equals(newPwd, newPwdR)) {
            return new Resp("not_same", "password is not same");
        }

//        Authentication currentuser= SecurityContextHolder.getContext().getAuthentication();
        String pwdFromDatabase = userMapper.queryPwdByUsername(user.getUsername());
        boolean result = passwordEncoder.matches(oldPwd, pwdFromDatabase);
        if (!result) {
            return new Resp("failed", "password is wrong");
        }
        String newPwdEncoded = passwordEncoder.encode(newPwd);
        userService.updatePwdByUsername(user.getUsername(), newPwdEncoded);




        return new Resp("success", "change password success");
    }

    @RequestMapping("/view")
    @ResponseBody

    public SearchUser viewUser(HttpServletRequest request) {
        String uId = request.getParameter("userId");
        if (uId == "") {
            throw new IllegalArgumentException("userId为空");
        }
        Long userId = Long.valueOf(uId);
        SearchUser user = userMapper.viewUser(userId);
        return user;


    }

    @RequestMapping("/show")
    public String showUser(HttpServletRequest request,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String uId = request.getParameter("userId");
        Long userId = 0l;
        //是否查看的是当前登录用户的主页 0表示不是 1表示是
        Integer isMyPage = 0;

        //如果参数为空 则是在导航栏点击进来的 查看当前登录用户首页
        if (Objects.equals(null, uId) || Objects.equals("", uId)) {
            userId = userMapper.getUserIdByName(userDetails.getUsername());
            isMyPage = 1;
        } else {
            //要查看的用户id
            userId = Long.valueOf(uId);
        }
        //当前登录的用户id
        Long userDetailId = userMapper.getUserIdByName(userDetails.getUsername());

        if (userDetailId == userId) {
            isMyPage = 1;

        }
        User user = userMapper.getUsernameById(userId);

        List<Article> articleList = articleMapper.getAllArticleList(userId);
        request.setAttribute("articleList",articleList);
        request.setAttribute("user",user);
        request.setAttribute("isMyPage",isMyPage);
        return "showUser";
    }

}
