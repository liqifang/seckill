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

    /**
     * 商品详情缓存 Key 前缀
     * <p>
     * 完整格式：seckill:goods:detail:{activityId}:{goodsId}
     */
    public static final String GOODS_DETAIL_PREFIX = "seckill:goods:detail:";

    /**
     * 商品详情缓存过期时间（单位：分钟）
     */
    public static final long GOODS_DETAIL_TTL_MINUTES = 30;

}
