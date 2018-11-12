package com.spring.demo.controller;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.QuertyParams;
import com.spring.demo.entity.Resp;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

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
        String aId = request.getParameter("id");
        Long articleId;
        if (Objects.equals(null, aId)) {
            articleId = 0l;
        } else {
            articleId = Long.valueOf(aId);
        }

        //articleId无值 则跳转该文章的新建页面
        if (Objects.equals(0l, articleId) ) {
            Long userId = userMapper.getUserIdByName(username);
            request.setAttribute("title", null);
            request.setAttribute("content", null);
            request.setAttribute("userId", userId);
            request.setAttribute("articleId",articleId);

            return "/editArticle";
        } else {





        Article article = articleService.viewArticleById(articleId);
        request.setAttribute("articleId", articleId);
        request.setAttribute("title", article.getTitle());
        request.setAttribute("content", article.getContent());
        Long userId = userMapper.getUserIdByName(username);
        request.setAttribute("userId",userId);
        return "/editArticle";

        }
    }



    @RequestMapping("/save")
    @ResponseBody
    public Resp saveArticle(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("userId"));
        String title = request.getParameter("title");
        if (Objects.equals(null, title) || Objects.equals("", title)) {
            return new Resp("illegalArgument", "title is null");
        }
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
        String s = request.getParameter("articleId");
        Long articleId = Long.valueOf(s);
        String title = request.getParameter("title");
        if (Objects.equals(null, title) || Objects.equals("", title)) {
            return new Resp("illegalArgument", "title is null");
        }
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
    public List<Article> getArticleList( @RequestBody QuertyParams quertyParams) {
        Long userId = quertyParams.getUserId();
//        String name =  Request["Name"];
//        String  uId = request.getParameter("queryParams");
       return articleService.getArticleList(userId);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Resp deleteArticle(HttpServletRequest request) {
        String str = request.getParameter("articleId");
        Long articleId = Long.valueOf(str);
        int resultRow = articleService.deleteArticle(articleId);
        if (resultRow == 1) {
            return new Resp("success", "delete article success");
        } else {
            return new Resp("failed", "delete article failed");
        }
    }

    @RequestMapping("/view")
    @ResponseBody
    public Article viewArticleById(HttpServletRequest request) {
        Long articleId = Long.valueOf(request.getParameter("articleId"));
        Article article =  articleService.viewArticleById(articleId);
        return article;
//        request.setAttribute("title", article.getTitle());
//        request.setAttribute("content", article.getContent());
    }
}
