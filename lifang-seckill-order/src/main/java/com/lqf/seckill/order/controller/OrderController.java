package com.lqf.seckill.order.controller;

import com.lqf.seckill.common.aspect.ApiOperationLog;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.order.model.vo.DoSeckillReqVO;
import com.lqf.seckill.order.model.vo.DoSeckillRspVO;
import com.lqf.seckill.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ApiOperationLog("秒杀下单")
    public Response<DoSeckillRspVO> doSeckill(@RequestBody @Validated DoSeckillReqVO reqVO){
        return orderService.doSeckill(reqVO);
    }
}
