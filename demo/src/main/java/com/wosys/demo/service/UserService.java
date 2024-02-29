// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo.service;


import com.wosys.demo.domain.po.User;

public interface UserService {
    User getUserByUId(int uid);

    int insertUser(User user);
}
