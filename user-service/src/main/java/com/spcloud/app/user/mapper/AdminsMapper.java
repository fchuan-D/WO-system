package com.spcloud.app.user.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spcloud.app.user.pojo.Admins;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("管理员")
public interface AdminsMapper extends BaseMapper<Admins> {}
