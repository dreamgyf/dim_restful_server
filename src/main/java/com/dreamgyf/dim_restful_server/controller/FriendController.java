package com.dreamgyf.dim_restful_server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dreamgyf.dim_restful_server.entity.User;
import com.dreamgyf.dim_restful_server.entity.dao.UserEntity;
import com.dreamgyf.dim_restful_server.mapper.FriendMapper;
import com.dreamgyf.dim_restful_server.mapper.UserMapper;
import com.dreamgyf.dim_restful_server.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/friend")
public class FriendController {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendMapper friendMapper;

    @RequestMapping("/search")
    public R search(@RequestParam("myId") Integer myId, @RequestParam("keyword") String keyword) {
        List<UserEntity> userList = userMapper.selectList(new QueryWrapper<UserEntity>().eq("id", keyword).or().like("username", keyword).or().like("nickname", keyword));
        List<Integer> excludeList = new ArrayList<>();
        excludeList.add(userMapper.selectById(myId).getId());
        List<User> friendList = friendMapper.selectFriend(myId);
        excludeList.addAll(friendList.stream().map(User::getId).collect(Collectors.toList()));
        for(int i = userList.size() - 1;i >= 0;i--) {
            for(Integer id : excludeList) {
                if(userList.get(i).getId().equals(id)) {
                    userList.remove(i);
                    break;
                }
            }
        }
        return R.ok().put("data", new HashMap<String, Object>(){{put("friendList", userList);}});
    }

    

}