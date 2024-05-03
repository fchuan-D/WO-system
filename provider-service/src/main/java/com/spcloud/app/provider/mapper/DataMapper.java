package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("作业数据")
public interface DataMapper extends BaseMapper<Data> {}
