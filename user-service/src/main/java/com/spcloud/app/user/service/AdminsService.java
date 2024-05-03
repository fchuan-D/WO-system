package com.spcloud.app.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.wosys.base.utils.Session;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spcloud.app.user.mapper.AdminsMapper;
import com.spcloud.app.user.pojo.Admins;
import com.wosys.base.utils.R;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminsService {

    @Resource
    private AdminsMapper mapper;

    @Resource
    private PasswordEncoder pwdEncoder;

    /**
     * 将数据转换成Session对象
     */
    public Session toSession(Admins user) {
        Session session = new Session();
        session.setId(user.getId());
        session.setUsername(user.getUsername());
        session.setTable("admins");
        session.setRoles("管理员");
        session.setCx(session.getRoles());

        session.setObject(BeanUtil.beanToMap(user));
        return session;
    }

    public Admins login(String username, String pwd) {
        Admins row = findByUsername(username);

        // row为null的则是没有账号匹配成功
        if (row == null) {
            return null;
        }

        // 新建密码匹配类进行密码匹配，是否正确
        if (!pwdEncoder.matches(pwd, row.getPwd())) {
            return null;
        }
        return row;
    }

    public R<Object> editPassword(int id, String oldPassword, String newPassword) {
        String encodePassword = pwdEncoder.encode(newPassword);
        Admins admins = mapper.selectById(id);
        if (!pwdEncoder.matches(oldPassword, admins.getPwd())) {
            return R.error("原密码不正确，请重新输入");
        }
        admins.setPwd(encodePassword);
        mapper.updateById(admins);
        return R.ok();
    }

    /**
     * 根据Username字段参数获取一行数据
     */
    public Admins findByUsername(String str) {
        // 新建管理员模块实体类Admins
        Admins pojo = new Admins();
        // 设置参数
        pojo.setUsername(str);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Admins> queryWrapper = Wrappers.query(pojo);
        // 根据queryWrapper 查询
        Admins row = mapper.selectOne(queryWrapper);
        return row;
    }

    public R<Admins> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }
}
