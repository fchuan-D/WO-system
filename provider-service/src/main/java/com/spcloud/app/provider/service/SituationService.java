package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.Convert;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.SituationMapper;
import com.spcloud.app.provider.pojo.Situation;
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
public class SituationService {

    @Resource
    private SituationMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Situation> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Situation>> selectAll(Wrapper<Situation> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Situation>> selectAll() {
        QueryWrapper<Situation> wrapper = Wrappers.query();
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
        QueryWrapper<Situation> wrapper = mapToWrapper(map);
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
        QueryWrapper<Situation> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 根据map 条件筛选addpeople字段等于session.username的数据并分页
     */
    public R selectPagesAddpeople(Map<String, Object> map) {
        // 获取前端给到列表基础参数
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将map参数转换为mybatis-plus的QueryWrapper类
        QueryWrapper<Situation> wrapper = mapToWrapper(map);
        // 设置为当前角色
        wrapper.eq("addpeople", SessionFactory.getUsername());
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Situation> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Situation> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        // 判断URL 参数planid是否大于0
        if (!StringUtil.isNullOrEmpty(map.get("planid")) && Convert.toInt(map.get("planid")) > 0) {
            // 大于0 则写入条件
            wrapper.eq("planid", map.get("planid"));
        }

        if (!StringUtil.isNullOrEmpty(map.get("num"))) {
            wrapper.like("num", map.get("num"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("well"))) {
            wrapper.like("well", map.get("well"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("pressure"))) {
            wrapper.like("pressure", map.get("pressure"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("speed"))) {
            wrapper.like("speed", map.get("speed"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Situation> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Situation entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getPressure())) {
            return R.error("请填写井口压力");
        }

        if (StringUtil.isNullOrEmpty(entityData.getSpeed())) {
            return R.error("请填写钻进速度");
        }

        if (StringUtil.isNullOrEmpty(entityData.getFlowrate())) {
            return R.error("请填写钻井液流量");
        }

        if (StringUtil.isNullOrEmpty(entityData.getIndicators())) {
            return R.error("请填写其他指标");
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
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业情况' , '新增' , '作业编号：" +
                            post.get("num") +
                            "<br>井号：" +
                            post.get("well") +
                            "<br>井口压力：" +
                            post.get("pressure") +
                            "<br>钻进速度：" +
                            post.get("speed") +
                            "<br>钻井液流量：" +
                            post.get("flowrate") +
                            "<br>其他指标：" +
                            post.get("indicators") +
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

    public R<Object> update(Situation entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getPressure())) {
            return R.error("请填写井口压力");
        }
        if (StringUtil.isNullOrEmpty(entityData.getSpeed())) {
            return R.error("请填写钻进速度");
        }
        if (StringUtil.isNullOrEmpty(entityData.getFlowrate())) {
            return R.error("请填写钻井液流量");
        }
        if (StringUtil.isNullOrEmpty(entityData.getIndicators())) {
            return R.error("请填写其他指标");
        }
        if (StringUtil.isNullOrEmpty(entityData.getDetails())) {
            return R.error("请填写详情");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业情况' , '更新' , '作业编号：" +
                        post.get("num") +
                        "<br>井号：" +
                        post.get("well") +
                        "<br>井口压力：" +
                        post.get("pressure") +
                        "<br>钻进速度：" +
                        post.get("speed") +
                        "<br>钻井液流量：" +
                        post.get("flowrate") +
                        "<br>其他指标：" +
                        post.get("indicators") +
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
            Situation map = mapper.selectById(id);
            Map post = DB.name("situation").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('作业情况' , '删除' , '作业编号：" +
                            map.getNum() +
                            "<br>井号：" +
                            map.getWell() +
                            "<br>井口压力：" +
                            map.getPressure() +
                            "<br>钻进速度：" +
                            map.getSpeed() +
                            "<br>钻井液流量：" +
                            map.getFlowrate() +
                            "<br>其他指标：" +
                            map.getIndicators() +
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
