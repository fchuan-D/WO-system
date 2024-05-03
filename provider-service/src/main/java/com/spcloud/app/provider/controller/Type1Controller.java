package com.spcloud.app.provider.controller;

import cn.hutool.core.bean.BeanUtil;
import com.spcloud.app.provider.pojo.Type1;
import com.spcloud.app.provider.service.Type1Service;
import com.wosys.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = {"管理员控制器"})
@RestController
@RequestMapping("/api/type1")
public class Type1Controller {

    @Autowired
    public Type1Service type1Service;

    @ApiOperation(value = "获取全部作业类型", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public R<List<Type1>> selectAll() {
        return type1Service.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取管理员列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public R selectPages(@RequestBody Map<String, Object> req) {
        return type1Service.selectPages(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", dataType = "Integer")
    public R findById(@RequestParam Integer id) {
        return type1Service.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Type1.class, paramType = "body")
    public R update(@RequestBody Map data) {
        Type1 post = BeanUtil.mapToBean(data, Type1.class, true);
        return type1Service.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的点赞", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Type1.class, paramType = "body")
    public R insert(@RequestBody Map data) {
        Type1 post = BeanUtil.mapToBean(data, Type1.class, true);
        return type1Service.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", type = "json", dataTypeClass = List.class)
    public R delete(@RequestBody List<Integer> id) {
        return type1Service.delete(id);
    }
}
