package com.spcloud.app.provider.controller;

import cn.hutool.core.bean.BeanUtil;
import com.spcloud.app.provider.pojo.Stuff;
import com.spcloud.app.provider.service.StuffService;
import com.wosys.base.config.Configure;
import com.wosys.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = {"管理员控制器"})
@RestController
@RequestMapping("/api/stuff")
public class StuffController {

    @Autowired
    public StuffService stuffService;

    @ApiOperation(value = "获取全部员工", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public R<List<Stuff>> selectAll() {
        return stuffService.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取管理员列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public R selectPages(@RequestBody Map<String, Object> req) {
        return stuffService.selectPages(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", dataType = "Integer")
    public R findById(@RequestParam Integer id) {
        return stuffService.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Stuff.class, paramType = "body")
    public R update(@RequestBody Map data) {
        Stuff post = BeanUtil.mapToBean(data, Stuff.class, true);
        return stuffService.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的点赞", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Stuff.class, paramType = "body")
    public R insert(@RequestBody Map data) {
        Stuff post = BeanUtil.mapToBean(data, Stuff.class, true);
        return stuffService.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "点赞对应的id", type = "json", dataTypeClass = List.class)
    public R delete(@RequestBody List<Integer> id) {
        return stuffService.delete(id);
    }

    @ApiOperation(value = "根据文件导入到数据库中", httpMethod = "GET")
    @RequestMapping("/imports")
    public R<Object> imports(@RequestParam("file") String file) {
        String path = Configure.ROOT_DIR + (file);
        int count = stuffService.imports(path);
        return R.success(count);
    }

    @ApiOperation(value = "生成导入模板", httpMethod = "GET")
    @RequestMapping("/importTemplate")
    public void importTemplate(@Autowired HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=stuffTemplate.xls");
            stuffService.importFile(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
