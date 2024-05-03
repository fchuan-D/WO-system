package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Type2;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("材料类型")
public interface Type2Mapper extends BaseMapper<Type2> {}
