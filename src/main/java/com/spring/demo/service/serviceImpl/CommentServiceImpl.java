package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.Comment;
import com.spring.demo.mapper.ArticleMapper;
import com.spring.demo.mapper.CommentMapper;
import com.spring.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;


    public boolean savaComment(Comment comment) {
        int resultRow = commentMapper.saveComment(comment);
        if (resultRow == 1) {
            articleMapper.updateCommentNum(comment.getArticleId());
        }
        return resultRow == 1 ? true : false;
    }

    public List<Comment> showCommentByArticleId(Long articleId) {
        return commentMapper.showCommentByArticleId(articleId);
    }

    public boolean deleteComment(Long commentId) {
        int resultRow = commentMapper.deleteComment(commentId);
        return resultRow == 1 ? true : false;
    }
}
