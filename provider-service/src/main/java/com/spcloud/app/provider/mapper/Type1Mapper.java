package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Type1;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("作业类型")
public interface Type1Mapper extends BaseMapper<Type1> {}
