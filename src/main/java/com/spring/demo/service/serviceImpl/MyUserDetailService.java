package com.spring.demo.service.serviceImpl;

import com.spring.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
@Component
@Service("userDetailService")
public class MyUserDetailService implements UserDetailsService  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("执行MyUserDetailService");

        logger.info("用户名为：{}",username);
        String password = userService.getPasswordByUsername(username);
//        String password = passwordEncoder.encode("111");
        logger.info("密码为：{}", password);
        String passwordInput = passwordEncoder.encode("222");
        logger.info("输入的密码为：{}",passwordInput);
        User user = new User(
                username,
                password,
                  true,  // 是否可用
                 true,  // 账户没有过期
              true, // 密码没有过期
                true,   // 帐号没有被锁定
                // 相当于 grantedAuthorityList
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin")


        );
        return user;
    }



}
