// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo.controller;

import com.wosys.demo.domain.po.User;
import com.wosys.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@ResponseBody
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/get/{uid}")
    public User GetUser(@PathVariable("uid") int uid) {
        User user = userService.getUserByUId(uid);
        if (user.getUid() < 1) {
            System.out.println(user.getUid() + "not found");
        }
        return user;
    }

    @GetMapping("/insert")
    public void InsertUser() {
        System.out.println("=======》路由成功");

        User user = new User();
        user.setUname("刘亦菲");
        user.setUage("35");
        user.setUaddr("美国");
        int i = userService.insertUser(user);
        if (i == 1) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }
}
