package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Updates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("计划更新")
public interface UpdatesMapper extends BaseMapper<Updates> {}
