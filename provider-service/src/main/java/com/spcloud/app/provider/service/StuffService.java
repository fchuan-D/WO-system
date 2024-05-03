package com.spcloud.app.provider.service;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.StuffMapper;
import com.spcloud.app.provider.pojo.Stuff;
import com.wosys.base.utils.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StuffService {

    @Resource
    private StuffMapper mapper;

    @Resource
    private PasswordEncoder pwdEncoder;

    /**
     * 根据Num字段参数获取一行数据
     */
    public Stuff findByNum(String username) {
        // 新建员工模块实体类Stuff
        Stuff pojo = new Stuff();
        // 设置参数
        pojo.setNum(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Stuff> queryWrapper = Wrappers.query(pojo);
        Stuff row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据Num字段参数获取一行数据，并不包含某uid 参数得行
     */
    public Stuff findByNum(String username, Integer uid) {
        // 新建员工模块实体类Stuff
        Stuff pojo = new Stuff();
        // 设置参数
        pojo.setNum(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Stuff> queryWrapper = Wrappers.query(pojo);
        // 设置参数 id != uid变量
        queryWrapper.ne("id", uid);
        // 根据queryWrapper 查询
        Stuff row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据id 获取一行数据
     */
    public R<Stuff> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Stuff>> selectAll(Wrapper<Stuff> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Stuff>> selectAll() {
        QueryWrapper<Stuff> wrapper = Wrappers.query();
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
        QueryWrapper<Stuff> wrapper = mapToWrapper(map);
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
        QueryWrapper<Stuff> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Stuff> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Stuff> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("num"))) {
            wrapper.like("num", map.get("num"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Stuff> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Stuff entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNum())) {
            return R.error("请填写工号");
        }

        if (findByNum(entityData.getNum()) != null) {
            return R.error("工号已经存在");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPassword())) {
            return R.error("请填写密码");
        }

        if (StringUtil.isNullOrEmpty(entityData.getName())) {
            return R.error("请填写姓名");
        }

        if (StringUtil.isNullOrEmpty(entityData.getSex())) {
            return R.error("请填写性别");
        }

        if (!StringUtil.isNullOrEmpty(entityData.getPhone())) {
            if (!(Validator.isMatchRegex(PatternPool.TEL, entityData.getPhone()) || Validator.isMobile(entityData.getPhone()))) {
                return R.error("请输入正确的手机");
            }
        }

        if (!StringUtil.isNullOrEmpty(entityData.getMail()) && !Validator.isEmail(entityData.getMail())) {
            return R.error("请输入正确的邮箱");
        }

        Info.handlerNullEntity(entityData);

        String pwd = pwdEncoder.encode(entityData.getPassword());
        entityData.setPassword(pwd);

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('员工' , '新增' , '工号：" +
                            post.get("num") +
                            "<br>姓名：" +
                            post.get("name") +
                            "' , '" +
                            SessionFactory.getCx() +
                            "' , '" +
                            SessionFactory.getUsername() +
                            "')"
            );

            return findById(entityData.getId());
        } else {
            return R.error("插入错误");
        }
    }

    public R<Object> update(Stuff entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNum())) {
            return R.error("请填写工号");
        }
        if (findByNum(entityData.getNum(), entityData.getId()) != null) {
            return R.error("工号已经存在");
        }
        if (StringUtil.isNullOrEmpty(entityData.getName())) {
            return R.error("请填写姓名");
        }
        if (StringUtil.isNullOrEmpty(entityData.getSex())) {
            return R.error("请填写性别");
        }
        if (!StringUtil.isNullOrEmpty(entityData.getPhone())) {
            if (!(Validator.isMatchRegex(PatternPool.TEL, entityData.getPhone()) || Validator.isMobile(entityData.getPhone()))) {
                return R.error("请输入正确的手机");
            }
        }
        if (!StringUtil.isNullOrEmpty(entityData.getMail()) && !Validator.isEmail(entityData.getMail())) {
            return R.error("请输入正确的邮箱");
        }

        String currentPwd = entityData.getPassword();
        if (!StringUtil.isNullOrEmpty(currentPwd)) {
            // 不等于空，设置密码
            String pwd = pwdEncoder.encode(currentPwd);
            entityData.setPassword(pwd);
        } else {
            Stuff old = mapper.selectById(entityData.getId());
            entityData.setPassword(old.getPassword());
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('员工' , '更新' , '工号：" +
                        post.get("num") +
                        "<br>姓名：" +
                        post.get("name") +
                        "' , '" +
                        SessionFactory.getCx() +
                        "' , '" +
                        SessionFactory.getUsername() +
                        "')"
        );

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
            Stuff map = mapper.selectById(id);
            Map post = DB.name("stuff").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('员工' , '删除' , '工号：" +
                            map.getNum() +
                            "<br>姓名：" +
                            map.getName() +
                            "' , '" +
                            SessionFactory.getCx() +
                            "' , '" +
                            SessionFactory.getUsername() +
                            "')"
            );

            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }

    public Execl getImportExecl() {
        Execl xls = new Execl(); // 生成导入类

        xls.addCol("num", "工号");
        xls.addCol("password", "密码");
        xls.addCol("name", "姓名");
        xls.addCol("sex", "性别");
        xls.addCol("phone", "手机");
        xls.addCol("mail", "邮箱");
        xls.addCol("content", "简介");
        xls.addCol("pos", "职位");
        xls.addCol("years", "工龄");

        return xls;
    }

    public int imports(String xlsFile) {
        // 插入一个空数据列，执行导出文件
        Execl xls = getImportExecl();
        List<Map> list = xls.imports(new File(xlsFile));
        int count = 0;
        for (Map map : list) {
            DB.name("stuff").insert(map);
            count++;
        }
        return count;
    }

    public void importFile(OutputStream outputStream) {
        Execl xls = getImportExecl();
        // 插入一个空数据列，执行导出文件
        List<Map> list = new ArrayList();
        xls.addData(list);
        xls.export(outputStream);
    }
}
