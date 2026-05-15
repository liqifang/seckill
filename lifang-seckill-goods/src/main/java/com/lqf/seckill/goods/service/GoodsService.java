package com.lqf.seckill.goods.service;

import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsDetailReqVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsDetailRspVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListReqVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListRspVO;

import java.util.List;

public interface GoodsService {
    /**
     * 查询秒杀商品列表
     *
     * @param reqVO 活动 ID
     * @return 秒杀商品列表
     */
    Response<List<FindSeckillGoodsListRspVO>> findSeckillGoodsList(FindSeckillGoodsListReqVO reqVO);

    /**
     * 查询秒杀商品详情
     *
     * @param reqVO 商品 ID，活动 ID
     * @return 秒杀商品详情
     */
    Response<FindSeckillGoodsDetailRspVO> findSeckillGoodsDetail(FindSeckillGoodsDetailReqVO reqVO);

}
