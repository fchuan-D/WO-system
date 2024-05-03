package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Information;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("设备信息")
public interface InformationMapper extends BaseMapper<Information> {}
