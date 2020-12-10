package com.zszxz.handler;

import com.alibaba.fastjson.JSON;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lsc
 * <p> </p>
 */
@Component
public class ExpAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 设置响应头
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 返回值
        ResultPage result = ResultPage.error(CodeMsg.ACCOUNT_ERROR);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
