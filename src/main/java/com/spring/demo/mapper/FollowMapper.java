package com.spring.demo.mapper;

import com.spring.demo.entity.FollowEntity;
import com.spring.demo.entity.SearchUser;
import com.spring.demo.service.FollowService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {

    public int addFollow(FollowEntity followEntity);

    public int isFollowCurrentUser(@Param("followUserId") Long followUserId, @Param("userId") Long userId);

    public int cancelFollow(FollowEntity followEntity);

    public List<SearchUser> getFollowList(@Param("currentUserId")Long currentUserId,
                                          @Param("offset") Long offset,
                                          @Param("size") Long size);

    public List<SearchUser> getFollowerList(@Param("currentUserId")Long currentUserId,
                                          @Param("offset") Long offset,
                                          @Param("size") Long size);

    public int getFollowTotal(@Param("currentUserId") Long currentUserId);

    public int getFollowerTotal(@Param("currentUserId") Long currentUserId);

    public int removeFan(@Param("followUserId") Long followUserId, @Param("userId") Long userId);

    public int isFriendA(@Param("aUserId") Long aUserId, @Param("bUserId") Long bUserId);

    public int isFriendB(@Param("aUserId") Long aUserId, @Param("bUserId") Long bUserId);

    public List<Long> getAllFollowList(@Param("userId") Long userId);
}