package com.spring.demo.controller;


import com.spring.demo.entity.*;
import com.spring.demo.mapper.FollowMapper;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/follow")
public class FollowController {

   @Autowired
   private FollowService followService;

   @Autowired
   private UserMapper userMapper;

   @Autowired
   private FollowMapper followMapper;


    @RequestMapping("/add")
    @ResponseBody
    public Resp addFollow(HttpServletRequest request,@AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        String uId = request.getParameter("userId");
        if (uId == "") {
            throw new IllegalArgumentException("userId为空");
        }
        Long followUserId = Long.valueOf(uId);
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowUserId(followUserId);
        followEntity.setUserId(userId);
        boolean result = followService.addFollow(followEntity);
        if (result) {
            return new Resp("success", "add follow success");
        } else {
            return new Resp("failed", "add follow success");
        }
    }

    @RequestMapping("/cancel")
    @ResponseBody
    public Resp cancelFollow(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        Long userId = userMapper.getUserIdByName(user.getUsername());
        String uId = request.getParameter("userId");
        if (uId == "") {
            throw new IllegalArgumentException("userId为空");
        }
        Long followUserId = Long.valueOf(uId);
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowUserId(followUserId);
        followEntity.setUserId(userId);
        boolean result = followService.cancelFollow(followEntity);
        if (result) {
            return new Resp("success", "cancel follow success");
        } else {
            return new Resp("failed", "cancel follow success");
        }

    }

    @RequestMapping("/manage")
    public String followManage(HttpServletRequest request) {
        return "followManage";
    }

    @RequestMapping("/getFollowList")
    @ResponseBody
    public ReturnPage<List<SearchUser>> followManage(HttpServletRequest request,
                                                     @RequestBody QuertyParams quertyParams,
                                                     @AuthenticationPrincipal UserDetails user) {
        Long size =Long.valueOf(quertyParams.getPageSize());
        Long offset = Long.valueOf(quertyParams.getOffset());
        Long userId = userMapper.getUserIdByName(user.getUsername());
        List<SearchUser> userList = followService.getFollowList(userId,offset,size);
        for (SearchUser searchUser : userList) {
            searchUser.setIsFollowCurrentUser(1);
            if (followService.isFriend(userId, searchUser.getUserId())) {
                searchUser.setIsFriend(1);
            } else {
                searchUser.setIsFriend(0);
            }
        }
        int total = followMapper.getFollowTotal(userId);
        ReturnPage<List<SearchUser>> returnPage = new ReturnPage<>(1,total,userList);
        return returnPage;

    }

    @RequestMapping("/fanManage")
    public String fanManage(HttpServletRequest request) {
        return "followerManage";
    }

    @RequestMapping("/getFollowerList")
    @ResponseBody
    public ReturnPage<List<SearchUser>> followerManage(HttpServletRequest request,
                                                     @RequestBody QuertyParams quertyParams,
                                                     @AuthenticationPrincipal UserDetails user) {
        Long size =Long.valueOf(quertyParams.getPageSize());
        Long offset = Long.valueOf(quertyParams.getOffset());
        Long userId = userMapper.getUserIdByName(user.getUsername());
        List<SearchUser> userList = followService.getFollowerList(userId,offset,size);
        for (SearchUser searchUser : userList) {
            searchUser.setIsFollowCurrentUser(1);
            if (followService.isFriend(userId, searchUser.getUserId())) {
                searchUser.setIsFriend(1);
            } else {
                searchUser.setIsFriend(0);
            }
        }
        int total = followMapper.getFollowerTotal(userId);
        ReturnPage<List<SearchUser>> returnPage = new ReturnPage<>(1,total,userList);
        return returnPage;

    }

    @RequestMapping("/removeFan")
    @ResponseBody
    public Resp removeFan(HttpServletRequest request,
                          @AuthenticationPrincipal UserDetails user) {
        String string = request.getParameter("userId");
        if (Objects.equals("", string) || Objects.equals(null, string)) {
            return new Resp("failed", "remove failed");
        }
        Long userId = Long.valueOf(string);
        Long followUserId = userMapper.getUserIdByName(user.getUsername());
        boolean result = followService.removeFan(followUserId, userId);
        if (result) {
            return new Resp("success", "remove success");
        } else {
            return new Resp("failed", "remove failed");
        }
    }





}
