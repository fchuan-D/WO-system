package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.Type2Mapper;
import com.spcloud.app.provider.pojo.Type2;
import com.wosys.base.utils.Info;
import com.wosys.base.utils.R;
import com.wosys.base.utils.SelectPage;
import com.wosys.base.utils.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Type2Service {

    @Resource
    private Type2Mapper mapper;

    /**
     * 根据Typename字段参数获取一行数据
     */
    public Type2 findByTypename(String username) {
        // 新建材料类型模块实体类Type2
        Type2 pojo = new Type2();
        // 设置参数
        pojo.setTypename(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Type2> queryWrapper = Wrappers.query(pojo);
        Type2 row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据Typename字段参数获取一行数据，并不包含某uid 参数得行
     */
    public Type2 findByTypename(String username, Integer uid) {
        // 新建材料类型模块实体类Type2
        Type2 pojo = new Type2();
        // 设置参数
        pojo.setTypename(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Type2> queryWrapper = Wrappers.query(pojo);
        // 设置参数 id != uid变量
        queryWrapper.ne("id", uid);
        // 根据queryWrapper 查询
        Type2 row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     * 根据id 获取一行数据
     */
    public R<Type2> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Type2>> selectAll(Wrapper<Type2> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Type2>> selectAll() {
        QueryWrapper<Type2> wrapper = Wrappers.query();
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
        QueryWrapper<Type2> wrapper = mapToWrapper(map);
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
        QueryWrapper<Type2> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Type2> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Type2> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("typename"))) {
            wrapper.like("typename", map.get("typename"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Type2> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Type2 entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getTypename())) {
            return R.error("请填写类型名称");
        }

        if (findByTypename(entityData.getTypename()) != null) {
            return R.error("类型名称已经存在");
        }

        Info.handlerNullEntity(entityData);

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料类型' , '新增' , '类型名称：" +
                            post.get("typename") +
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

    public R<Object> update(Type2 entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getTypename())) {
            return R.error("请填写类型名称");
        }
        if (findByTypename(entityData.getTypename(), entityData.getId()) != null) {
            return R.error("类型名称已经存在");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料类型' , '更新' , '类型名称：" +
                        post.get("typename") +
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
            Type2 map = mapper.selectById(id);
            Map post = DB.name("type2").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料类型' , '删除' , '类型名称：" +
                            map.getTypename() +
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
}
