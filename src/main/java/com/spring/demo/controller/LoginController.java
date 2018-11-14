package com.spring.demo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.demo.entity.Article;
import com.spring.demo.entity.Resp;
import com.spring.demo.entity.User;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.security.SecurityProperties;
import com.spring.demo.service.ArticleService;
import com.spring.demo.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {


    private Logger logger = LoggerFactory.getLogger(LoginController.class);


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

    @RequestMapping("/")
    public String toWelcome() {

        return "/welcome";
    }

    @RequestMapping("/welcome")
    public String welcome() {

        return "/welcome";
    }

    @RequestMapping("/home")
    public String toHome(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        PageHelper.startPage(1, 5);


        
        Map<String, String> article = new HashMap<>();
        List<Article> titleList = articleService.showArticleTitle(userId);
        PageInfo<Article> pageInfo = new PageInfo<>(titleList);
        request.setAttribute("articles",article);
        request.setAttribute("pageInfo",pageInfo);
        logger.info(pageInfo.toString());
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
//    @ResponseBody
    public String registerPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
//后台对前台传来的参数进行验证
        boolean haveUsername = userService.isExistUsername(username);
        if (haveUsername) {
            Resp resp = new Resp("failed", "用户名已存在");
            request.setAttribute("resp",resp);
            return "/register";
        }

        boolean haveEmail = userService.isExistUsername(email);
        if (haveEmail) {
            Resp resp = new Resp("failed", "邮箱已被占用");
            request.setAttribute("resp",resp);
            return "/register";
        }
        String password = request.getParameter("password");
        String passwordR = request.getParameter("passwordR");
        if (!password.equals(passwordR)) {
            Resp resp = new Resp("failed", "两次输入的密码不一致");
            request.setAttribute("resp",resp);
            return "/register";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.register(user);
        Resp resp = new Resp("success", "register success");
        return ("/login");

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
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

//    @GetMapping("/me")
//    @ResponseBody
//    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
////        return SecurityContextHolder.getContext().getAuthentication();
//        return user;
//    }



}



    @EnableWebSecurity
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ComponentScan({"com.spring.demo.service.serviceImpl"})
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        public final static String SESSION_KEY = "user";

        @Autowired
        private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

        @Autowired
        private AuthenticationFailureHandler myAuthenticationFailureHandler;

        @Autowired
        private SecurityProperties securityProperties;

        @Autowired
        private DataSource dataSource;

        @Autowired
        private UserDetailsService userDetailsService;



        @Override
        protected void configure(HttpSecurity http) throws Exception {
            logger.info("属性是：{}",securityProperties.getBrowser().getLoginPage());
            http
                    .authorizeRequests()
                    .antMatchers("/webjars/**", "/resources/**",
                            "/**/*.css",
                            securityProperties.getBrowser().getLoginPage(),
                    "/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js",
                            "/welcome",
                            "/register",
                            "/isExistUsername",
                            "/isExistEmail",
                            "/registerPost",
                            "/**/*.js",
                            "/static/js/jquery.js",
                            "/static/js/bootstrap-datetimepicker.min.js",
                            "/static/messages_zh.js",
                            "/static/js/jquery.validate.min.js").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")

                    .loginPage("/login")
                    .loginProcessingUrl("/loginPost")
//                    .successHandler(myAuthenticationSuccessHandler)
//                    .failureHandler(myAuthenticationFailureHandler)
                    .permitAll()
                    .failureUrl("/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .userDetailsService(userDetailsService())
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

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
            tokenRepository.setDataSource(dataSource);
//            tokenRepository.setCreateTableOnStartup(true);
            return tokenRepository;
        }

    }


