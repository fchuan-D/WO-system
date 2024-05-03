package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Auditing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("审核")
public interface AuditingMapper extends BaseMapper<Auditing> {}
