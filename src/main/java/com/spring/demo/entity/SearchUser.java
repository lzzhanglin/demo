package com.spring.demo.entity;

public class SearchUser {
    private Long userId;

    private String username;

    private String nickname;

    private String phone;

    private String school;

    private String birthday;

    private String describe;

    private String homeAddr;

    //0表示未关注 1表示关注
    private Integer isFollowCurrentUser;


    //是否互相关注
    private Integer isFriend;

    private Long articleNum;

    public Long getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Long articleNum) {
        this.articleNum = articleNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public Integer getIsFollowCurrentUser() {
        return isFollowCurrentUser;
    }

    public void setIsFollowCurrentUser(Integer isFollowCurrentUser) {
        this.isFollowCurrentUser = isFollowCurrentUser;
    }

    public Integer getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Integer isFriend) {
        this.isFriend = isFriend;
    }
}
