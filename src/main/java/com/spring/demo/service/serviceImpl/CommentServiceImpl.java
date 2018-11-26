package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.Comment;
import com.spring.demo.mapper.CommentMapper;
import com.spring.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;


    public boolean savaComment(Comment comment) {
        int resultRow = commentMapper.saveComment(comment);
        return resultRow == 1 ? true : false;
    }

    public List<Comment> showCommentByArticleId(Long articleId) {
        return commentMapper.showCommentByArticleId(articleId);
    }
}
