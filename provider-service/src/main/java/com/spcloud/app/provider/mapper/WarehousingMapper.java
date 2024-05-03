package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Warehousing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("入库")
public interface WarehousingMapper extends BaseMapper<Warehousing> {}
