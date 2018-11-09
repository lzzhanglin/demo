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

    public Long updateArticle(Article articlea) {
        articleMapper.updateArticle(articlea);
        return articlea.getArticleId();

    }

    public List<Article> getArticleList(Long userId) {
        List<Article> articleList = articleMapper.getArticleList(userId);
        return articleList;
    }
}
