package com.zszxz.controller;

import com.zszxz.entity.SysUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * @Author lsc
 * <p> </p>
 */
@RestController
public class SysUserController {




    @GetMapping("test/seu")
    @Secured("ROLE_USER")
    public String testSecurity() {
        return "需要USER权限";
    }

    @GetMapping("test/allow")
    @RolesAllowed({"USER","ADMIN"})
    public String testAllow() {
        return "需要权限";
    }

    @GetMapping("test/perm")
    @PermitAll
    public String testPerm() {
        return "允许";
    }

    @GetMapping("test/deny")
    @DenyAll
    public String testDeny() {
        return "拒绝";
    }

    @GetMapping("api/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String authAdmin() {
        return "需要ADMIN权限";
    }

    @GetMapping("api/test")
    @PreAuthorize("isAnonymous()")
    public String authUser() {
        return "匿名用户访问";
    }


    @GetMapping("api/has")
    @PreAuthorize("#sysUser.username == 'admin' ")
    public String hasPerm(SysUser sysUser) {
        return "EL测试";
    }

    @GetMapping("api/sys")
    @PreAuthorize("#c.username == authentication.principal ")
    public String hasPerm2(@P("c")SysUser sysUser) {
        return "EL测试";
    }

}
