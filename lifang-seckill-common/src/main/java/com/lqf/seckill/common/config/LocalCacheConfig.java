package com.lqf.seckill.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Caffeine 本地缓存配置
 */
@Configuration
public class LocalCacheConfig {
    
    /**
     * 商品列表本地缓存
     * <p>
     * 最大缓存 100 个商品，30秒后过期
     * @return Caffeine Cache
     */
    @Bean
    public Cache<String, String> goodsListLocalCache(){
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();
    }
    
    /**
     * 商品详细信息本地缓存
     * <p>
     * 最大缓存 500 个商品，30秒后过期
     * @return Caffeine Cache
     */
    @Bean
    public Cache<String, String> goodsDetailLocalCache() {
        return Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();
    }
}
