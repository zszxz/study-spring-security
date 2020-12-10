package com.zszxz.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zszxz.entity.SysUser;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import com.zszxz.utils.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author lsc
 * <p> 登陆认证过滤器 </p>
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }


    /**
     * @Author lsc
     * <p> 登陆认证</p>
     * @Param [request, response]
     * @Return
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser user = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword());
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * @Author lsc
     * <p> 登陆成功返回token</p>
     * @Param [request, res, chain, auth]
     * @Return
     */
        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain chain, Authentication auth){
            SysUser principal = (SysUser)auth.getPrincipal();
            String token = JwtUtil.getToken(principal.getUsername(),principal.getAuthorities().toString());
            try {
                //登录成功時，返回json格式进行提示
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                ResultPage result = ResultPage.sucess(CodeMsg.SUCESS,token);
                out.write(new ObjectMapper().writeValueAsString(result));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String result="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            result="账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            result="密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            result="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            result="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            result="账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            result="用户不存在";
        }
        // 其他错误
        else{
            result="未知异常";
        }
        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");
        // 将反馈塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
