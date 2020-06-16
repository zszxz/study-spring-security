package com.zszxz.handler;

import com.alibaba.fastjson.JSON;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lsc
 * <p> 登陆成功后处理 </p>
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 跨域处理
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的请求方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        // 允许的请求头
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 设置响应头
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 返回值
        ResultPage result = ResultPage.sucess(CodeMsg.SUCESS, "登陆成功");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
