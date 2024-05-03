package com.spcloud.app.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.jntoo.db.DB;
import com.jntoo.db.QueryMap;
import com.jntoo.db.model.Options;
import com.wosys.base.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公共查询业务逻辑处理
 */
@Service("CommonService")
public class CommonService {

    /**
     * 根据提供的参数查询数据库信息
     *
     * @param map
     * @return
     */
    public R<Object> query(Map map) {
        if (!map.containsKey("name")) {
            return R.error("找不到相关名称");
        }
        if (!map.containsKey("options") && !(map.get("options") instanceof Map)) {
            return R.error("找不到相关配置");
        }
        if (!map.containsKey("func")) {
            return R.error("找不到引用");
        }

        JSONObject object = new JSONObject();
        object.putAll((Map) map.get("options"));

        QueryMap queryWrapper = DB.name(map.get("name").toString());
        queryWrapper.setOptions(object.toJavaObject(Options.class));
        String func = map.get("func").toString().toLowerCase();
        Object result = null;
        List args = (List) map.get("args");
        try {
            if (func.equals("select")) {
                result = queryWrapper.select();
            } else if (func.equals("find")) {
                if (args == null) {
                    result = queryWrapper.find();
                } else {
                    result = queryWrapper.find(args.get(0));
                }
            } else if (func.equals("count")) {
                if (args == null) {
                    result = queryWrapper.count();
                } else {
                    result = queryWrapper.count(String.valueOf(args.get(0)));
                }
            } else if (func.equals("avg")) {
                result = queryWrapper.avg(String.valueOf(args.get(0)));
            } else if (func.equals("sum")) {
                result = queryWrapper.sum(String.valueOf(args.get(0)));
            } else if (func.equals("max")) {
                result = queryWrapper.max(String.valueOf(args.get(0)));
            } else if (func.equals("min")) {
                result = queryWrapper.min(String.valueOf(args.get(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.success(result);
    }

    /**
     * 根据参数执行数据库语句
     *
     * @param data
     * @return
     */
    public R select(@RequestBody Map<String, Object> data) {
        String sql = String.valueOf(data.get("sql"));
        String type = String.valueOf(data.get("type"));
        List binds = null;
        if (data.containsKey("binds") && data.get("binds") instanceof List) {
            binds = (List) data.get("binds");
        } else {
            binds = new ArrayList();
        }

        Object[] datas = binds.toArray();
        if ("execute".equals(type)) {
            return R.success(DB.execute(sql, datas));
        } else if ("select".equals(type)) {
            return R.success(DB.select(sql, datas));
        } else {
            return R.success(DB.find(sql, datas));
        }
    }
}
