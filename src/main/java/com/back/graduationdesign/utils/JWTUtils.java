package com.back.graduationdesign.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;

@Data
public class JWTUtils {

    private static final String secret = "123456abc";
    private static final long expire = 3600;
    private static final String header = "token";


    /**
     * 生成token
     * @param subject
     * @return
     */
    public static String createToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 60*60*1000);//过期时间

        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }


    /**
     * 获取token中用户信息
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 验证token是否过期
     * @param date
     * @return
     */
    public static boolean isTokenExpired(Date date){
        return date.before(new Date());
    }


    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token){
        return getTokenClaim(token).getExpiration();
    }


    /**
     * 获取token中的用户名
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token){
        return getTokenClaim(token).getSubject();
    }


    /**
     * 获取token签发时间
     * @param token
     * @return
     */
    public static Date getIssuedAtDateFromToken(String token){
        return getTokenClaim(token).getIssuedAt();
    }

}
