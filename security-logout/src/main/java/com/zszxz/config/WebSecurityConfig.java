package com.zszxz.config;

import com.zszxz.handler.FailureHandler;
import com.zszxz.handler.OutSuccessHandler;
import com.zszxz.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author lsc
 * <p> </p>
 */
@EnableWebSecurity// 开启springSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SuccessHandler successHandler;

    @Autowired
    FailureHandler failureHandler;

    @Autowired
    OutSuccessHandler outSuccessHandler;


    /* *
     * @Author lsc
     * <p> 授权</p>
     * @Param [http]
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()// 授权
                .antMatchers("/api/download/**").anonymous()// 匿名用户权限
                .antMatchers("/api/**").hasRole("USER")//普通用户权限
                .antMatchers("/api/admin/**").hasRole("ADMIN")// 管理员权限
                .and()
                .formLogin()// 登陆
                .loginProcessingUrl("/api/login")
                            .successHandler(successHandler)// 登陆成功后的处理动作
                            .failureHandler(failureHandler)// 登陆失败后的处理动作
                            .permitAll() // 允许所有人访问
                .and()
                .logout()
                .logoutSuccessHandler(outSuccessHandler)
                .and()
                .csrf().disable()// 关闭 csrf 否则post
                .httpBasic();// http请求方式 ,web 浏览器会弹出对话框

    }



}
