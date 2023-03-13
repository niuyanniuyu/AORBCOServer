package com.aorbco.aorbcoserver;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AorbcoServerApplicationTests {

    @Test
    void contextLoads() {

        String s = "我是张三";
        List<Term> segment = HanLP.segment(s);
        for (Term term : segment) {
            System.out.println(term.nature.ordinal());
        }

        System.out.println(HanLP.segment(s));



    }

}
