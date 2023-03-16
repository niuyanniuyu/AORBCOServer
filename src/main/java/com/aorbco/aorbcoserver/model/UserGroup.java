package com.aorbco.aorbcoserver.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserGroup {
    private String groupName;
    private Set<String> groupMembers;
}
