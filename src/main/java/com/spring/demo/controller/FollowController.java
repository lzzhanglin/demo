package com.spring.demo.controller;


import com.spring.demo.entity.FollowEntity;
import com.spring.demo.entity.Resp;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/follow")
public class FollowController {

   @Autowired
   private FollowService followService;

   @Autowired
   private UserMapper userMapper;


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
}
