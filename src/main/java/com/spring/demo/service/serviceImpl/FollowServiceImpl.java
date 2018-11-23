package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.FollowEntity;
import com.spring.demo.entity.SearchUser;
import com.spring.demo.mapper.FollowMapper;
import com.spring.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean cancelFollow(FollowEntity followEntity) {
        int resultRow = followMapper.cancelFollow(followEntity);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<SearchUser> getFollowList(Long currentUserId,Long offset, Long size) {
        return followMapper.getFollowList(currentUserId,offset,size);
    }

    public List<SearchUser> getFollowerList(Long currentUserId,Long offset, Long size) {
        return followMapper.getFollowerList(currentUserId,offset,size);
    }

    public boolean removeFan(Long followUserId, Long userId) {
        int resultRow = followMapper.removeFan(followUserId, userId);
        if (resultRow == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFriend(Long aUserId,Long bUserId) {
        int resultA = followMapper.isFriendA(aUserId, bUserId);
        int resultB = followMapper.isFriendB(aUserId, bUserId);
        if (resultA == 1 && resultB == 1) {
            return true;
        } else {
            return false;
        }
    }
}
