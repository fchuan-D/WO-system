package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Logs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("操作日志")
public interface LogsMapper extends BaseMapper<Logs> {}
