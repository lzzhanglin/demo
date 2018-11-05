package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.User;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.LoginAndRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginAndRegisterService")
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    @Autowired
    private UserMapper userMapper;
    public int register(User user) {
        int result = userMapper.register(user);
        return result;
    }

    public String getPasswordByUsername(String username) {
         String password = userMapper.getPasswordByUsername(username);
         return password;
    }
}
