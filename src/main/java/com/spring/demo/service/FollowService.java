package com.spring.demo.service;

import com.spring.demo.entity.FollowEntity;

public interface FollowService {

    public boolean addFollow(FollowEntity followEntity);

    public int isFollowCurrentUser(Long followUserId, Long userId);
}
