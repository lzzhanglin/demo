package com.spring.demo.entity;

public class FollowEntity {

    private Long followId;

    private Long followUserId;

    private Long userId;

    //1 表示当前登录用户已经关注的用户 0表示当前登录用户未关注的用户
    private Integer isFollowCurrentUser;

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsFollowCurrentUser() {
        return isFollowCurrentUser;
    }

    public void setIsFollowCurrentUser(Integer isFollowCurrentUser) {
        this.isFollowCurrentUser = isFollowCurrentUser;
    }
}
