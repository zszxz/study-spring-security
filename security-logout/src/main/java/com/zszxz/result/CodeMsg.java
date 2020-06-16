package com.zszxz.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor//全参构造
@NoArgsConstructor//空参构造
public enum CodeMsg {

    SUCESS(200,"sucess"),
    PERM_ERROR(403,"没有权限"),
    ACCOUNT_ERROR(401,"账号或者密码错误");

    // 错误消息码
    private Integer code;
    // 错误消息提示
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
