package com.aorbco.aorbcoserver.dao;

import java.util.Set;

public interface PreferenceDao {

    public int canMatchPreference(String userName);

    public String matchPreference(String userName);

    public int havePreferenceContent(String preferenceName);

    public Set<String> getAllPreferenceContent(String preferenceName);
    public String getNonrandomPreferenceContent(String preferenceName, int num);
    public String getRandomPreferenceContent(String preferenceName);

    public String getPreferenceNameByUrl(String url);
}
