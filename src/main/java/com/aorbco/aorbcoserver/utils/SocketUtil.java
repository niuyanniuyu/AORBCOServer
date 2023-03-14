package com.aorbco.aorbcoserver.utils;

import com.aorbco.aorbcoserver.AorbcoServerApplication;
import com.aorbco.aorbcoserver.config.SocketConfig;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

/**
 * @author Ben
 */
@Slf4j
public final class SocketUtil {
    private static byte[] buf = new byte[1024];

    private static Socket socket;

    static {
        try {
            socket = new Socket(AorbcoServerApplication.ca.getBean(SocketConfig.class).getHost(), AorbcoServerApplication.ca.getBean(SocketConfig.class).getPort());

            if ("connect server successfully!".equals(recv())) {
                log.info("已与网站Ego服务建立连接！");
            } else {
                throw new RuntimeException("与网站Ego服务建立连接失败！");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static String recv() {
        try {
            int line = socket.getInputStream().read(buf);
            return new String(buf, 0, line);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static void send(String str) {
        try {
            while (socket==null){
                Thread.sleep(1000);
            }
            socket.getOutputStream().write(str.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public static void closeSocket() {
        try {
            if (socket.getOutputStream() != null) {
                try {
                    socket.getOutputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (socket.getInputStream() != null) {
                try {
                    socket.getInputStream().close();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
