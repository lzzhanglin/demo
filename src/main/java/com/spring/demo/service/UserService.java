package com.spring.demo.service;

import com.spring.demo.entity.User;

public interface UserService {
    public int register(User user);

    public String getPasswordByUsername(String username);

    public boolean isExistUsername(String username);

    public boolean isExistEmail(String username);


    public boolean saveUserProfile(User user);

    public User queryUserProfile(String username);


}
