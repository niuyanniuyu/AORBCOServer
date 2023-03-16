package com.aorbco.aorbcoserver.dao.impl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Ben
 */
@Repository
public class UserDaoImpl implements UserDao {
    private final Map<String, User> userMap = new HashMap<>();

    {
        User user1 = new User();
        user1.setUserName("张三");
        List<String> preference1 = new LinkedList<>();
        preference1.add("自然语言处理");
        preference1.add("机器视觉");
        user1.setUserPreference(preference1);
        user1.setPreferenceScore(new HashMap<String, Double>());
        user1.setUserAcquaintance(new HashMap<String, User>());
        userMap.put(user1.getUserName(), user1);

        User user2 = new User();
        user2.setUserName("李四");
        List<String> preference2 = new LinkedList<>();
        preference2.add("通信语言");
        user2.setUserPreference(preference2);
        user2.setPreferenceScore(new HashMap<String, Double>());
        user2.setUserAcquaintance(new HashMap<String, User>());
        userMap.put(user2.getUserName(), user2);
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

    @Override
    public void updateUserPreferencesScore(String name) {
        //通信后端，更新分数

    }

    @Override
    public void insertUser(String name) {
        User user = new User();
        user.setUserName(name);
        List<String> preference = new LinkedList<>();
        user.setUserPreference(preference);
        user.setPreferenceScore(new HashMap<String, Double>());
        user.setUserAcquaintance(new HashMap<String, User>());
        userMap.put(user.getUserName(), user);
    }

    @Override
    public String insertAcquaintance(String name, String acquaintanceName) {
        User user = userMap.get(name);
        User acquaintance = userMap.get(acquaintanceName);
        if (user != null && acquaintance != null) {
            user.userAcquaintance.put(acquaintanceName, acquaintance);
            return ServerConstant.KEY_MAP.get("SA").replace(ServerConstant.KEY_MAP.get("PLACEHOLDER"), acquaintanceName);
        }
        return ServerConstant.KEY_MAP.get("AF").replace(ServerConstant.KEY_MAP.get("PLACEHOLDER"), acquaintanceName);
    }

    @Override
    public String deleteAcquaintance(String name, String acquaintanceName) {
        return null;
    }

    @Override
    public Set<String> getAllAcquaintance(String name) {
        Set<String> userAcquaintance = null;
        User user = userMap.get(name);
        if (user != null) {
            userAcquaintance = user.getUserAcquaintance().keySet();
        }
        return userAcquaintance;
    }


}
