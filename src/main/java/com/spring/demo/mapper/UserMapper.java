package com.spring.demo.mapper;

import com.spring.demo.entity.SearchUser;
import com.spring.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


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

    public List<SearchUser> searchUser(@Param("keyWord") String keyWord,
                                       @Param("offset") Long offset,
                                       @Param("size") Long size);

    public int searchUserTotal(@Param("keyWord") String keyWord);

    public SearchUser viewUser(Long userId);

    public User getUsernameById(Long userId);
}
