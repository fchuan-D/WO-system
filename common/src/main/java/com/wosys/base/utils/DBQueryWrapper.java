package com.wosys.base.utils;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.segments.NormalSegmentList;
import com.jntoo.db.QueryWrapperBase;
import com.jntoo.db.utils.StringUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DBQueryWrapper extends QueryWrapperBase<Map, DBQueryWrapper> {

    public DBQueryWrapper() {}

    public DBQueryWrapper(String name) {
        super(name);
    }

    public static DBQueryWrapper make(String name) {
        DBQueryWrapper wrapper = new DBQueryWrapper(name);
        return wrapper;
    }

    public DBQueryWrapper plus(QueryWrapper queryWrapper) {
        String sql = queryWrapper.getSqlSelect();
        MergeSegments expression = queryWrapper.getExpression();

        NormalSegmentList normal = expression.getNormal();
        Map<String, Object> valuePairs = queryWrapper.getParamNameValuePairs();

        String str = (String) normal.stream().map(ISqlSegment::getSqlSegment).collect(Collectors.joining(" "));
        Pattern pattern = Pattern.compile("\\#\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String keyText = matcher.group(1);
            String[] arr = keyText.split("\\.");
            Object val = valuePairs.get(arr[2]);
            matcher.appendReplacement(result, "'" + String.valueOf(val).replaceAll("'", "\\'") + "'");
        }
        str = result.toString().trim();
        if (!StringUtil.isNullOrEmpty(str)) {
            where(str);
        }
        return this;
    }
}
