package com.aorbco.aorbcoserver.service.impl;

import com.aorbco.aorbcoserver.service.UserService;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public List<String> identificationSubject(String text) {
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

        return userList;
    }
}
