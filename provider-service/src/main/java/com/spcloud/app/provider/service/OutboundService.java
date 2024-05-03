package com.spcloud.app.provider.service;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.Convert;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.OutboundMapper;
import com.spcloud.app.provider.pojo.Outbound;
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
public class OutboundService {

    @Resource
    private OutboundMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Outbound> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Outbound>> selectAll(Wrapper<Outbound> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Outbound>> selectAll() {
        QueryWrapper<Outbound> wrapper = Wrappers.query();
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
        QueryWrapper<Outbound> wrapper = mapToWrapper(map);
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
        QueryWrapper<Outbound> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 根据map 条件筛选handler字段等于session.username的数据并分页
     */
    public R selectPagesHandler(Map<String, Object> map) {
        // 获取前端给到列表基础参数
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将map参数转换为mybatis-plus的QueryWrapper类
        QueryWrapper<Outbound> wrapper = mapToWrapper(map);
        // 设置为当前角色
        wrapper.eq("handler", SessionFactory.getUsername());
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Outbound> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Outbound> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        // 判断URL 参数materialid是否大于0
        if (!StringUtil.isNullOrEmpty(map.get("materialid")) && Convert.toInt(map.get("materialid")) > 0) {
            // 大于0 则写入条件
            wrapper.eq("materialid", map.get("materialid"));
        }

        if (!StringUtil.isNullOrEmpty(map.get("materialnu"))) {
            wrapper.like("materialnu", map.get("materialnu"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("materialna"))) {
            wrapper.like("materialna", map.get("materialna"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("quantity_start"))) {
            wrapper.ge("quantity", map.get("quantity_start"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("quantity_end"))) {
            wrapper.le("quantity", map.get("quantity_end"));
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

    public R selectPages(QueryWrapper<Outbound> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Outbound entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getQuantity())) {
            return R.error("请填写出库数量");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPerson())) {
            return R.error("请填写负责人");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPhone())) {
            return R.error("请填写联系方式");
        }

        if (!StringUtil.isNullOrEmpty(entityData.getPhone())) {
            if (!(Validator.isMatchRegex(PatternPool.TEL, entityData.getPhone()) || Validator.isMobile(entityData.getPhone()))) {
                return R.error("请输入正确的联系方式");
            }
        }

        Info.handlerNullEntity(entityData);
        entityData.setAddtime(Info.getDateStr());

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('出库' , '新增' , '材料编号：" +
                            post.get("materialnu") +
                            "<br>材料名称：" +
                            post.get("materialna") +
                            "<br>负责人：" +
                            post.get("person") +
                            "<br>联系方式：" +
                            post.get("phone") +
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

    public R<Object> update(Outbound entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getQuantity())) {
            return R.error("请填写出库数量");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPerson())) {
            return R.error("请填写负责人");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPhone())) {
            return R.error("请填写联系方式");
        }
        if (!StringUtil.isNullOrEmpty(entityData.getPhone())) {
            if (!(Validator.isMatchRegex(PatternPool.TEL, entityData.getPhone()) || Validator.isMobile(entityData.getPhone()))) {
                return R.error("请输入正确的联系方式");
            }
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('出库' , '更新' , '材料编号：" +
                        post.get("materialnu") +
                        "<br>材料名称：" +
                        post.get("materialna") +
                        "<br>负责人：" +
                        post.get("person") +
                        "<br>联系方式：" +
                        post.get("phone") +
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
            Outbound map = mapper.selectById(id);
            Map post = DB.name("outbound").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('出库' , '删除' , '材料编号：" +
                            map.getMaterialnu() +
                            "<br>材料名称：" +
                            map.getMaterialna() +
                            "<br>负责人：" +
                            map.getPerson() +
                            "<br>联系方式：" +
                            map.getPhone() +
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
