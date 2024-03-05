// @author:樊川
// @email:945001786@qq.com
package com.wosys.userservice.service;


import com.wosys.common.domain.po.User;

public interface UserService {
    User getUserByUId(int uid);

    int insertUser(User user);
}
