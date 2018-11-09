package com.spring.demo.mapper;

import com.spring.demo.entity.Article;

import java.util.List;

public interface ArticleMapper {
    public int saveArticle(Article article);

    public int updateArticle(Article article);

    public List<Article> getArticleList(Long userId);
}
