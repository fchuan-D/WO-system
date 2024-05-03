package com.spcloud.app.provider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.provider.mapper.MaterialMapper;
import com.spcloud.app.provider.pojo.Material;
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
public class MaterialService {

    @Resource
    private MaterialMapper mapper;

    /**
     * 根据id 获取一行数据
     */
    public R<Material> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     * 根据Wrapper 对象进行数据筛选
     */
    public R<List<Material>> selectAll(Wrapper<Material> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     * 直接筛选所有数据
     */
    public R<List<Material>> selectAll() {
        QueryWrapper<Material> wrapper = Wrappers.query();
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
        QueryWrapper<Material> wrapper = mapToWrapper(map);
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
        QueryWrapper<Material> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     * 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Material> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Material> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("materialnu"))) {
            wrapper.like("materialnu", map.get("materialnu"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("materialna"))) {
            wrapper.like("materialna", map.get("materialna"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("specification"))) {
            wrapper.like("specification", map.get("specification"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Material> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    public R insert(Material entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getMaterialnu())) {
            return R.error("请填写材料编号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getMaterialna())) {
            return R.error("请填写材料名称");
        }

        if (StringUtil.isNullOrEmpty(entityData.getType())) {
            return R.error("请填写材料类型");
        }

        if (StringUtil.isNullOrEmpty(entityData.getSpecification())) {
            return R.error("请填写规格型号");
        }

        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写材料图片");
        }

        if (StringUtil.isNullOrEmpty(entityData.getFacturer())) {
            return R.error("请填写生产厂家");
        }

        if (StringUtil.isNullOrEmpty(entityData.getSupplier())) {
            return R.error("请填写供应商");
        }

        if (StringUtil.isNullOrEmpty(entityData.getUnit())) {
            return R.error("请填写单位");
        }

        if (StringUtil.isNullOrEmpty(entityData.getLevel())) {
            return R.error("请填写级别");
        }

        if (StringUtil.isNullOrEmpty(entityData.getType1())) {
            return R.error("请填写接头类型");
        }

        if (StringUtil.isNullOrEmpty(entityData.getQuantity())) {
            return R.error("请填写数量");
        }

        Info.handlerNullEntity(entityData);

        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料信息' , '新增' , '材料编号：" +
                            post.get("materialnu") +
                            "<br>材料名称：" +
                            post.get("materialna") +
                            "<br>规格型号：" +
                            post.get("specification") +
                            "<br>生产厂家：" +
                            post.get("facturer") +
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

    public R<Object> update(Material entityData, Map post) {
        if (StringUtil.isNullOrEmpty(entityData.getMaterialnu())) {
            return R.error("请填写材料编号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getMaterialna())) {
            return R.error("请填写材料名称");
        }
        if (StringUtil.isNullOrEmpty(entityData.getType())) {
            return R.error("请填写材料类型");
        }
        if (StringUtil.isNullOrEmpty(entityData.getSpecification())) {
            return R.error("请填写规格型号");
        }
        if (StringUtil.isNullOrEmpty(entityData.getPic())) {
            return R.error("请填写材料图片");
        }
        if (StringUtil.isNullOrEmpty(entityData.getFacturer())) {
            return R.error("请填写生产厂家");
        }
        if (StringUtil.isNullOrEmpty(entityData.getSupplier())) {
            return R.error("请填写供应商");
        }
        if (StringUtil.isNullOrEmpty(entityData.getUnit())) {
            return R.error("请填写单位");
        }
        if (StringUtil.isNullOrEmpty(entityData.getLevel())) {
            return R.error("请填写级别");
        }
        if (StringUtil.isNullOrEmpty(entityData.getType1())) {
            return R.error("请填写接头类型");
        }
        if (StringUtil.isNullOrEmpty(entityData.getQuantity())) {
            return R.error("请填写数量");
        }

        mapper.updateById(entityData);

        Integer charuid = entityData.getId();
        DB.execute(
                "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料信息' , '更新' , '材料编号：" +
                        post.get("materialnu") +
                        "<br>材料名称：" +
                        post.get("materialna") +
                        "<br>规格型号：" +
                        post.get("specification") +
                        "<br>生产厂家：" +
                        post.get("facturer") +
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
            Material map = mapper.selectById(id);
            Map post = DB.name("material").find(id);

            mapper.deleteById(id);
            DB.execute(
                    "INSERT INTO logs(module,operationtype,operationcontent,cx,username) values('材料信息' , '删除' , '材料编号：" +
                            map.getMaterialnu() +
                            "<br>材料名称：" +
                            map.getMaterialna() +
                            "<br>规格型号：" +
                            map.getSpecification() +
                            "<br>生产厂家：" +
                            map.getFacturer() +
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
