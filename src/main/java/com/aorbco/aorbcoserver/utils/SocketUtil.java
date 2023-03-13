package com.aorbco.aorbcoserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Ben
 */
@Slf4j
@Component
public final class SocketUtil {

    private static String socketHost;

    @Value("${socket.host}")
    public void setSocketHost(String val) {
        socketHost = val;
    }

    @Value("${socket.port}")
    private static int socketPort;

    private static byte[] buf = new byte[1024];


    private static Socket socket;

    static {
        try {
            socket = new Socket(socketHost, socketPort);
            byte[] buf = new byte[128];

            if ("connect server successfully!".equals(recv())) {
                log.info("已与网站Ego服务建立连接！");
            } else {
                throw new RuntimeException("与网站Ego服务建立连接失败！");
            }
        } catch (IOException e) {
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
