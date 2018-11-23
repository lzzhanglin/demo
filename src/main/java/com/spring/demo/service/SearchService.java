package com.spring.demo.service;

import com.spring.demo.entity.SearchUser;

import java.util.List;

public interface SearchService {

    public List<SearchUser> searchUser(String keyWord, Long offset, Long size);
}
