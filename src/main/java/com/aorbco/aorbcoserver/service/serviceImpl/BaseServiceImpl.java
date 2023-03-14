package com.aorbco.aorbcoserver.service.serviceImpl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.service.BaseService;
import com.aorbco.aorbcoserver.service.LoginService;
import com.aorbco.aorbcoserver.service.PushService;
import com.aorbco.aorbcoserver.utils.SocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public String processMes(String text) {
        //嵌入逻辑处理 ---->
        if (text.startsWith(ServerConstant.KEY_MAP.get("MNQ")) ||
                text.startsWith(ServerConstant.KEY_MAP.get("HMNQ")) ||
                text.startsWith(ServerConstant.KEY_MAP.get("IAQ")) ||
                text.startsWith(ServerConstant.KEY_MAP.get("HIAQ"))) {
            return loginService.userLogin(text);
        } else if (text.startsWith(ServerConstant.KEY_MAP.get("QAQ"))) {

        }
        SocketUtil.send("小龟\t喜欢\t小鱼");

        //----> 嵌入逻辑处理
        String res = SocketUtil.recv();
        return res + ServerConstant.RESPONSE_DELIMITER + "11111111";
    }


}
