package com.spring.demo.controller;


import com.spring.demo.entity.Comment;
import com.spring.demo.entity.Resp;
import com.spring.demo.mapper.ArticleMapper;
import com.spring.demo.mapper.CommentMapper;
import com.spring.demo.service.CommentService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/save")
    @ResponseBody
    public Resp saveComment(HttpServletRequest request,@AuthenticationPrincipal UserDetails user) {
        String comment = request.getParameter("comment");
        if (Objects.equals(null, comment) || Objects.equals("", comment)) {
            return new Resp("commentNull", "comment can't null");
        }
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
            String createTime1 = commentMapper.getPostTime(commentEntity.getCommentId());
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("createTime", createTime1);
            hashMap.put("commentId", commentEntity.getCommentId());
            hashMap.put("userId", userId);

            return new Resp("success", user.getUsername(),hashMap);
        } else {
            return new Resp("failed", "post comment failed");
        }
    }


    @RequestMapping("/saveReply")
    @ResponseBody
    public Resp saveReply(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        String comment = request.getParameter("comment");
        if (Objects.equals(null, comment) || Objects.equals("", comment)) {
            return new Resp("commentNull", "comment can't null");
        }
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
        //被引用的评论
        String refComment = commentMapper.getCommentById(replyCommentId);
        StringBuilder sb = new StringBuilder();

        sb.append(comment);
        sb.append(" //@");
        sb.append(commentMapper.getCommentPoster(replyCommentId));
        sb.append(": ");
        sb.append(refComment);


        commentEntity.setComment(sb.toString());
        commentEntity.setArticleId(articleId);
        commentEntity.setUserId(userId);
        commentEntity.setReplyCommentId(replyCommentId);
        if (commentService.savaComment(commentEntity)) {
            String createTime1 = commentMapper.getPostTime(commentEntity.getCommentId());
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("createTime", createTime1);
            hashMap.put("commentId", commentEntity.getCommentId());
            hashMap.put("replyCommentId", commentEntity.getReplyCommentId());
            hashMap.put("username", commentMapper.getCommentPoster(replyCommentId));
            hashMap.put("comment", commentEntity.getComment());
            hashMap.put("userId", userId);
            return new Resp("success", user.getUsername(),hashMap);
        } else {
            return new Resp("failed", "post comment failed");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Resp deleteComment(HttpServletRequest request) {
        String cId = request.getParameter("commentId");
        if (Objects.equals(null, cId) || Objects.equals("", cId)) {
            return new Resp("failed", "commentId is null");
        }
        Long articleId = articleMapper.getArticleIdByCommentId(Long.valueOf(cId));
        boolean result = commentService.deleteComment(Long.valueOf(cId));
        if (result) {
            articleMapper.restoreCommentNum(articleId);
            return new Resp("success", "delete success");
        } else {
            return new Resp("failed", "delete failed");
        }

    }
}
