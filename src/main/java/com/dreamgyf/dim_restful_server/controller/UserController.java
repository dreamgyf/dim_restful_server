package com.dreamgyf.dim_restful_server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dreamgyf.dim_restful_server.entity.Data;
import com.dreamgyf.dim_restful_server.entity.User;
import com.dreamgyf.dim_restful_server.entity.dao.FriendEntity;
import com.dreamgyf.dim_restful_server.entity.dao.UserEntity;
import com.dreamgyf.dim_restful_server.mapper.FriendMapper;
import com.dreamgyf.dim_restful_server.mapper.UserMapper;
import com.dreamgyf.dim_restful_server.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendMapper friendMapper;

    @RequestMapping("/signin")
    public R signin(@RequestBody Map<String,String> params) {
        String username = params.get("username");
        String password = params.get("password");
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username).eq("password", password));
        if(userEntity != null) {
            Data data = new Data();
            User my = new User();
            my.setId(userEntity.getId());
            my.setUsername(userEntity.getUsername());
            my.setNickname(userEntity.getNickname());
            data.setMy(my);
            List<User> friendList = friendMapper.selectFriend(userEntity.getId());
            data.setFriendList(friendList);
            return R.ok().put("data", data);    
        }
        else
            return R.error("用户名或密码错误");
    }

    @RequestMapping("/userinfo")
    public User userinfo(@RequestBody Map<String,Object> params) {
        Integer myId = Integer.valueOf(params.get("myId").toString());
        Integer friendId = Integer.valueOf(params.get("friendId").toString());
        UserEntity userEntity = userMapper.selectById(friendId);
        FriendEntity friendEntity = friendMapper.selectOne(new QueryWrapper<FriendEntity>().eq("user_id", myId).eq("friend_id", friendId));
        User user = new User();
        user.setId(friendId);
        user.setUsername(userEntity.getUsername());
        user.setNickname(userEntity.getNickname());
        if(friendEntity != null) {
            user.setRemarkName(friendEntity.getRemakeName());
        }
        return user;
    }

    @RequestMapping("/friend/search")
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
