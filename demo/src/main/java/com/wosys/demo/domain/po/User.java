// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;


@Data
@TableName("wo_user")
public class User {
    @TableId(value = "u_id", type = IdType.AUTO)
    private Long uid;

    @TableField("u_name")
    private String uname;

    @TableField("u_age")
    private String uage;

    @TableField("u_addr")
    private String uaddr;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    // 自动填充创建时间和更新时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
