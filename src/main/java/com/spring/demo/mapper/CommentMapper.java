package com.spring.demo.mapper;

import com.spring.demo.entity.Comment;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

public interface CommentMapper {

    public int saveComment(Comment comment);

        public List<Comment> showCommentByArticleId(Long articleId);
}
