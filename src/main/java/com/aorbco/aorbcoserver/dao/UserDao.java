package com.aorbco.aorbcoserver.dao;

import com.aorbco.aorbcoserver.model.User;

import java.util.List;
import java.util.Set;

/**
 * @author Ben
 */
public interface UserDao {
    public User findUserByName(String name);

    public boolean addUserPreferences(String name, List<String> preferences);

    public boolean removeUserPreferences(String name, List<String> preferences);

    public boolean removeUserAllPreferences(String name);

    public void updateUserPreferencesScore(String name);

    public void insertUser(String name);

    public String insertAcquaintance(String name, String acquaintanceName);

    public String deleteAcquaintance(String name, String acquaintanceName);

    public Set<String> getAllAcquaintance(String name);
}
