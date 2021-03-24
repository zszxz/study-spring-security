package com.zszxz.service;

import com.zszxz.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author lsc
 * <p> </p>
 */
@Component
@Slf4j
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 登陆验证时，通过username获取用户的所有权限信息； 正式环境中就是查询用户数据授权
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------用户{}身份认证-----",username);
        // 新建用户
        SysUser user = new SysUser();
        // 账号
        user.setUsername(username);
        // 密码
        user.setPassword(passwordEncoder.encode("123456"));
        // 设置权限
        Set authoritiesSet = new HashSet();
        // 注意角色权限需要加 ROLE_前缀，否则报403
        GrantedAuthority userPower = new SimpleGrantedAuthority("ROLE_USER");
        GrantedAuthority adminPower = new SimpleGrantedAuthority("ROLE_ADMIN");
        authoritiesSet.add(userPower);
        authoritiesSet.add(adminPower);
        user.setAuthorities(authoritiesSet);
        return user;
    }
}
