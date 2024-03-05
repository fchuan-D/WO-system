// @author:樊川
// @email:945001786@qq.com
package com.wosys.jobservice.controller;

import com.wosys.common.domain.po.User;
import com.wosys.jobservice.client.UserClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@ResponseBody
public class ClientController {
    @Resource
    UserClient userClient;

    @GetMapping("/user-client/get/{uid}")
    public User GetUserByClient(@PathVariable("uid") int uid) {
        System.out.println("调用user client");
        return userClient.getUserById(uid);
    }
}
