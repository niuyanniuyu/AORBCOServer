package com.aorbco.aorbcoserver.service.serviceImpl;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.aorbco.aorbcoserver.dao.UserDao;
import com.aorbco.aorbcoserver.service.LoginService;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ben
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDao userDao;

    //你好，我是李强，我的熟人是张爱民和李英
    public String userLogin(String text) {
        LinkedList<String> userList = new LinkedList<>();
        try {
            List<Term> seg = new PerceptronLexicalAnalyzer().enableNameRecognize(true).seg(text);
            for (Term term : seg) {
                if (term.nature.equals(Nature.nr)) {
                    userList.add(term.word);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (userList.size() == 0) {
            return ServerConstant.KEY_MAP.get("NEEDRESEND");
        }
        StringBuilder sb = new StringBuilder();
        String userNameO = userList.removeFirst();
        if (userDao.findUserByName(userNameO) != null) {
            sb.append(ServerConstant.KEY_MAP.get("HIA")).insert(sb.length() - 1, userNameO);
        } else {
            sb.append(ServerConstant.KEY_MAP.get("NEWHIA")).insert(sb.length() - 1, userNameO);
        }

        int i = 0;
        for (String userName : userList) {
            if (i++ > 0) {
                sb.insert(sb.length() - 1,ServerConstant.CAESURA_SIGN).insert(sb.length() - 1, userName);
            } else {
                sb.append(ServerConstant.KEY_MAP.get("DISCRIMINATEACQA")).insert(sb.length() - 1, userName);
            }
        }

        return sb.toString();
    }
}
