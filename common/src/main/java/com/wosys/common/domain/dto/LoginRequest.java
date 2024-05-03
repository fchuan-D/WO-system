// @author:樊川
// @email:945001786@qq.com
package com.wosys.common.domain.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String UserName;
    private String UserPwd;
}
