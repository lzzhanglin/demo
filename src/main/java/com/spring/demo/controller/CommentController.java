package com.spring.demo.controller;


import com.spring.demo.entity.Comment;
import com.spring.demo.entity.Resp;
import com.spring.demo.service.CommentService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/save")
    @ResponseBody
    public Resp saveComment(HttpServletRequest request) {
        String comment = request.getParameter("comment");
        String userIdStr = request.getParameter("userId");
        String replyCommentIdStr = request.getParameter("replyCommentId");
        String articleIdStr = request.getParameter("articleId");
        if (Objects.equals(null, userIdStr) || Objects.equals("", userIdStr)) {
            return new Resp("userIdError", "userId is null");
        }
        if (Objects.equals(null, articleIdStr) || Objects.equals("", articleIdStr)) {
            return new Resp("articleIdError", "articleId is null");

        }
        Long userId = Long.valueOf(userIdStr);
        Long articleId = Long.valueOf(articleIdStr);
        Long replyCommentId;
        if (Objects.equals(null, replyCommentIdStr) || Objects.equals("", replyCommentIdStr)) {
            replyCommentId = 0l;
        } else {
            replyCommentId = Long.valueOf(replyCommentIdStr);
        }
        Comment commentEntity = new Comment();
        commentEntity.setComment(comment);
        commentEntity.setArticleId(articleId);
        commentEntity.setUserId(userId);
        commentEntity.setReplyCommentId(replyCommentId);
        if (commentService.savaComment(commentEntity)) {
            return new Resp("success", "post comment success");
        } else {
            return new Resp("failed", "post comment failed");
        }
    }
}
