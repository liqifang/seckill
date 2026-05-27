package com.lqf.seckill.common.constant;

/**
 * @Description: Redis Key 缓存常量
 */
public class RedisKeyConstants {

    /**
     * 商品列表缓存前缀
     * <p>
     * 完整格式：seckill:goods:list:{activityId}
     */
    public static final String GOODS_LIST_PREFIX = "seckill:goods:list:";

    /**
     * 商品列表缓存过期时间（单位：分钟）
     */
    public static final long GOODS_LIST_TTL_MINUTES = 30;
}
