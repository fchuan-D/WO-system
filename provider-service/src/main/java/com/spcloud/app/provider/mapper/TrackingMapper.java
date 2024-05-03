package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Tracking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("成本跟踪")
public interface TrackingMapper extends BaseMapper<Tracking> {}
