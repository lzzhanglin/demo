package com.spring.demo.controller;

import com.spring.demo.entity.*;
import com.spring.demo.mapper.CategoryMapper;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;



    @RequestMapping("/getCategory")
    @ResponseBody
    public List<Article> getCategoryByUserId(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
//        Long articleId = Long.valueOf(request.getParameter("articleId"));
        return categoryService.getCategoryByUserId(userId);
    }

    @RequestMapping("/manage")
    public String categoryManage(HttpServletRequest request) {

        return "categoryManage";
    }

    @RequestMapping("/getCategoryList")
    @ResponseBody
    public ReturnPage<List<Category>> getCategoryList(@RequestBody QuertyParams quertyParams,
                                                      @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        Long size =Long.valueOf(quertyParams.getPageSize());
        Long offset = Long.valueOf(quertyParams.getOffset());



        List<Category> categoryList = categoryService.getCategoryList(userId,offset,size);
        Integer total = categoryMapper.getCategoryTotalNum(userId);
        ReturnPage<List<Category>> returnPage = new ReturnPage<>(1,total,categoryList);
        return returnPage;
    }

    @RequestMapping("/view")
    @ResponseBody
    public Category viewCategory(HttpServletRequest request) {
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        return categoryService.viewCategory(categoryId);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Resp updateCategory(HttpServletRequest request) {
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        String categoryName = request.getParameter("categoryName");
        boolean result = categoryService.updateCategory(categoryId,categoryName);
        if (result) {
            return new Resp("success", "update success");

        } else {
            return new Resp("failed", "update failed");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Resp deleteCategory(HttpServletRequest request) {
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        boolean result = categoryService.deleteCategory(categoryId);
        if (result) {
            return new Resp("success", "delete success");
        } else {
            return new Resp("failed", "delete failed");
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public Resp addCategory(HttpServletRequest request,
                            @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        Category category = new Category();
        category.setCategoryName(request.getParameter("categoryName"));
        category.setUserId(userId);
        if (Objects.equals(null, category.getCategoryName())||Objects.equals("",category.getCategoryName())) {
            return new Resp("failed", "add failed");
        }
        boolean result = categoryService.addCategory(category);
        if (result) {
            return new Resp("success", "add success");
        } else {
            return new Resp("failed", "add failed");
        }



    }
}
