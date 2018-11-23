package com.spring.demo.service;

import com.spring.demo.entity.FollowEntity;
import com.spring.demo.entity.SearchUser;

import java.util.List;

public interface FollowService {

    public boolean addFollow(FollowEntity followEntity);

    public int isFollowCurrentUser(Long followUserId, Long userId);

    public boolean cancelFollow(FollowEntity followEntity);

    public List<SearchUser> getFollowList(Long currentUserId,Long offset, Long size);

    public List<SearchUser> getFollowerList(Long currentUserId,Long offset, Long size);

    public boolean removeFan(Long followUserId, Long userId);

    public boolean isFriend(Long aUserId,Long bUserId);
}
