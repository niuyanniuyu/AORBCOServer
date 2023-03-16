package com.aorbco.aorbcoserver.dao;

import com.aorbco.aorbcoserver.model.User;

import java.util.Set;

public interface UserGroupDao {
    public Set<String> findGroupMembersByGroupName(String groupName);

    public String findGroupByUserName(String userName);

    public void joinGroup(String userName, String groupName);
}
