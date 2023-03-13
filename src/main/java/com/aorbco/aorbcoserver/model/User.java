package com.aorbco.aorbcoserver.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class User {
    private String userName;
    private List<String> userPreference;
}
