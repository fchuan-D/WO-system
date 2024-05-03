package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.HseMapper;
import com.spcloud.app.provider.pojo.Hse;
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
public class HseService {

    @Resource
    private HseMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Hse> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Hse>> selectAll(Wrapper<Hse> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Hse>> selectAll() {
        QueryWrapper<Hse> wrapper = Wrappers.query();
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
        QueryWrapper<Hse> wrapper = mapToWrapper(map);
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
        QueryWrapper<Hse> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 根据map 条件筛选operator字段等于session.username的数据并分页
     */
    public R selectPagesOperator(Map<String, Object> map) {
        // 获取前端给到列表基础参数
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将map参数转换为mybatis-plus的QueryWrapper类
        QueryWrapper<Hse> wrapper = mapToWrapper(map);
        // 设置为当前角色
        wrapper.eq("operator", SessionFactory.getUsername());
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Hse> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Hse> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("proname"))) {
            wrapper.like("proname", map.get("proname"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("resperson"))) {
            wrapper.like("resperson", map.get("resperson"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Hse> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Hse entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getProname())) {
            return R.error("请填写项目名");
        }

        if (StringUtil.isNullOrEmpty(entityData.getResperson())) {
            return R.error("请填写负责人");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPersonnel())) {
            return R.error("请填写执行人员");
        }

        if (StringUtil.isNullOrEmpty(entityData.getTimeon())) {
            return R.error("请填写开始日期");
        }

        if (StringUtil.isNullOrEmpty(entityData.getEndtime())) {
            return R.error("请填写结束日期");
        }

        if (StringUtil.isNullOrEmpty(entityData.getObjectives())) {
            return R.error("请填写管理目标");
        }

        if (StringUtil.isNullOrEmpty(entityData.getIdentification())) {
            return R.error("请填写风险识别");
        }

        if (StringUtil.isNullOrEmpty(entityData.getReport())) {
            return R.error("请填写环境报告");
        }

        if (StringUtil.isNullOrEmpty(entityData.getRecords())) {
            return R.error("请填写事故记录");
        }

        if (StringUtil.isNullOrEmpty(entityData.getMeasures())) {
            return R.error("请填写改进措施");
        }

        if (StringUtil.isNullOrEmpty(entityData.getCompliance())) {
            return R.error("请填写法律法规遵守情况");
        }

        Info.handlerNullEntity(entityData);
        entityData.setAddtime(Info.getDateStr());

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('HSE' , '新增' , '项目名：" +
                            post.get("proname") +
                            "<br>负责人：" +
                            post.get("resperson") +
                            "<br>执行人员：" +
                            post.get("personnel") +
                            "<br>管理目标：" +
                            post.get("objectives") +
                            "<br>风险识别：" +
                            post.get("identification") +
                            "<br>事故记录：" +
                            post.get("records") +
                            "<br>改进措施：" +
                            post.get("measures") +
                            "<br>法律法规遵守情况：" +
                            post.get("compliance") +
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

    public R<Object> update(Hse entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getProname())) {
            return R.error("请填写项目名");
        }
        if (StringUtil.isNullOrEmpty(entityData.getResperson())) {
            return R.error("请填写负责人");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPersonnel())) {
            return R.error("请填写执行人员");
        }
        if (StringUtil.isNullOrEmpty(entityData.getTimeon())) {
            return R.error("请填写开始日期");
        }
        if (StringUtil.isNullOrEmpty(entityData.getEndtime())) {
            return R.error("请填写结束日期");
        }
        if (StringUtil.isNullOrEmpty(entityData.getObjectives())) {
            return R.error("请填写管理目标");
        }
        if (StringUtil.isNullOrEmpty(entityData.getIdentification())) {
            return R.error("请填写风险识别");
        }
        if (StringUtil.isNullOrEmpty(entityData.getReport())) {
            return R.error("请填写环境报告");
        }
        if (StringUtil.isNullOrEmpty(entityData.getRecords())) {
            return R.error("请填写事故记录");
        }
        if (StringUtil.isNullOrEmpty(entityData.getMeasures())) {
            return R.error("请填写改进措施");
        }
        if (StringUtil.isNullOrEmpty(entityData.getCompliance())) {
            return R.error("请填写法律法规遵守情况");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('HSE' , '更新' , '项目名：" +
                        post.get("proname") +
                        "<br>负责人：" +
                        post.get("resperson") +
                        "<br>执行人员：" +
                        post.get("personnel") +
                        "<br>管理目标：" +
                        post.get("objectives") +
                        "<br>风险识别：" +
                        post.get("identification") +
                        "<br>事故记录：" +
                        post.get("records") +
                        "<br>改进措施：" +
                        post.get("measures") +
                        "<br>法律法规遵守情况：" +
                        post.get("compliance") +
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
            Hse map = mapper.selectById(id);
            Map post = DB.name("hse").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('HSE' , '删除' , '项目名：" +
                            map.getProname() +
                            "<br>负责人：" +
                            map.getResperson() +
                            "<br>执行人员：" +
                            map.getPersonnel() +
                            "<br>管理目标：" +
                            map.getObjectives() +
                            "<br>风险识别：" +
                            map.getIdentification() +
                            "<br>事故记录：" +
                            map.getRecords() +
                            "<br>改进措施：" +
                            map.getMeasures() +
                            "<br>法律法规遵守情况：" +
                            map.getCompliance() +
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
