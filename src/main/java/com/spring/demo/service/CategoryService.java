package com.spring.demo.service;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Article> getCategoryByUserId(Long userId);

    public List<Category> getCategoryList(Long userId, Long offset, Long size);

    public Category viewCategory(Long categoryId);

    public boolean updateCategory(Long categoryId, String categoryName);

    public boolean deleteCategory(Long categoryId);

    public boolean addCategory(Category category);
}
