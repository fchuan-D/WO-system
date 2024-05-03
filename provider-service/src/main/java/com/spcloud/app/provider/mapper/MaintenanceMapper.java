package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Maintenance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("设备维护")
public interface MaintenanceMapper extends BaseMapper<Maintenance> {}
