package com.spcloud.app.user.controller;

import cn.hutool.core.util.StrUtil;
import com.spcloud.app.user.pojo.LoginPojo;
import com.spcloud.app.user.pojo.PasswordPojo;
import com.spcloud.app.user.service.UserService;
import com.wosys.base.utils.JwtTokenUtils;
import com.wosys.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api("用户登录")
public class UserController {

    @Resource
    private JwtTokenUtils jwtTokenUtils;

    @Resource
    private UserService userService;

    @GetMapping("/get")
    public R GetUser() {
        System.out.println("dkz");
        return R.success("dkz");
    }

    @ApiOperation(value = "账号密码登录", httpMethod = "POST")
    @PostMapping("/login")
    public R<Object> login(@RequestBody LoginPojo loginPojo) {
        if (loginPojo.getA() != null) {
            if (StrUtil.isEmpty(loginPojo.getPagerandom())) {
                return R.error("请输入验证码");
            }
            String token = jwtTokenUtils.getUserIdFromToken(loginPojo.getCaptchToken());
            if (!token.equals(loginPojo.getPagerandom())) {
                return R.error("验证码错误请重新输入");
            }
        }
        return userService.login(loginPojo.getUsername(), loginPojo.getPwd(), loginPojo.getCx());
    }

    @ApiOperation(value = "退出登录操作", httpMethod = "GET")
    @GetMapping("/logout")
    public R<Object> logout() {
        return R.ok();
    }

    @ApiOperation(value = "修改密码", httpMethod = "POST")
    @PostMapping("/editPassword")
    public R<Object> editPassword(@RequestBody PasswordPojo passwordPojo) {
        return userService.editPassword(passwordPojo);
    }

    @ApiOperation(value = "使用token获取用户信息，并更新token", httpMethod = "GET")
    @GetMapping("/tokenLogin")
    public R<Object> tokenLogin(@RequestParam("token") String token) {
        return userService.tokenLogin(token);
    }
}
