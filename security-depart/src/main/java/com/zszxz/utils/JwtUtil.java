package com.zszxz.utils;

import com.zszxz.entity.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

/**
 * @Author lsc
 * <p> </p>
 */
public class JwtUtil {

    private static final String CLAIMS_ROLE = "zszxzRoles";

    /**
     * 5天(毫秒)
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 5;
    /**
     * JWT密码
     */
    private static final String SECRET = "secret";


    /**
     * 签发JWT
     */
    public static String getToken(String username, String roles) {
        Map<String, Object> claims = new HashMap<>(8);
        // 主体
        claims.put( CLAIMS_ROLE, roles);
        return Jwts.builder()
                .setClaims(claims)
                .claim("username",username)
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )// 过期时间
                .signWith( SignatureAlgorithm.HS512, SECRET )// 加密
                .compact();
    }

    /**
     * 验证JWT
     */
    public static Boolean validateToken(String token) {
        return (!isTokenExpired( token ));
    }

    /**
     * 获取token是否过期
     */
    public static Boolean isTokenExpired(String token) {
        Date expiration = getExpireTime( token );
        return expiration.before( new Date() );
    }

    /**
     * 根据token获取username
     */
    public static String getUsernameByToken(String token) {
        String username = (String) parseToken( token ).get("username");
        return username;
    }

    public static Set<GrantedAuthority> getRolseByToken(String token) {
        String rolse = (String) parseToken( token ).get(CLAIMS_ROLE);
        String[] strArray = StringUtils.strip(rolse, "[]").split(", ");
        Set<GrantedAuthority> authoritiesSet = new HashSet();
        if (strArray.length>0){
            Arrays.stream(strArray).forEach(rols-> {
                GrantedAuthority authority = new SimpleGrantedAuthority(rols);
                authoritiesSet.add(authority);
            });
        }
        return authoritiesSet;
    }

    /**
     * 获取token的过期时间
     */
    public static Date getExpireTime(String token) {
        Date expiration = parseToken( token ).getExpiration();
        return expiration;
    }

    /**
     * 解析JWT
     */
    private static Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( SECRET )
                .parseClaimsJws( token )
                .getBody();
        return claims;
    }

}
