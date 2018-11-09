package com.spring.demo.controller;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Resp;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/edit")
    public String toEditArticle(HttpServletRequest request,@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        Long userId = userMapper.getUserIdByName(username);
        request.setAttribute("userId",userId);

        return "/editArticle";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Resp saveArticle(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("userId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setContent(content);
        Long articleId = articleService.saveArticle(article);
        if (!Objects.equals(null, articleId)) {
            return new Resp("success", "save success",articleId.toString());
        } else {
            return new Resp("failed", "save failed");
        }





    }

    @RequestMapping("/update")
    @ResponseBody
    public Resp updateArticle(HttpServletRequest request) {
        Article article = new Article();
        Long articleId = Long.valueOf(request.getParameter("articleId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setContent(content);
        Long aId = articleService.updateArticle(article);
        if (!Objects.equals(null, aId)) {
            return new Resp("success", "update success");
        } else {
            return new Resp("failed", "update failed");
        }

    }

    @RequestMapping("/manage")
    public String manageArticle(HttpServletRequest request,@AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        request.setAttribute("userId",userId);
        return "/articleManage";

    }

    @RequestMapping("/getArticleList")
    @ResponseBody
    public List<Article> getArticleList(HttpServletRequest request,Long userId) {
        String uId = request.getParameter("user");
       return articleService.getArticleList(userId);
    }
}
