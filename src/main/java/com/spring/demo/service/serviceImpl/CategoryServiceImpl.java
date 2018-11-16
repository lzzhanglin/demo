package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.Article;
import com.spring.demo.entity.Category;
import com.spring.demo.mapper.CategoryMapper;
import com.spring.demo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryMapper categoryMapper;
    public List<Article> getCategoryByUserId(Long userId) {
        return categoryMapper.getCategoryByUserId(userId);
    }

    public List<Category> getCategoryList(Long userId, Long offset, Long size) {
        logger.info("用户id为：{}",userId);
        logger.info("起始数据位置为：{}",offset);
        logger.info("偏移量为：{}",size);
        List<Category> categoryList = categoryMapper.getCategoryList(userId,offset,size);
        logger.info("查询记录总条数为：{}",categoryList.size());
        return categoryList;
    }

    public Category viewCategory(Long categoryId) {
        return categoryMapper.viewCategory(categoryId);
    }

    public boolean updateCategory(Long categoryId, String categoryName) {

        int resultRow =  categoryMapper.updateCategory(categoryId, categoryName);
        logger.info("更新行数为：{}",resultRow);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCategory(Long categoryId) {
        int resultRow = categoryMapper.deleteCategory(categoryId);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addCategory(Category category) {
        int resultRow = categoryMapper.addCategory(category);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }
}
