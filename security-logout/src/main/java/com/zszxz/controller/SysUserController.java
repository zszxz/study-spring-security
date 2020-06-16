package com.zszxz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lsc
 * <p> </p>
 */
@RestController
public class SysUserController {

    @GetMapping("api/test")
    public String  test(){
        return "普通用户访问";
    }

    @GetMapping("api/admin/test")
    public String  testAdmin(){
        return "管理员访问";
    }
}
