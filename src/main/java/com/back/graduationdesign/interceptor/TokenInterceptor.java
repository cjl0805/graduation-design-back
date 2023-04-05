package com.back.graduationdesign.interceptor;

import com.back.graduationdesign.utils.JWTUtils;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        System.out.println("request = " + request);
        String uri = request.getRequestURI();
        log.info("拦截到的请求为{}",uri);
        System.out.println("uri = " + uri);
        String token = request.getHeader("token");
        log.info("token:{}",token);
        System.out.println("token = " + token);
        //如果token为空，直接抛出异常
        if (StringUtils.isBlank(token)){
            throw new Exception("token不能为空");
        }
        else {
            Claims claims = null;
            try{
                //获取token中的声明（载荷）信息
                claims = JWTUtils.getTokenClaim(token);
                if (uri.contains("/backend") && !claims.getSubject().equals("admin")){
                    throw new Exception("当前不是管理员，权限不足");
                }
                //判断载荷是否为空或者失效时间已过
                if (claims == null || JWTUtils.isTokenExpired(claims.getExpiration())){
                    PrintWriter writer = response.getWriter();
                    String result = JSON.toJSONString(new R(501, "token已过期", null));
                    writer.print(result);
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
