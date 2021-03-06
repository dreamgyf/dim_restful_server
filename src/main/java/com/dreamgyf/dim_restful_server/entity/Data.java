package com.dreamgyf.dim_restful_server.entity;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private User my;

    private List<User> friendList;

    private List<Group> groupList;


    public User getMy() {
        return this.my;
    }

    public void setMy(User my) {
        this.my = my;
    }

    public List<User> getFriendList() {
        return this.friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    public List<Group> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

}
