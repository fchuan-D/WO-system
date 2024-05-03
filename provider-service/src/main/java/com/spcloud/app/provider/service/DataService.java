package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.DataMapper;
import com.spcloud.app.provider.pojo.Data;
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
public class DataService {

    @Resource
    private DataMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Data> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Data>> selectAll(Wrapper<Data> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Data>> selectAll() {
        QueryWrapper<Data> wrapper = Wrappers.query();
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
        QueryWrapper<Data> wrapper = mapToWrapper(map);
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
        QueryWrapper<Data> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Data> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Data> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("number1"))) {
            wrapper.like("number1", map.get("number1"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("wellnumber"))) {
            wrapper.like("wellnumber", map.get("wellnumber"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Data> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Data entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNumber1())) {
            return R.error("请填写编号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getWellnumber())) {
            return R.error("请填写井号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写作业图片");
        }

        if (StringUtil.isNullOrEmpty(entityData.getDepth())) {
            return R.error("请填写井深");
        }

        if (StringUtil.isNullOrEmpty(entityData.getDiameter())) {
            return R.error("请填写井径");
        }

        if (StringUtil.isNullOrEmpty(entityData.getMudproperties())) {
            return R.error("请填写泥浆性能");
        }

        if (StringUtil.isNullOrEmpty(entityData.getBittype())) {
            return R.error("请填写钻头类型");
        }

        if (StringUtil.isNullOrEmpty(entityData.getSpeed())) {
            return R.error("请填写钻速");
        }

        if (StringUtil.isNullOrEmpty(entityData.getDetails())) {
            return R.error("请填写详情");
        }

        Info.handlerNullEntity(entityData);
        entityData.setAddtime(Info.getDateStr());

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业数据' , '新增' , '编号：" +
                            post.get("number1") +
                            "<br>井号：" +
                            post.get("wellnumber") +
                            "<br>井深：" +
                            post.get("depth") +
                            "<br>井径：" +
                            post.get("diameter") +
                            "<br>泥浆性能：" +
                            post.get("mudproperties") +
                            "<br>钻头类型：" +
                            post.get("bittype") +
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

    public R<Object> update(Data entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNumber1())) {
            return R.error("请填写编号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getWellnumber())) {
            return R.error("请填写井号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写作业图片");
        }
        if (StringUtil.isNullOrEmpty(entityData.getDepth())) {
            return R.error("请填写井深");
        }
        if (StringUtil.isNullOrEmpty(entityData.getDiameter())) {
            return R.error("请填写井径");
        }
        if (StringUtil.isNullOrEmpty(entityData.getMudproperties())) {
            return R.error("请填写泥浆性能");
        }
        if (StringUtil.isNullOrEmpty(entityData.getBittype())) {
            return R.error("请填写钻头类型");
        }
        if (StringUtil.isNullOrEmpty(entityData.getSpeed())) {
            return R.error("请填写钻速");
        }
        if (StringUtil.isNullOrEmpty(entityData.getDetails())) {
            return R.error("请填写详情");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业数据' , '更新' , '编号：" +
                        post.get("number1") +
                        "<br>井号：" +
                        post.get("wellnumber") +
                        "<br>井深：" +
                        post.get("depth") +
                        "<br>井径：" +
                        post.get("diameter") +
                        "<br>泥浆性能：" +
                        post.get("mudproperties") +
                        "<br>钻头类型：" +
                        post.get("bittype") +
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
            Data map = mapper.selectById(id);
            Map post = DB.name("data").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业数据' , '删除' , '编号：" +
                            map.getNumber1() +
                            "<br>井号：" +
                            map.getWellnumber() +
                            "<br>井深：" +
                            map.getDepth() +
                            "<br>井径：" +
                            map.getDiameter() +
                            "<br>泥浆性能：" +
                            map.getMudproperties() +
                            "<br>钻头类型：" +
                            map.getBittype() +
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
