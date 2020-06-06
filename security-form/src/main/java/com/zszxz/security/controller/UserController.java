package com.zszxz.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Author lsc
 * <p> </p>
 */
@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login/success")
    @ResponseBody
    public String success() {
        return "sucess";
    }
}
