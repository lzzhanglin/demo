package com.spring.demo.controller;


import com.spring.demo.entity.Resp;
import com.spring.demo.entity.User;
import com.spring.demo.security.MyPasswordEncoder;
import com.spring.demo.security.SecurityProperties;
import com.spring.demo.service.LoginAndRegisterService;
import com.spring.demo.service.serviceImpl.MyUserDetailService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {


    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private MyUserDetailService myUserDetailService;


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginAndRegisterService loginAndRegisterService;


    @RequestMapping("/")
    public String toWelcome() {

        return "/welcome";
    }

    @RequestMapping("/welcome")
    public String welcome() {

        return "/welcome";
    }

    @RequestMapping("/home")
    public String toHome() {
        return "/home";
    }

    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request) {
        return "/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
//        User userEntity = new User();
//        model.addAttribute("user", userEntity);
        return "/register";
    }


//    @InitBinder("form")
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(user);
//    }
    @RequestMapping(value = "/registerPost", method = RequestMethod.POST)
    @ResponseBody
    public Resp registerPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordR = request.getParameter("passwordR");
        if (!password.equals(passwordR)) {
            return new Resp("failed", "两次输入的密码不一致");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        loginAndRegisterService.register(user);

        return new Resp("success", "register success");

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }



}



    @EnableWebSecurity
    @Order(Ordered.HIGHEST_PRECEDENCE)
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        public final static String SESSION_KEY = "user";
        @Autowired
        private SecurityProperties securityProperties;
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            logger.info("属性是：{}",securityProperties.getBrowser().getLoginPage());
            http
                    .authorizeRequests()
                    .antMatchers("/webjars/**", "/resources/**",
                            "/**/*.css",
                            securityProperties.getBrowser().getLoginPage(),
                            "/welcome",
                            "/register",
                            "/registerPost",
                            "/**/*.js").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")

                    .loginPage("/login")
                    .loginProcessingUrl("/loginPost")
                    .permitAll()
                    .failureUrl("/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                    .and()
                    .csrf().disable();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }



    }


