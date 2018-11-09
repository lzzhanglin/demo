package com.spring.demo.entity;


public class Article {

    public Long articleId;

    public String title;

    public Long userId;

    public String createTime;

    public String content;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }
}


