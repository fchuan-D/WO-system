package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.Convert;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.TrackingMapper;
import com.spcloud.app.provider.pojo.Tracking;
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
public class TrackingService {

    @Resource
    private TrackingMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Tracking> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Tracking>> selectAll(Wrapper<Tracking> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Tracking>> selectAll() {
        QueryWrapper<Tracking> wrapper = Wrappers.query();
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
        QueryWrapper<Tracking> wrapper = mapToWrapper(map);
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
        QueryWrapper<Tracking> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Tracking> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Tracking> wrapper = Wrappers.query();

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
        if (!StringUtil.isNullOrEmpty(map.get("costofuse_start"))) {
            wrapper.ge("costofuse", map.get("costofuse_start"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("costofuse_end"))) {
            wrapper.le("costofuse", map.get("costofuse_end"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("cont"))) {
            wrapper.like("cont", map.get("cont"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Tracking> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Tracking entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getCostofuse())) {
            return R.error("请填写使用成本");
        }

        Info.handlerNullEntity(entityData);
        entityData.setAddtime(Info.getDateStr());

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('成本跟踪' , '新增' , '作业编号：" +
                            post.get("num") +
                            "<br>井号：" +
                            post.get("well") +
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

    public R<Object> update(Tracking entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getCostofuse())) {
            return R.error("请填写使用成本");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('成本跟踪' , '更新' , '作业编号：" +
                        post.get("num") +
                        "<br>井号：" +
                        post.get("well") +
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
            Tracking map = mapper.selectById(id);
            Map post = DB.name("tracking").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('成本跟踪' , '删除' , '作业编号：" +
                            map.getNum() +
                            "<br>井号：" +
                            map.getWell() +
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
