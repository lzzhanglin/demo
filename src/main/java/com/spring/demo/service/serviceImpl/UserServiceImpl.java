package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.User;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    public boolean saveUserProfile(User user) {
        int resultRow = userMapper.saveUserProfile(user);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User queryUserProfile(String username) {
        User user = userMapper.queryUserProfile(username);
        return user;
    }

    public boolean updatePwdByUsername(String username, String newPwd) {
        int resultRow = userMapper.updatePwdByUsername(username, newPwd);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Long getUserIdByName(String username) {
        return userMapper.getUserIdByName(username);
    }


}
