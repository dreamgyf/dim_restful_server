package com.dreamgyf.dim_restful_server.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dreamgyf.dim_restful_server.entity.User;
import com.dreamgyf.dim_restful_server.entity.dao.FriendEntity;

import org.apache.ibatis.annotations.Param;

public interface FriendMapper extends BaseMapper<FriendEntity> {

    List<User> selectFriend(@Param("id") Integer id);
}