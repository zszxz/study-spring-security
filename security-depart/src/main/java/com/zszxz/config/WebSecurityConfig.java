package com.zszxz.config;

import com.zszxz.filter.JWTLoginFilter;
import com.zszxz.filter.JwtAuthenticationFilter;
import com.zszxz.handler.*;
import com.zszxz.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author lsc
 * <p> </p>
 */
@EnableWebSecurity// 开启springSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    DenyHandler denyHandler;

    @Autowired
    OutSuccessHandler outSuccessHandler;

    @Autowired
    SysUserDetailsService userDetailsService;

    @Autowired
    ExpAuthenticationEntryPoint expAuthenticationEntryPoint;

    /* *
     * @Author lsc
     * <p> 授权</p>
     * @Param [http]
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()// 授权
                .antMatchers("/api/download/**").anonymous()// 匿名用户权限
                .antMatchers("/api/**").hasRole("USER")//普通用户权限
                .antMatchers("/api/admin/**").hasRole("ADMIN")// 管理员权限
                .antMatchers("/login").permitAll()
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()// 异常
                .exceptionHandling()
                .accessDeniedHandler(denyHandler)//授权异常处理
                .authenticationEntryPoint(expAuthenticationEntryPoint)// 认证异常处理
                .and()
                .logout()
                .logoutSuccessHandler(outSuccessHandler)
                .and()
                .addFilterBefore(new JWTLoginFilter("/login",authenticationManager()),UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security不创建HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();// 关闭 csrf 否则post


    }



    /* *
     * @Author lsc
     * <p>认证 设置加密方式 </p>
     * @Param [auth]
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
