package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Plan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("作业计划")
public interface PlanMapper extends BaseMapper<Plan> {}
