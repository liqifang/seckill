package com.lqf.seckill.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: SaToken 配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 SaToken 拦截器，打开注解鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 秒杀下单接口，需要登录
            SaRouter.match("/seckill/order", r -> StpUtil.checkLogin());
            SaRouter.match("/user/logout", r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}
