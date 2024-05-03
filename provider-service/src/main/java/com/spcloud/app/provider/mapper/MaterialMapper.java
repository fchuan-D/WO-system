package com.spcloud.app.provider.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.provider.pojo.Material;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("材料信息")
public interface MaterialMapper extends BaseMapper<Material> {}
