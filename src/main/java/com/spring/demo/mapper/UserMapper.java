package com.spring.demo.mapper;

import com.spring.demo.entity.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    public String getPasswordByUsername(String username);

    public int register(User user);

    public int isExistUsername(String username);

    public int isExistEmail(String email);

    public int saveUserProfile(User user);

    public User queryUserProfile(String username);

    public Long getUserIdByName(String username);

    public String queryPwdByUsername(String username);

    public int updatePwdByUsername(@Param("username") String username, @Param("newPwd") String newPwd);
}
