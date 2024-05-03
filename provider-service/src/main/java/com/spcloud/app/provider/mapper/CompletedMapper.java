package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Completed;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("计划完成")
public interface CompletedMapper extends BaseMapper<Completed> {}
