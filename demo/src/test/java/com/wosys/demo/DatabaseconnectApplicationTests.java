// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
class DatabaseconnectApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testOracleConnect() {
        jdbcTemplate.execute("update hmall.item set sold = 2 where id = 317578; ");
    }

}