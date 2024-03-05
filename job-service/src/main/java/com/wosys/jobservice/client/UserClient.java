// @author:樊川
// @email:945001786@qq.com
package com.wosys.jobservice.client;


import com.wosys.common.domain.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
public interface UserClient {
    @RequestMapping("/get/{uid}")
    User getUserById(@PathVariable("uid") int uid);
}