package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Stuff;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("员工")
public interface StuffMapper extends BaseMapper<Stuff> {}
