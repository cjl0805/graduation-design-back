package com.back.graduationdesign.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果统一类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private int code;
    private String message;
    private Object data;

    public static R success (Object data){
        return new R(200,"操作成功",data);
    }

    public static R error (String message){
        return new R(500,message,null);
    }
}
