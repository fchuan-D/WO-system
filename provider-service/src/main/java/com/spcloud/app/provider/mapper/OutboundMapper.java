package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Outbound;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("出库")
public interface OutboundMapper extends BaseMapper<Outbound> {}
