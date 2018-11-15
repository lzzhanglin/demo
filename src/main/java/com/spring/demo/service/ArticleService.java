package com.spring.demo.service;

import com.spring.demo.entity.Article;

import java.util.List;

public interface ArticleService {
    public Long saveArticle(Article article);

    public Long updateArticle(Article article);

    public List<Article> getArticleList(Long userId,Long offset,Long size);

    public int deleteArticle(Long articleId);

    public Article viewArticleById(Long articleId);

    public List<Article> showArticleTitle(Long userId);
}
