// @author:樊川
// @email:945001786@qq.com
package com.wosys.userservice;

import com.wosys.common.domain.po.User;
import com.wosys.userservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DatabaseconnectApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void testDBConnect() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

}