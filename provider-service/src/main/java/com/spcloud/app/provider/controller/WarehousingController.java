package com.spcloud.app.provider.controller;

import cn.hutool.core.bean.BeanUtil;
import com.spcloud.app.provider.pojo.Warehousing;
import com.spcloud.app.provider.service.WarehousingService;
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
@RequestMapping("/api/warehousing")
public class WarehousingController {

    @Autowired
    public WarehousingService warehousingService;

    @ApiOperation(value = "获取全部入库", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public R<List<Warehousing>> selectAll() {
        return warehousingService.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取管理员列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public R selectPages(@RequestBody Map<String, Object> req) {
        return warehousingService.selectPages(req);
    }

    @ApiOperation(value = "根据条件筛选获取经手人字段值为当前用户列表并分页", httpMethod = "POST")
    @RequestMapping("/selectHandler")
    public R selectHandler(@RequestBody Map<String, Object> req) {
        return warehousingService.selectPagesHandler(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", dataType = "Integer")
    public R findById(@RequestParam Integer id) {
        return warehousingService.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Warehousing.class, paramType = "body")
    public R update(@RequestBody Map data) {
        Warehousing post = BeanUtil.mapToBean(data, Warehousing.class, true);
        return warehousingService.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的点赞", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Warehousing.class, paramType = "body")
    public R insert(@RequestBody Map data) {
        Warehousing post = BeanUtil.mapToBean(data, Warehousing.class, true);
        return warehousingService.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", type = "json", dataTypeClass = List.class)
    public R delete(@RequestBody List<Integer> id) {
        return warehousingService.delete(id);
    }
}
