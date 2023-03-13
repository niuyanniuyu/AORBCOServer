package com.aorbco.aorbcoserver.service;

import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

@Service
@Slf4j
public class BaseService {

    public String testTest(String text) {
        List<String> strings = HanLP.extractKeyword(text, 3);


        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            socket = new Socket("192.168.199.117", 8001);
            outputStream = socket.getOutputStream();
            outputStream.write("小龟\t喜欢\t小鱼".getBytes());

            byte[] buf = new byte[1024];
            int line = socket.getInputStream().read(buf);
            String status = new String(buf, 0, line);

            String res;
            if ("connect server successfully!".equals(status)){
                line = socket.getInputStream().read(buf);
                res = new String(buf, 0, line);
                System.out.println(res);
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return HanLP.segment(text).toString() + "&&&&" + "11111111111111111111111111111111111111fdasdsadasdasd";
    }


}
