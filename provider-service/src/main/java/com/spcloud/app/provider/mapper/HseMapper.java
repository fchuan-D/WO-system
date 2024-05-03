package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Hse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("HSE")
public interface HseMapper extends BaseMapper<Hse> {}
