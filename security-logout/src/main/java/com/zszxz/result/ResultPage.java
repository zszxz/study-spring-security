package com.zszxz.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lsc
 * <p> </p>
 */
@Data//set get 等方法
@AllArgsConstructor
@NoArgsConstructor
public class ResultPage<T> {

    // 状态码
    private Integer code;
    // 消息提示
    private String msg;
    // 存放的数据
    private T data;


    ResultPage(Integer code,String msg) {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    // 成功的时候调用
    public static <T> ResultPage<T> sucess(CodeMsg codeMsg,T data){
        return new ResultPage<T>(codeMsg.getCode(),codeMsg.getMsg(),data);
    }

    //失败的时候调用
    public static <T> ResultPage<T> error(CodeMsg codeMsg){
        return new ResultPage<T>(codeMsg.getCode(),codeMsg.getMsg());
    }
}
