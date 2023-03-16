package com.aorbco.aorbcoserver.service.impl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.model.User;
import com.aorbco.aorbcoserver.service.PushService;
import com.aorbco.aorbcoserver.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ben
 */
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    UserDao userDao;

    @Override
    public String recommendKeyWords() {
        StringBuilder sb = new StringBuilder(ServerConstant.KEY_MAP.get("ATTPIPYPI"));
        for (String preference : userDao.findUserByName(ContextUtil.presentUser).getUserPreference()) {
            sb.insert(sb.length() - 1, preference).insert(sb.length() - 1, ServerConstant.CAESURA_SIGN);
        }
        return sb.delete(sb.length() - 2, sb.length() - 1).toString();
    }

    @Override
    public boolean needRecommendKeyWords() {
        User user = userDao.findUserByName(ContextUtil.presentUser);
        return user.getUserPreference().size() != 0;
    }
}
