package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.User;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    public int register(User user) {
        user.setRole("user");
        int result = userMapper.register(user);
        return result;
    }

    public String getPasswordByUsername(String username) {
         String password = userMapper.getPasswordByUsername(username);
         return password;
    }

    public boolean isExistUsername(String username) {
        int result = userMapper.isExistUsername(username);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistEmail(String email) {
        int result = userMapper.isExistEmail(email);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}
