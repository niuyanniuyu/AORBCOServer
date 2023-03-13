package com.aorbco.aorbcoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AorbcoServerApplication {
    public static ConfigurableApplicationContext ca;

    public static void main(String[] args) {
        ca = SpringApplication.run(AorbcoServerApplication.class, args);
    }
}
