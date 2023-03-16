package com.aorbco.aorbcoserver.model;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Ben
 */
@Data
public class User {
    private String userName;
    private List<String> userPreference;
    public Map<String, Double> preferenceScore;
    public Map<String, User> userAcquaintance;
}
