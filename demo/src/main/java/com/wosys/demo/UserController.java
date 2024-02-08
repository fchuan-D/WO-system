// @author:樊川
// @email:945001786@qq.com
package com.wosys.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/say")
    public String Say() {
        return "hello lld";
    }
}
