package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.SearchUser;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("searchService")
public class SearchServiceImpl implements SearchService {


    @Autowired
    private UserMapper userMapper;

    public List<SearchUser> searchUser(String keyWord, Long offset, Long size) {
        List<SearchUser> userList = userMapper.searchUser(keyWord,offset,size);
        return userList;
    }

}
