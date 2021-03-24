package com.zszxz.filter;

import com.zszxz.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @Author lsc
 * <p>请求拦截认证 </p>
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 从请求头中取出 token
        String token = request.getHeader("token");
        // 2. 判断token是否为空
        if (StringUtils.isNotBlank(token)) {
            // 3. 从 token 里获取用户名
            String username = JwtUtil.getUsernameByToken(token);
            // 4. 只要 token 没过期，就让用户保持登录状态 jwt 令牌只是辅助登录, 真正是否登录要看 authentication 是否有效
            if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // token校验,保持登陆
                if (JwtUtil.validateToken(token)) {
                    Set rolseByToken = JwtUtil.getRolseByToken(token);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, rolseByToken);
                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
