package com.spring.demo.mapper;

import com.spring.demo.entity.User;


public interface UserMapper {
    public String getPasswordByUsername(String username);

    public int register(User user);

    public int isExistUsername(String username);

    public int isExistEmail(String email);

    public int saveUserProfile(User user);

    public User queryUserProfile(String username);
}
