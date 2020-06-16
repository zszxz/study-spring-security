package com.zszxz.handler;

import com.alibaba.fastjson.JSON;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lsc
 * <p>登陆失败调用 </p>
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 返回值
        ResultPage result = ResultPage.error(CodeMsg.ACCOUNT_ERROR);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
