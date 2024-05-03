// @author:樊川
// @email:945001786@qq.com
package com.wosys.common.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("TU_User")
public class User {
    @TableId(value = "User_ID", type = IdType.AUTO)
    private Long UserID;

    @TableField("User_Name")
    private String UserName;

    @TableField("User_Pwd")
    private String UserPwd;

    @TableField("User_Email")
    private String UserEmail;

    @TableField("User_Tel")
    private String UserTel;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    // 自动填充创建时间和更新时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
