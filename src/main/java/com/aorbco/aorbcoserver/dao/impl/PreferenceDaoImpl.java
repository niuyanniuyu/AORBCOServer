package com.aorbco.aorbcoserver.dao.impl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.PreferenceDao;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.model.Preference;
import com.aorbco.aorbcoserver.model.User;
import com.aorbco.aorbcoserver.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PreferenceDaoImpl implements PreferenceDao {
    @Autowired
    UserDao userDao;

    // 领域名称：[领域名称：Set<内容1,内容2,内容3...>]
    private final Map<String, Preference> preferenceMap = new HashMap<>();

    {
        Preference preference1 = new Preference();
        preference1.setName("自然语言处理");
        Set<String> contents1 = new HashSet<>();
        contents1.add("[对话系统-预训练]|General-Purpose Question-Answering with MACAW|https://arxiv.org/pdf/2109.02593.pdf");
        contents1.add("[综述-数据增强]|Data Augmentation Approaches in Natural Language Processing: A Survey|https://arxiv.org/pdf/2110.01852.pdf");
        contents1.add("[综述-向量召回]|A Comprehensive Survey and Experimental Comparison of Graph-Based Approximate Nearest Neighbor Search|https://arxiv.org/pdf/2101.12631.pdf");
        preference1.setContent(contents1);
        preferenceMap.put(preference1.getName(), preference1);

        Preference preference2 = new Preference();
        preference2.setName("机器视觉");
        Set<String> contents2 = new HashSet<>();
        contents2.add("[模型]|DELIGHT: DEEP AND LIGHT-WEIGHT TRANSFORMER|https://arxiv.org/abs/2008.00623.pdf");
        preference2.setContent(contents2);
        preferenceMap.put(preference2.getName(), preference2);

        Preference preference3 = new Preference();
        preference3.setName("通信语言");
        Set<String> contents3 = new HashSet<>();
        preference3.setContent(contents3);
        preferenceMap.put(preference3.getName(), preference3);
    }

    // 内容网址：领域名称
    private final Map<String, String> contentUrl2preferenceMap = new HashMap<>();

    {
        contentUrl2preferenceMap.put("https://arxiv.org/pdf/2109.02593.pdf", "自然语言处理");
        contentUrl2preferenceMap.put("https://arxiv.org/pdf/2110.01852.pdf", "自然语言处理");
        contentUrl2preferenceMap.put("https://arxiv.org/pdf/2101.12631.pdf", "自然语言处理");
        contentUrl2preferenceMap.put("https://arxiv.org/abs/2008.00623.pdf", "机器视觉");
    }


    /**
     * 0用户没有偏好;1有偏好没有内容;2存在内容
     *
     * @param userName
     * @return
     */
    @Override
    public int canMatchPreference(String userName) {
        User user = userDao.findUserByName(userName);
        if (user != null && user.getUserPreference().size() > 0) {
            Set<String> pset = new HashSet<>(preferenceMap.keySet());
            pset.addAll(user.getUserPreference());
            return pset.size() == (preferenceMap.keySet().size() + user.getUserPreference().size()) ? 1 : 2;
        }
        return 0;
    }

    @Override
    public String matchPreference(String userName) {
        User user = userDao.findUserByName(userName);
        List<String> userPreference = user.getUserPreference();
        StringBuilder sb = new StringBuilder();
        for (String pname : userPreference) {
            Preference preference = preferenceMap.get(pname);
            if (preference != null) {
                int num = RandomUtil.rInt(1, preference.getContent().size());
                for (String content : preference.getContent()) {
                    if (num-- == 0) {
                        break;
                    }
                    sb.append(ServerConstant.RESPONSE_DELIMITER).append(content);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 0当前领域没有内容;1当前领域有内容
     *
     * @param preferenceName
     * @return
     */
    @Override
    public int havePreferenceContent(String preferenceName) {
        return preferenceMap.get(preferenceName).getContent().size() == 0 ? 0 : 1;
    }

    @Override
    public Set<String> getAllPreferenceContent(String preferenceName) {
        return preferenceMap.get(preferenceName).getContent();
    }

    /**
     * 形式：content1&&&&content2&&&&content3
     *
     * @param preferenceName
     * @param num
     * @return
     */
    @Override
    public String getNonrandomPreferenceContent(String preferenceName, int num) {
        StringBuilder sb = new StringBuilder();
        if (havePreferenceContent(preferenceName) != 0) {
            Set<String> allPreferenceContent = getAllPreferenceContent(preferenceName);
            num = Math.min(num, allPreferenceContent.size());
            Iterator<String> iterator = allPreferenceContent.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (i >= num) {
                    break;
                }
                if (i++ != 0) {
                    sb.append(ServerConstant.RESPONSE_DELIMITER);
                }
                sb.append(next);
            }

        }
        return sb.toString();
    }

    @Override
    public String getRandomPreferenceContent(String preferenceName) {
        String res = "";
        if (havePreferenceContent(preferenceName) != 0) {
            int num = RandomUtil.rInt(1, getAllPreferenceContent(preferenceName).size());
            res = getNonrandomPreferenceContent(preferenceName, num);
        }
        return res;
    }

    @Override
    public String getPreferenceNameByUrl(String url) {
        return contentUrl2preferenceMap.get(url);
    }
}
