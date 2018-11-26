package com.spring.demo.service;

import com.spring.demo.entity.Comment;

import java.util.List;

public interface CommentService {

    public boolean savaComment(Comment comment);

    public List<Comment> showCommentByArticleId(Long articleId);
}
