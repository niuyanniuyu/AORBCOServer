package com.aorbco.aorbcoserver.dao.impl;

import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.dao.UserGroupDao;
import com.aorbco.aorbcoserver.model.User;
import com.aorbco.aorbcoserver.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserGroupDaoImpl implements UserGroupDao {

    @Autowired
    UserDao userDao;

    private final Map<String, UserGroup> groupMap = new HashMap<>();

    {
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setGroupName("自然语言处理组");
        HashSet<String> members1 = new HashSet<>();
        members1.add("张三");
        userGroup1.setGroupMembers(members1);
        groupMap.put(userGroup1.getGroupName(), userGroup1);


        UserGroup userGroup2 = new UserGroup();
        userGroup2.setGroupName("通信语言组");
        HashSet<String> members2 = new HashSet<>();
        members2.add("李四");
        userGroup2.setGroupMembers(members2);
        groupMap.put(userGroup2.getGroupName(), userGroup2);
    }


    @Override
    public Set<String> findGroupMembersByGroupName(String groupName) {
        return groupMap.get(groupName) != null ? groupMap.get(groupName).getGroupMembers() : null;
    }

    @Override
    public String findGroupByUserName(String userName) {

        return null;
    }

    @Override
    public void joinGroup(String userName, String groupName) {
        User user = userDao.findUserByName(userName);
        UserGroup group = groupMap.get(groupName);
        if (user != null && group != null) {
            group.getGroupMembers().add(userName);
        }
    }
}
