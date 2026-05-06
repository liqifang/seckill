package com.lqf.seckill.goods.controller;

import com.lqf.seckill.common.aspect.ApiOperationLog;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListReqVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListRspVO;
import com.lqf.seckill.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill/goods")
@Slf4j
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /**
     * 查询秒杀商品列表
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/list")
    @ApiOperationLog("查询秒杀商品列表")
    public Response<List<FindSeckillGoodsListRspVO>> getSeckillGoodsList(@RequestBody @Validated FindSeckillGoodsListReqVO reqVO) {
        return goodsService.findSeckillGoodsList(reqVO);
    }
}