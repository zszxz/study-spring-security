package com.zszxz.handler;

import com.alibaba.fastjson.JSON;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class OutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 设置响应头
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 返回值
        ResultPage result = ResultPage.sucess(CodeMsg.SUCESS,"退出登陆成功");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
