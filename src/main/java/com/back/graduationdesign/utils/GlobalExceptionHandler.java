package com.back.graduationdesign.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 */
//@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public R tokenExceptionHandler(Exception e){
//        return new R(501,"token已过期，请重新登录",e.getMessage());
//    }
//}
