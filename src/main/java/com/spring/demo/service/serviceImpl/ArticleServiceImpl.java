package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Category;
import com.spring.demo.mapper.ArticleMapper;
import com.spring.demo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


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

    public List<Article> getArticleList(Long userId,Long offset,Long size) {
        logger.info("起始数据位置为：{}",offset);
        logger.info("偏移量为：{}",size);
        List<Article> articleList = articleMapper.getArticleList(userId,offset,size);
        logger.info("查询记录总条数为：{}",articleList.size());
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
