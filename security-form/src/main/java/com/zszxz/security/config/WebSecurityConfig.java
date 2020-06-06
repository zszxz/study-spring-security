package com.zszxz.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author lsc
 * <p> </p>
 */
@EnableWebSecurity// 开启springSecurity
public class WebSecurityConfig {

    /* *
     * @Author lsc
     * <p> 主要是让SpringSecurity知道springSecurityFilterChain负责管理应用的URL
     * 校验，通过账号，密码的方式校验成功后重定向至应用；
     *  </p>
     * @Param []
     */
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        //
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("user").roles("USER").build());
        manager.createUser(users.username("admin").password("admin").roles("USER","ADMIN").build());
        return manager;
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/**")
                    .authorizeRequests(authorize -> authorize
                            .anyRequest().hasRole("USER")
                    )
                    .formLogin(form -> {
                        form
                                .loginPage("/login") // 配置登陆页
                                .loginProcessingUrl("/login")
                                .successForwardUrl("/login/success")// 登陆成功后会转发至 /login/success 请求
                                .permitAll(); // 允许所有人访问
                    })
                    .csrf().disable()
                    .httpBasic();// http请求方式 ,web 浏览器会弹出对话框

        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorize ->
                            authorize // 授权
                            .anyRequest() // 任何请求
                            .authenticated() // 认证认证
                    )
                    .formLogin(withDefaults()); //
        }
    }

}
