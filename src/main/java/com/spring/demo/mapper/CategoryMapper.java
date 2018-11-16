package com.spring.demo.mapper;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    public List<Article> getCategoryByUserId(Long userId);

    public List<Category> getCategoryList(@Param("userId") Long userId,
                                          @Param("offset") Long offset,
                                          @Param("size") Long size);

    public int getCategoryTotalNum(Long userId);

    public Category viewCategory(Long categoryId);

    public int updateCategory(@Param("categoryId")Long categoryId,
                              @Param("categoryName")String categoryName);

    public int deleteCategory(Long categoryId);

    public int addCategory(Category category);
}
