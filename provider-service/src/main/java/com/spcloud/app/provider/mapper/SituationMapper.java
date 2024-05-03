package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Situation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("作业情况")
public interface SituationMapper extends BaseMapper<Situation> {}
