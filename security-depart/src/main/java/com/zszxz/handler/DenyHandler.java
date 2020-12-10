package com.zszxz.handler;

import com.alibaba.fastjson.JSON;
import com.zszxz.result.CodeMsg;
import com.zszxz.result.ResultPage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lsc
 * <p> 权限不足处理 </p>
 */
@Component
public class DenyHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 设置响应头
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 返回值
        ResultPage result = ResultPage.error(CodeMsg.PERM_ERROR);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
