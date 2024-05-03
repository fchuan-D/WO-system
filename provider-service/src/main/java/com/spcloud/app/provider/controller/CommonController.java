package com.spcloud.app.provider.controller;

import com.spcloud.app.provider.service.CommonService;
import com.wosys.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = {"公共查询控制器"})
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Resource
    private CommonService commonService;

    @PostMapping("/query")
    @ApiOperation(value = "根据参数查询数据库信息", httpMethod = "POST")
    public R<Object> query(@RequestBody Map map) {
        return commonService.query(map);
    }

    @RequestMapping("/select")
    @ApiOperation(value = "根据参数获取数据库信息", httpMethod = "POST")
    public R select(@RequestBody Map<String, Object> data) {
        return commonService.select(data);
    }
}
