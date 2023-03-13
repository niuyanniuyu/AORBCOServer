package com.aorbco.aorbcoserver;

import com.aorbco.aorbcoserver.config.SocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AorbcoServerApplication {
    public static ConfigurableApplicationContext ca;

    public static void main(String[] args) {
        ca = SpringApplication.run(AorbcoServerApplication.class, args);
    }
}
