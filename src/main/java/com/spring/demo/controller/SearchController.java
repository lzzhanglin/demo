package com.spring.demo.controller;

import com.spring.demo.entity.*;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.FollowService;
import com.spring.demo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unbescape.html.HtmlEscape;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowService followService;



    @RequestMapping("/result")
    public String toResultPage(HttpServletRequest request) {
        return "/searchResult";
    }

    @RequestMapping("/userList")
    @ResponseBody
    public ReturnPage<List<SearchUser>> getUserList(HttpServletRequest request,
                                                    @RequestBody QuertyParams quertyParams,
                                                    @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());

        Long size =Long.valueOf(quertyParams.getPageSize());
        Long offset = Long.valueOf(quertyParams.getOffset());

        String keyWord = quertyParams.getKeyWord();
        logger.info("输入的参数为："+keyWord);



        List<SearchUser> userList = searchService.searchUser(keyWord,offset,size);
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == userId) {
                index = i;
            }
            userList.get(i).setIsFollowCurrentUser(followService.isFollowCurrentUser(userList.get(i).getUserId(),userId));
        }
        if (index == -1) {
            //当前用户不在此List之中，不用删除当前用户
        } else {
            userList.remove(index);

        }
        Integer total = userMapper.searchUserTotal(keyWord);
        if (index != -1) {
            total -= 1;
        }
        ReturnPage<List<SearchUser>> returnPage = new ReturnPage<>(1,total,userList);
        return returnPage;
    }

}
