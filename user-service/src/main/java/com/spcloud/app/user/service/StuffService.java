package com.spcloud.app.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spcloud.app.user.mapper.StuffMapper;
import com.spcloud.app.user.pojo.Stuff;
import com.wosys.base.utils.R;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wosys.base.utils.Session;

import javax.annotation.Resource;

@Service
public class StuffService {

    @Resource
    private StuffMapper mapper;

    @Resource
    private PasswordEncoder pwdEncoder;

    /**
     * 将数据转换成Session对象
     */
    public Session toSession(Stuff user) {
        Session session = new Session();
        session.setId(user.getId());
        session.setUsername(user.getNum());
        session.setTable("stuff");
        session.setRoles("员工");
        session.setCx(session.getRoles());

        session.setObject(BeanUtil.beanToMap(user));
        return session;
    }

    public Stuff login(String username, String pwd) {
        Stuff row = findByNum(username);

        // row为null的则是没有账号匹配成功
        if (row == null) {
            return null;
        }

        // 新建密码匹配类进行密码匹配，是否正确
        if (!pwdEncoder.matches(pwd, row.getPassword())) {
            return null;
        }
        return row;
    }

    public R<Object> editPassword(int id, String oldPassword, String newPassword) {
        String encodePassword = pwdEncoder.encode(newPassword);
        Stuff admins = mapper.selectById(id);
        if (!pwdEncoder.matches(oldPassword, admins.getPassword())) {
            return R.error("原密码不正确，请重新输入");
        }
        admins.setPassword(encodePassword);
        mapper.updateById(admins);
        return R.ok();
    }

    /**
     * 根据Num字段参数获取一行数据
     */
    public Stuff findByNum(String str) {
        // 新建管理员模块实体类Stuff
        Stuff pojo = new Stuff();
        // 设置参数
        pojo.setNum(str);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Stuff> queryWrapper = Wrappers.query(pojo);
        // 根据queryWrapper 查询
        Stuff row = mapper.selectOne(queryWrapper);
        return row;
    }

    public R<Stuff> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }
}
