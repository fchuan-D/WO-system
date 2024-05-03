package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Maintenance1;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("材料维护")
public interface Maintenance1Mapper extends BaseMapper<Maintenance1> {}
