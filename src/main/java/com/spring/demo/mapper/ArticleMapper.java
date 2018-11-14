package com.spring.demo.mapper;

import com.spring.demo.entity.Article;

import java.util.List;

public interface ArticleMapper {
    public int saveArticle(Article article);

    public int updateArticle(Article article);

    public List<Article> getArticleList(Long userId);

    public int deleteArticle(Long articleId);

    public Article viewArticleById(Long articleId);

    public List<Article> showArticleTitle(Long userId);
}
