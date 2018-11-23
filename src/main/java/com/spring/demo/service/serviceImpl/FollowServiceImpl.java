package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.FollowEntity;
import com.spring.demo.mapper.FollowMapper;
import com.spring.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("followService")
public class FollowServiceImpl implements FollowService {


    @Autowired
    private FollowMapper followMapper;

    public boolean addFollow(FollowEntity followEntity) {

        int resultRow = followMapper.addFollow(followEntity);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }

    }

    public int isFollowCurrentUser(Long followUserId, Long userId) {
        return followMapper.isFollowCurrentUser(followUserId, userId);

    }
}
