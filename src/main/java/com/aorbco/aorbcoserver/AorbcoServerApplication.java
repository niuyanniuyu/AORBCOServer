package com.aorbco.aorbcoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.Socket;

@SpringBootApplication
@EnableConfigurationProperties(Socket.class)
public class AorbcoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AorbcoServerApplication.class, args);
    }

}
