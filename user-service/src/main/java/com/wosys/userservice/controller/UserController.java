// @author:樊川
// @email:945001786@qq.com
package com.wosys.userservice.controller;

import com.wosys.common.domain.dto.Result;
import com.wosys.common.domain.po.User;
import com.wosys.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping("/get/{uid}")
    public Result GetUser(@PathVariable("uid") int uid) {
        System.out.println("get user");
        User user = userService.getUserByUId(uid);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("user", user);
        return Result.success(res);
    }

    @GetMapping("/get/list")
    public Result GetUserList() {
        System.out.println("get user list");
        List<User> users = userService.getUserList();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("users", users);
        return Result.success(res, (long) users.size());
    }

    @GetMapping("/insert")
    public void InsertUser() {
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
