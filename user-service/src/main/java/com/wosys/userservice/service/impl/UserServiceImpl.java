// @author:樊川
// @email:945001786@qq.com
package com.wosys.userservice.service.impl;

import com.wosys.common.domain.po.User;
import com.wosys.userservice.mapper.UserMapper;
import com.wosys.userservice.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserByUId(int uid) {
        User user = userMapper.selectById(uid);
        if (user.getUid() < 1) {
            System.out.println(user.getUid() + "not found");
        }
        return user;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectList(null);
    }
}
