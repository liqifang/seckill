package com.lqf.seckill.order.service;

import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.order.model.vo.DoSeckillReqVO;
import com.lqf.seckill.order.model.vo.DoSeckillRspVO;

public interface OrderService {
    /**
     * 秒杀下单
     *
     * @param reqVO
     * @return
     */
    Response<DoSeckillRspVO> doSeckill(DoSeckillReqVO reqVO);
}
