package com.zszxz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lsc
 * <p> </p>
 */
@RestController
public class SysUserController {


    @GetMapping("api/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String authAdmin() {
        return "需要ADMIN权限";
    }

    @GetMapping("api/test")
    @PreAuthorize("hasAuthority('USER')")
    public String authUser() {
        return "需要USER权限";
    }
}
