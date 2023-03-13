package com.aorbco.aorbcoserver.dao;

import com.aorbco.aorbcoserver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Ben
 */
@Repository
public class UserDao {
    private final Map<String, User> userMap = new HashMap<>();

    {
        User user = new User();

        user.setUserName("张三");
        List<String> preference = new LinkedList<>();
        preference.add("自然语言处理");
        user.setUserPreference(preference);
        userMap.put(user.getUserName(), user);

        user.setUserName("李四");
        preference.clear();
        preference.add("机器视觉");
        user.setUserPreference(preference);
        userMap.put(user.getUserName(), user);
    }

    public User findUserByName(String name) {
        return userMap.getOrDefault(name, null);
    }

    public boolean addUserPreferences(String name, List<String> preferences) {
        User user = findUserByName(name);
        if (user == null) {
            return false;
        } else {
            user.getUserPreference().addAll(preferences);
            return true;
        }
    }

    public boolean removeUserPreferences(String name, List<String> preferences) {
        User user = findUserByName(name);
        if (user == null) {
            return false;
        } else {
            List<String> userPreference = user.getUserPreference();
            Iterator<String> iterator = userPreference.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (preferences.contains(next)) {
                    iterator.remove();
                }
            }
            return true;
        }
    }

    public boolean removeUserAllPreferences(String name) {
        User user = findUserByName(name);
        if (user == null) {
            return false;
        } else {
            user.getUserPreference().clear();
            return true;
        }
    }
}
