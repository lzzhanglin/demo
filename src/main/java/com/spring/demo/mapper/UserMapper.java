package com.spring.demo.mapper;

import com.spring.demo.entity.User;


public interface UserMapper {
    public String getPasswordByUsername(String username);

    public int register(User user);
}
