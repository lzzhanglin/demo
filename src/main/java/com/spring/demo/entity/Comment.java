package com.spring.demo.entity;

public class Comment {
//评论实体类
    private Long commentId;

    private String comment;

    private String createTime;

    private Long userId;

    private String username;

    private Long replyCommentId;

    private Long articleId;

    //如果是0 则不是当前登录用户发表的评论
    //如果是1 则是当前用户发表的评论，可以进行删除操作
    private Long isCurrentUserCreated;

    public Long getIsCurrentUserCreated() {
        return isCurrentUserCreated;
    }

    public void setIsCurrentUserCreated(Long isCurrentUserCreated) {
        this.isCurrentUserCreated = isCurrentUserCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(Long replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
