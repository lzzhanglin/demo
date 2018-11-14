package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.Article;
import com.spring.demo.mapper.ArticleMapper;
import com.spring.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;
    public Long saveArticle(Article article) {
       articleMapper.saveArticle(article);
        return article.getArticleId();
    }

    public Long updateArticle(Article article) {
        articleMapper.updateArticle(article);
        return article.getArticleId();

    }

    public List<Article> getArticleList(Long userId) {
        List<Article> articleList = articleMapper.getArticleList(userId);
        return articleList;
    }

    public int deleteArticle(Long articleId) {
       return articleMapper.deleteArticle(articleId);
    }

    public Article viewArticleById(Long articleId) {
        return articleMapper.viewArticleById(articleId);
    }

    public List<Article> showArticleTitle(Long userId) {
        return articleMapper.showArticleTitle(userId);
    }
}
