// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo.service.impl;

import com.wosys.demo.domain.po.User;
import com.wosys.demo.mapper.UserMapper;
import com.wosys.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserByUId(int uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

}
