package com.lqf.seckill.goods.controller;

import com.lqf.seckill.common.aspect.ApiOperationLog;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.goods.model.vo.PreheatActivityCacheReqVO;
import com.lqf.seckill.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 管理端 -> 商品管理
 */

@Slf4j
@RestController
@RequestMapping("/admin/seckill/goods")
@RequiredArgsConstructor
public class AdminGoodsController {

    private final GoodsService goodsService;

    @PostMapping("/cache/preheat")
    @ApiOperationLog("手动预热商品缓存")
    public Response<?> preheatCache(@RequestBody @Validated PreheatActivityCacheReqVO reqVO) {
        return goodsService.preheatActivityGoods(reqVO.getActivityId());
    }

}