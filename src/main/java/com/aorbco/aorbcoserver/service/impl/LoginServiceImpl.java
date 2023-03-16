package com.aorbco.aorbcoserver.service.impl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.service.LoginService;
import com.aorbco.aorbcoserver.service.UserService;
import com.aorbco.aorbcoserver.utils.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ben
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    //你好，我是李强，我的熟人是张爱民和李英
    public String userLogin(String text) {
        List<String> userList = userService.identificationSubject(text);

        if (userList.size() == 0) {
            return ServerConstant.KEY_MAP.get("NEEDRESEND");
        }
        StringBuilder sb = new StringBuilder();
        String userNameO = userList.remove(0);
        ContextUtil.presentUser = userNameO;
        if (userDao.findUserByName(userNameO) != null) {
            sb.append(ServerConstant.KEY_MAP.get("HIA")).insert(sb.length() - 1, userNameO);
        } else {
            sb.append(ServerConstant.KEY_MAP.get("NEWHIA")).insert(sb.length() - 1, userNameO);
            //TODO 添加新用户到列表中，通知数据库修改
            userDao.insertUser(userNameO);
        }

        int i = 0;
        for (String userName : userList) {
            if (i++ > 0) {
                sb.insert(sb.length() - 1, ServerConstant.CAESURA_SIGN).insert(sb.length() - 1, userName);
            } else {
                sb.append(ServerConstant.KEY_MAP.get("DISCRIMINATEACQA")).insert(sb.length() - 1, userName);
            }
            if (userDao.findUserByName(userName) == null) {
                userDao.insertUser(userName);
            }
            userDao.insertAcquaintance(userNameO, userName);
            userDao.insertAcquaintance(userName, userNameO);
        }

        return sb.toString();
    }
}
