package com.zszxz.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lsc
 * <p> </p>
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {

        return "hello zszxz";
    }
}
