package com.aorbco.aorbcoserver.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben
 */
@Slf4j
public class ServerConstant {
    public static final String RESPONSE_DELIMITER = "&&&&";

    public static final String CAESURA_SIGN = "、";

    public static Map<String, String> KEY_MAP;

    static {
        KEY_MAP = new HashMap<>();

        Resource resource;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            resource = new ClassPathResource("key.aor");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data = null;
            while ((data = br.readLine()) != null) {
                String[] split = data.split("=");
                if (split.length == 2) {
                    KEY_MAP.put(split[0], split[1]);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }


}
