package com.aorbco.aorbcoserver.constant;

import com.hankcs.hanlp.corpus.tag.Nature;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben
 */
public class PartOfSpeechConstant {
    public static final Map<Nature, String> PART_OF_SPEECH_MAP = new HashMap<Nature, String>() {{
        PART_OF_SPEECH_MAP.put(Nature.a,"adj");
        PART_OF_SPEECH_MAP.put(Nature.ag,"adj");
        PART_OF_SPEECH_MAP.put(Nature.al,"adj");
        PART_OF_SPEECH_MAP.put(Nature.an,"adj");
        PART_OF_SPEECH_MAP.put(Nature.ad,"adv");
    }};

}
