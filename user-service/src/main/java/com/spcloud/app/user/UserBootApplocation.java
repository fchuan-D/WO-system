package com.spcloud.app.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans({@ComponentScan("com.wosys.base"), @ComponentScan({"com.spcloud.app.user.config", "com.spcloud.app.user.controller"})})
public class UserBootApplocation {

    public static void main(String[] args) {
        SpringApplication.run(UserBootApplocation.class, args);
    }
}
