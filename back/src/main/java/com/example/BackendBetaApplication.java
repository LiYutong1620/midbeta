package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.example", "com.ruoyi"})
@EnableScheduling
@MapperScan("com.example.mapper")
public class BackendBetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendBetaApplication.class, args);
    }

}