package com.lqf.seckill.goods.service;

import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListReqVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListRspVO;

import java.util.List;

public interface GoodsService {
    /**
     * 查询秒杀商品列表
     *
     * @param reqVO
     * @return
     */
    Response<List<FindSeckillGoodsListRspVO>> findSeckillGoodsList(FindSeckillGoodsListReqVO reqVO);
}
