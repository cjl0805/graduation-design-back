package com.back.graduationdesign.utils;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class RegexUtils {
    private static final String phoneRegex =
            "^1[3-9]\\d{9}$";
    private static final String IDCardRegex =
            "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    public static boolean checkPhone(String phone){
        return Pattern.matches(phoneRegex,phone);
    }

    public static boolean checkIDCard(String IDCard){
        return Pattern.matches(IDCardRegex,IDCard);
    }

}
