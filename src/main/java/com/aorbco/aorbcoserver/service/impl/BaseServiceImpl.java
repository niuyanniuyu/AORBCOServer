package com.aorbco.aorbcoserver.service.impl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.PreferenceDao;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.dao.UserGroupDao;
import com.aorbco.aorbcoserver.model.User;
import com.aorbco.aorbcoserver.service.BaseService;
import com.aorbco.aorbcoserver.service.LoginService;
import com.aorbco.aorbcoserver.service.PushService;
import com.aorbco.aorbcoserver.service.UserService;
import com.aorbco.aorbcoserver.utils.ContextUtil;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Ben
 */
@Service
@Slf4j
public class BaseServiceImpl implements BaseService {
    @Autowired
    PushService pushService;

    @Autowired
    LoginService loginService;

    @Autowired
    PreferenceDao preferenceDao;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserGroupDao userGroupDao;

    public String processMes(String text) {
        //嵌入逻辑处理 ---->
        if (text.startsWith(ServerConstant.KEY_MAP.get("MNQ")) || text.startsWith(ServerConstant.KEY_MAP.get("HMNQ")) || text.startsWith(ServerConstant.KEY_MAP.get("IAQ")) || text.startsWith(ServerConstant.KEY_MAP.get("HIAQ"))) {
            String pre = loginService.userLogin(text);
            if (!ServerConstant.KEY_MAP.get("NEEDRESEND").equals(pre)) {
                StringBuilder sb = new StringBuilder(pre);
                //加入推荐标题
                sb.append(ServerConstant.RESPONSE_DELIMITER);
                if (!pushService.needRecommendKeyWords()) {
                    sb.append(ServerConstant.KEY_MAP.get("UTGPFTTBPEYPTHMEMY"));
                } else {
                    sb.append(pushService.recommendKeyWords());
                    //加入推荐内容
                    sb.append(ServerConstant.RESPONSE_DELIMITER);
                    int i = preferenceDao.canMatchPreference(ContextUtil.presentUser);
                    switch (i) {
                        case 1:
                            sb.append(ServerConstant.KEY_MAP.get("MEHNIYKIYPFYCCPOECAEWHYGSAK"));
                            break;
                        case 2:
                            sb.append(ServerConstant.KEY_MAP.get("TFATSCOSAOC"));
                            sb.append(preferenceDao.matchPreference(ContextUtil.presentUser));
                            break;
                        default:
                            sb.append(ServerConstant.KEY_MAP.get("UTGPFTTBPEYPTHMEMY"));
                            break;
                    }
                }
                return sb.toString();
            }

            return loginService.userLogin(pre);
        } else if (text.startsWith(ServerConstant.KEY_MAP.get("MAI")) || text.startsWith(ServerConstant.KEY_MAP.get("MFIQ"))) {
            List<String> acquaintances = userService.identificationSubject(text);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (String acquaintance : acquaintances) {
                if (userDao.findUserByName(acquaintance) == null) {
                    userDao.insertUser(acquaintance);
                }
                if (i++ != 0) {
                    sb.append(ServerConstant.RESPONSE_DELIMITER);
                }
                sb.append(userDao.insertAcquaintance(ContextUtil.presentUser, acquaintance));
            }
            String res = sb.toString();
            if (res.contains(ServerConstant.KEY_MAP.get("S"))) {
                res += ServerConstant.RESPONSE_DELIMITER + ServerConstant.KEY_MAP.get("NTRTPOA");
                ContextUtil.presentContext = ServerConstant.KEY_MAP.get("AP");
            }

            return res;
        } else if (text.equals(ServerConstant.KEY_MAP.get("N")) || text.equals(ServerConstant.KEY_MAP.get("O"))) {
            if (ContextUtil.presentContext.equals(ServerConstant.KEY_MAP.get("AP"))) {
                Set<String> allAcquaintance = userDao.getAllAcquaintance(ContextUtil.presentUser);
                Set<String> allPreference = new HashSet<>();
                for (String acq : allAcquaintance) {
                    List<String> userPreference = userDao.findUserByName(acq).getUserPreference();
                    allPreference.addAll(userPreference);
                }
                if (allPreference.size() == 0) {
                    return ServerConstant.KEY_MAP.get("ACHNP");
                }
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (String preference : allPreference) {
                    if (i++ != 0) {
                        sb.append(ServerConstant.RESPONSE_DELIMITER);
                    }
                    switch (preferenceDao.havePreferenceContent(preference)) {
                        case 1:
                            sb.append(preferenceDao.getNonrandomPreferenceContent(preference, 1));
                            break;
                        default:
                            break;
                    }
                }
                if (sb.length() == 0) {
                    return ServerConstant.KEY_MAP.get("TKOAYAPFHNBIINEFTTB");
                }
                return sb.insert(0, ServerConstant.KEY_MAP.get("TFARBA") + ServerConstant.RESPONSE_DELIMITER).toString();

            } else if (ContextUtil.presentContext.equals(ServerConstant.KEY_MAP.get("JG"))) {
                String preference = preferenceDao.getPreferenceNameByUrl(ContextUtil.presentUrl);
                StringBuilder sb = new StringBuilder();
                //更新用户偏好
                User user = userDao.findUserByName(ContextUtil.presentUser);
                if (!user.getUserPreference().contains(preference)) {
                    user.getUserPreference().add(preference);
                    sb.append(ServerConstant.KEY_MAP.get("EHEPFY").replace(ServerConstant.KEY_MAP.get("PLACEHOLDER"), preference));
                }
                //更新组信息
                userGroupDao.joinGroup(ContextUtil.presentUser, preference + ServerConstant.KEY_MAP.get("G"));
                if (sb.length() > 0) {
                    sb.append(ServerConstant.RESPONSE_DELIMITER);
                }
                return sb.append(ServerConstant.KEY_MAP.get("YHJTG").replace(ServerConstant.KEY_MAP.get("PLACEHOLDER"), preference + ServerConstant.KEY_MAP.get("G"))).toString();

            } else {
                return ServerConstant.KEY_MAP.get("NEEDRESEND");
            }

        } else if (text.equals(ServerConstant.KEY_MAP.get("WAY"))) { //杂项问答
            return ServerConstant.KEY_MAP.get("IMCE");
        }
        return ServerConstant.KEY_MAP.get("NEEDRESEND");


//        SocketUtil.send("小龟\t喜欢\t小鱼");
//
//        //----> 嵌入逻辑处理
//        String res = SocketUtil.recv();
//        return res + ServerConstant.RESPONSE_DELIMITER + "11111111";
    }

