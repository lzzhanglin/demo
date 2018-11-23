package com.spring.demo.mapper;

import com.spring.demo.entity.FollowEntity;
import org.apache.ibatis.annotations.Param;

public interface FollowMapper {

    public int addFollow(FollowEntity followEntity);

    public int isFollowCurrentUser(@Param("followUserId") Long followUserId, @Param("userId") Long userId);
}
