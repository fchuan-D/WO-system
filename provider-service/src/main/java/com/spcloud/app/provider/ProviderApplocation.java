package com.spcloud.app.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans({@ComponentScan("com.wosys.base")})
@MapperScan("com.spcloud.app.provider.mapper")
public class ProviderApplocation {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplocation.class, args);
    }
}
