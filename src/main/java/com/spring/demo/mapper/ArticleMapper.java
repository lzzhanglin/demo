package com.spring.demo.mapper;

import com.spring.demo.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ArticleMapper {
    public int saveArticle(Article article);

    public int updateArticle(Article article);

    public List<Article> getArticleList(@Param("userId") Long userId,
                                        @Param("offset") Long offset,
                                        @Param("size") Long size);

    public int getArticleTotalNum(Long userId);

    public int deleteArticle(Long articleId);

    public Article viewArticleById(Long articleId);

    public List<Article> showArticleTitle(Long userId);
}
