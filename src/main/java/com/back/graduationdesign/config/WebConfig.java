package com.back.graduationdesign.config;

import com.back.graduationdesign.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final String[] urls =new String[]{
            "/**/login",
            "/**/upload",
            "/**/register",
            "/**/hairdressing-shop-introduction/get",
            "/**/hairstyle/list",
            "/**/hairstylist/list",
            "/**/**.jpg"
    };

    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 资源映射路径
         * addResourceHandler：访问映射路径
         * addResourceLocations：资源绝对路径
         */
        String filePath = "F:\\graduation-design\\img";
        String filePath1 = "F:\\graduation-design";
        registry.addResourceHandler("/picture/**")
                .addResourceLocations("file:"+ filePath +"/").addResourceLocations("file"+ filePath1 +"/");
    }

    /**
     * 登录拦截
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(urls);
    }

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
