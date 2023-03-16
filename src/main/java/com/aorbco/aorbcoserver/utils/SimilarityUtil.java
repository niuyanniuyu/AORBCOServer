package com.aorbco.aorbcoserver.utils;

import com.aorbco.aorbcoserver.constant.ServerConstant;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;

public class SimilarityUtil {
    public static WordVectorModel wordVectorModel;
    public static DocVectorModel docVectorModel;

    static {
        try {
            wordVectorModel = new WordVectorModel("data/model/W2V/polyglot-zh.model");
            docVectorModel = new DocVectorModel(wordVectorModel);

            Collection<String> values = ServerConstant.KEY_MAP.values();

            int i = 0;
            for (String str : values) {
                docVectorModel.addDocument(i++, str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
