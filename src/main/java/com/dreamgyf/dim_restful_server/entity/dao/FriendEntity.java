package com.dreamgyf.dim_restful_server.entity.dao;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("friend")
public class FriendEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer FriendId;
    
    private String remarkName;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return this.FriendId;
    }

    public void setFriendId(Integer FriendId) {
        this.FriendId = FriendId;
    }

    public String getRemakeName() {
        return this.remarkName;
    }

    public void setRemakeName(String remakeName) {
        this.remarkName = remakeName;
    }


}
