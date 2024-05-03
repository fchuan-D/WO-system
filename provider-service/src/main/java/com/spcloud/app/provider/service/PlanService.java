package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.PlanMapper;
import com.spcloud.app.provider.pojo.Plan;
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
public class PlanService {

    @Resource
    private PlanMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Plan> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Plan>> selectAll(Wrapper<Plan> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Plan>> selectAll() {
        QueryWrapper<Plan> wrapper = Wrappers.query();
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
        QueryWrapper<Plan> wrapper = mapToWrapper(map);
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
        QueryWrapper<Plan> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 根据map 条件筛选opera字段等于session.username的数据并分页
     */
    public R selectPagesOpera(Map<String, Object> map) {
        // 获取前端给到列表基础参数
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将map参数转换为mybatis-plus的QueryWrapper类
        QueryWrapper<Plan> wrapper = mapToWrapper(map);
        // 设置为当前角色
        wrapper.eq("opera", SessionFactory.getUsername());
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Plan> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Plan> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("num"))) {
            wrapper.like("num", map.get("num"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("well"))) {
            wrapper.like("well", map.get("well"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Plan> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Plan entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNum())) {
            return R.error("请填写作业编号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getWell())) {
            return R.error("请填写井号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getType())) {
            return R.error("请填写作业类型");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写图片");
        }

        if (StringUtil.isNullOrEmpty(entityData.getTimeon())) {
            return R.error("请填写开始时间");
        }

        if (StringUtil.isNullOrEmpty(entityData.getEndtime())) {
            return R.error("请填写结束时间");
        }

        if (StringUtil.isNullOrEmpty(entityData.getLocation())) {
            return R.error("请填写作业地点");
        }

        if (StringUtil.isNullOrEmpty(entityData.getRes())) {
            return R.error("请填写负责人");
        }

        if (StringUtil.isNullOrEmpty(entityData.getOperators())) {
            return R.error("请填写作业人员");
        }

        if (StringUtil.isNullOrEmpty(entityData.getMaterials())) {
            return R.error("请填写使用材料");
        }

        if (StringUtil.isNullOrEmpty(entityData.getMeasures())) {
            return R.error("请填写安全措施");
        }

        if (StringUtil.isNullOrEmpty(entityData.getCost())) {
            return R.error("请填写作业成本");
        }

        if (StringUtil.isNullOrEmpty(entityData.getProgramme())) {
            return R.error("请填写修井方案");
        }

        Info.handlerNullEntity(entityData);

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute("UPDATE plan SET duration=(TIMESTAMPDIFF(DAY , timeon,endtime)+1) WHERE id='" + charuid + "'");

            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业计划' , '新增' , '作业编号：" +
                            post.get("num") +
                            "<br>井号：" +
                            post.get("well") +
                            "<br>作业地点：" +
                            post.get("location") +
                            "<br>负责人：" +
                            post.get("res") +
                            "<br>作业人员：" +
                            post.get("operators") +
                            "<br>作业成本：" +
                            post.get("cost") +
                            "<br>安全措施：" +
                            post.get("measures") +
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

    public R<Object> update(Plan entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getNum())) {
            return R.error("请填写作业编号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getWell())) {
            return R.error("请填写井号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getType())) {
            return R.error("请填写作业类型");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写图片");
        }
        if (StringUtil.isNullOrEmpty(entityData.getTimeon())) {
            return R.error("请填写开始时间");
        }
        if (StringUtil.isNullOrEmpty(entityData.getEndtime())) {
            return R.error("请填写结束时间");
        }
        if (StringUtil.isNullOrEmpty(entityData.getLocation())) {
            return R.error("请填写作业地点");
        }
        if (StringUtil.isNullOrEmpty(entityData.getRes())) {
            return R.error("请填写负责人");
        }
        if (StringUtil.isNullOrEmpty(entityData.getOperators())) {
            return R.error("请填写作业人员");
        }
        if (StringUtil.isNullOrEmpty(entityData.getMaterials())) {
            return R.error("请填写使用材料");
        }
        if (StringUtil.isNullOrEmpty(entityData.getMeasures())) {
            return R.error("请填写安全措施");
        }
        if (StringUtil.isNullOrEmpty(entityData.getCost())) {
            return R.error("请填写作业成本");
        }
        if (StringUtil.isNullOrEmpty(entityData.getProgramme())) {
            return R.error("请填写修井方案");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute("UPDATE plan SET duration=(TIMESTAMPDIFF(DAY , timeon,endtime)+1) WHERE id='" + post.get("id") + "'");

        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业计划' , '更新' , '作业编号：" +
                        post.get("num") +
                        "<br>井号：" +
                        post.get("well") +
                        "<br>作业地点：" +
                        post.get("location") +
                        "<br>负责人：" +
                        post.get("res") +
                        "<br>作业人员：" +
                        post.get("operators") +
                        "<br>作业成本：" +
                        post.get("cost") +
                        "<br>安全措施：" +
                        post.get("measures") +
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
            Plan map = mapper.selectById(id);
            Map post = DB.name("plan").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业计划' , '删除' , '作业编号：" +
                            map.getNum() +
                            "<br>井号：" +
                            map.getWell() +
                            "<br>作业地点：" +
                            map.getLocation() +
                            "<br>负责人：" +
                            map.getRes() +
                            "<br>作业人员：" +
                            map.getOperators() +
                            "<br>作业成本：" +
                            map.getCost() +
                            "<br>安全措施：" +
                            map.getMeasures() +
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
