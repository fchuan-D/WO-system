package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Technical;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("技术材料")
public interface TechnicalMapper extends BaseMapper<Technical> {}
