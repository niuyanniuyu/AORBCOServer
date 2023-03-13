package com.aorbco.aorbcoserver.service;

import com.aorbco.aorbcoserver.utils.SocketUtil;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author Ben
 */
@Service
@Slf4j
public class BaseService {

    public String processMes(String text) {
        //嵌入逻辑处理 ---->
        SocketUtil.send("小龟\t喜欢\t小鱼");




        //----> 嵌入逻辑处理
        String res = SocketUtil.recv();
        return res + "&&&&" + "11111111111111111111111111111111111111fdasdsadasdasd";
    }


}
