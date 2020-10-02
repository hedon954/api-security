package com.hedon.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hedon Wang
 * @create 2020-10-02 17:22
 */
//加入下面这个注解，让异常处理走我们下面的逻辑而不走 Spring 的默认机制
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //一旦抛异常，统一发送500的状态码
    @ExceptionHandler(Exception.class) //处理所有异常
    public Map<String, Object> handle(Exception e){
        Map<String,Object> info = new HashMap<>();
        info.put("message",e.getMessage());
        info.put("time",new Date().getTime());
        return info;
    }

}
