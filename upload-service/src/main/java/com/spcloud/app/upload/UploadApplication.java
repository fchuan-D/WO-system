package com.spcloud.app.upload;

import com.spcloud.app.upload.config.Configure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({@ComponentScan("com.wosys.base")})
public class UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
        System.out.println(Configure.UPLOAD_DIR);
    }
}
