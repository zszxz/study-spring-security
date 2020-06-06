package com.zszxz.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author lsc
 * <p> </p>
 */
public class PasswordUtils {

    /* *
     * @Author lsc
     * <p> 加密</p>
     * @Param [password]
     */
    public static String BCrypt(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(password);
        return result;
    }

    /* *
     * @Author lsc
     * <p> 校验</p>
     * @Param [password]
     */
    public static Boolean verifyBrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(password);
        return encoder.matches(password,result);
    }

}
