package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("设备类型")
public interface TypeMapper extends BaseMapper<Type> {}
