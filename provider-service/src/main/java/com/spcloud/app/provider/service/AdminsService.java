package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.AdminsMapper;
import com.spcloud.app.provider.pojo.Admins;
import com.wosys.base.utils.Info;
import com.wosys.base.utils.R;
import com.wosys.base.utils.SelectPage;
import com.wosys.base.utils.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminsService {

    @Resource
    private AdminsMapper mapper;

    @Resource
    private PasswordEncoder pwdEncoder;

    /**
     * 根据Username字段参数获取一行数据
     */
    public Admins findByUsername(String username) {
        // 新建管理员模块实体类Admins
        Admins pojo = new Admins();
        // 设置参数
        pojo.setUsername(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Admins> queryWrapper = Wrappers.query(pojo);
        Admins row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据Username字段参数获取一行数据，并不包含某uid 参数得行
     */
    public Admins findByUsername(String username, Integer uid) {
        // 新建管理员模块实体类Admins
        Admins pojo = new Admins();
        // 设置参数
        pojo.setUsername(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Admins> queryWrapper = Wrappers.query(pojo);
        // 设置参数 id != uid变量
        queryWrapper.ne("id", uid);
        // 根据queryWrapper 查询
        Admins row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据id 获取一行数据
     */
    public R<Admins> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Admins>> selectAll(Wrapper<Admins> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Admins>> selectAll() {
        QueryWrapper<Admins> wrapper = Wrappers.query();
        wrapper.orderByDesc("id");
        return selectAll(wrapper);
    }

    /**
     * 根据map 条件筛选数据
     */
    public R selectAll(Map<String, Object> map) {
        // 获取筛选数据
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象，执行动态查询
        QueryWrapper<Admins> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        return selectAll(wrapper);
    }

    /**
     * 根据map 条件筛选数据并分页
     */
    public R selectPages(Map<String, Object> map) {
        // 获取筛选数据
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象，执行动态查询
        QueryWrapper<Admins> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Admins> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Admins> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("username"))) {
            wrapper.like("username", map.get("username"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Admins> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Admins entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getUsername())) {
            return R.error("请填写帐号");
        }

        if (findByUsername(entityData.getUsername()) != null) {
            return R.error("帐号已经存在");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPwd())) {
            return R.error("请填写密码");
        }

        Info.handlerNullEntity(entityData);

        String pwd = pwdEncoder.encode(entityData.getPwd());
        entityData.setPwd(pwd);

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            return findById(entityData.getId());
        } else {
            return R.error("插入错误");
        }
    }

    public R<Object> update(Admins entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getUsername())) {
            return R.error("请填写帐号");
        }
        if (findByUsername(entityData.getUsername(), entityData.getId()) != null) {
            return R.error("帐号已经存在");
        }

        String currentPwd = entityData.getPwd();
        if (!StringUtil.isNullOrEmpty(currentPwd)) {
            // 不等于空，设置密码
            String pwd = pwdEncoder.encode(currentPwd);
            entityData.setPwd(pwd);
        } else {
            Admins old = mapper.selectById(entityData.getId());
            entityData.setPwd(old.getPwd());
        }

        mapper.updateById(entityData);

        return R.success(mapper.selectById(entityData.getId()));
    }

    /**
     * 根据 id 删除
     */
    public R<Object> delete(List<Integer> ids) {
        try {
            for (Integer id : ids) {
                delete(id);
            }
            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }

    public R<Object> delete(Integer id) {
        try {
            mapper.deleteById(id);

            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }
}
