package com.wosys.base.config;


import com.wosys.base.encoder.ClearPasswordEncoder;
import com.wosys.base.encoder.Md5PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        if (Configure.isMd5) {
            return new Md5PasswordEncoder();
        }
        return new ClearPasswordEncoder();
    }
}