    public String onclickUrl(String url) {
        String preferenceName = preferenceDao.getPreferenceNameByUrl(url);
        if (!StringUtils.isEmpty(preferenceName)) {
            Set<String> groupMembers = userGroupDao.findGroupMembersByGroupName(preferenceName + ServerConstant.KEY_MAP.get("G"));
            if (groupMembers != null) {
                StringBuilder sb = new StringBuilder(ServerConstant.KEY_MAP.get("TPITFITMITGH").replace(ServerConstant.KEY_MAP.get("PLACEHOLDER"), preferenceName + ServerConstant.KEY_MAP.get("G")));
                //TODO 待删除
                System.out.println(sb.toString());
                int i = 0;
                for (String name : groupMembers) {
                    if (i++ != 0) {
                        sb.insert(sb.length() - 1, ServerConstant.CAESURA_SIGN);
                    }
                    sb.insert(sb.length() - 1, name);
                }
                ContextUtil.presentUrl = url;
                ContextUtil.presentContext = ServerConstant.KEY_MAP.get("JG");
                return sb.append(ServerConstant.RESPONSE_DELIMITER).append(ServerConstant.KEY_MAP.get("NTJTG")).toString();
            }
        }
        return ServerConstant.KEY_MAP.get("TPIOTLCBPBTMEODTTQT");
    }


}
