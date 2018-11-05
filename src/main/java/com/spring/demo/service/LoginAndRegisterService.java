package com.spring.demo.service;

import com.spring.demo.entity.User;

public interface LoginAndRegisterService {
    public int register(User user);

    public String getPasswordByUsername(String username);
}
