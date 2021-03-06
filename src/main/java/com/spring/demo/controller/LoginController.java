package com.spring.demo.controller;


import com.github.pagehelper.PageInfo;
import com.spring.demo.entity.Resp;
import com.spring.demo.entity.User;
import com.spring.demo.entity.VisitRecord;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.security.SecurityProperties;
import com.spring.demo.service.ArticleService;
import com.spring.demo.service.UserService;
import com.spring.demo.service.VisitService;
import com.spring.demo.service.serviceImpl.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.util.Objects;

@Controller
public class LoginController {


    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private VisitService visitService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private MyUserDetailService myUserDetailService;


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
//
//    @RequestMapping("/")
//    public String toWelcome() {
//
//        return "/home";
//    }
//
//    @RequestMapping("/welcome")
//    public String welcome() {
//
//        return "/home";
//    }

    @RequestMapping(value = {"/home","/",""})
    public String toHome(HttpServletRequest request, @AuthenticationPrincipal UserDetails user,
                         @RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "8") Integer pageSize) {
        Long userId = userMapper.getUserIdByName(user.getUsername());

        PageInfo pageInfo = articleService.showArticleTitle(userId, pageNum, pageSize);
        request.setAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        request.setAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        request.setAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        request.setAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        request.setAttribute("isLastPage", pageInfo.isIsLastPage());

        request.setAttribute("pageInfo",pageInfo);
        return "home";
    }



    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request) {
        String error = request.getParameter("error");
        logger.info("访问web服务一次");
        //获取访问的源IP地址
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setIpAddr(ip);
        visitService.addOrUpdateVisitRecord(visitRecord);
        logger.info("ip地址为：{}", ip);
        if (Objects.equals("true", error)) {
            logger.info("密码是否错误：{}",error);

            request.setAttribute("error",error);
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
//        User userEntity = new User();
//        model.addAttribute("user", userEntity);
        return "register";
    }


//    @InitBinder("form")
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(user);
//    }
    @RequestMapping(value = "/registerPost", method = RequestMethod.POST)
//    @ResponseBody
    public String registerPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
//后台对前台传来的参数进行验证
        boolean haveUsername = userService.isExistUsername(username);
        if (haveUsername) {
            Resp resp = new Resp("failed", "用户名已存在");
            request.setAttribute("resp",resp);
            return "register";
        }

        boolean haveEmail = userService.isExistUsername(email);
        if (haveEmail) {
            Resp resp = new Resp("failed", "邮箱已被占用");
            request.setAttribute("resp",resp);
            return "register";
        }
        String password = request.getParameter("password");
        String passwordR = request.getParameter("passwordR");
        if (!password.equals(passwordR)) {
            Resp resp = new Resp("failed", "两次输入的密码不一致");
            request.setAttribute("resp",resp);
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.register(user);
        Resp resp = new Resp("success", "register success");
        return ("login");

    }


    @RequestMapping(value = "/isExistUsername" ,method = RequestMethod.POST)
    @ResponseBody
    public Resp isExistUsername(String  username) {
//        String username = request.getParameter("username");
        if (Objects.equals(null,username)) {
            return new Resp("failed", "用户名为空");
        }
        logger.info("用户名为：{}",username);
        boolean isExist = userService.isExistUsername(username);
        if (isExist) {
            return new Resp("failed", "username is exist");
        } else return new Resp("success", "username is not be used");
    }

    @RequestMapping(value = "/isExistEmail" ,method = RequestMethod.POST)
    @ResponseBody
    public Resp isExistEmail(String email) {
        logger.info("邮箱为：{}",email);
//        String email = request.getParameter("email");
        if (Objects.equals(null,email)) {
            return new Resp("failed", "邮箱为空");
        }
        boolean isExist = userService.isExistEmail(email);
        if (isExist) {
            return new Resp("failed", "email is exist");
        } else return new Resp("success", "email is not be used");
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}





