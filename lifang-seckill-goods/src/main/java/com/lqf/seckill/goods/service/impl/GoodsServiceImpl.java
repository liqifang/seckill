package com.lqf.seckill.goods.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqf.seckill.common.domain.dataobject.GoodsDO;
import com.lqf.seckill.common.domain.dataobject.SeckillActivityDO;
import com.lqf.seckill.common.domain.dataobject.SeckillGoodsDO;
import com.lqf.seckill.common.domain.mapper.GoodsDOMapper;
import com.lqf.seckill.common.domain.mapper.SeckillActivityDOMapper;
import com.lqf.seckill.common.domain.mapper.SeckillGoodsDOMapper;
import com.lqf.seckill.common.enums.ActivityStatusEnum;
import com.lqf.seckill.common.enums.ResponseCodeEnum;
import com.lqf.seckill.common.exception.BizException;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListReqVO;
import com.lqf.seckill.goods.model.vo.FindSeckillGoodsListRspVO;
import com.lqf.seckill.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final SeckillGoodsDOMapper seckillGoodsDOMapper;
    private final SeckillActivityDOMapper seckillActivityDOMapper;
    private final GoodsDOMapper goodsDOMapper;

    /**
     * 查询秒杀商品列表
     * @param reqVO
     * @return
     */
    @Override
    public Response<List<FindSeckillGoodsListRspVO>> findSeckillGoodsList(FindSeckillGoodsListReqVO reqVO) {
        // 活动 ID
        Long activityId = reqVO.getActivityId();
        log.info("==> 查询秒杀商品列表，activityId={}", activityId);

        // 1.查询活动信息
        SeckillActivityDO activityDO = seckillActivityDOMapper.selectByPrimaryKey(activityId);
        if (Objects.isNull(activityDO)) {
            throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_NOT_EXIST);
        }

        // 2.根据活动 ID 查询该活动下的所有秒杀商品列表
        List<SeckillGoodsDO> seckillGoodsDOS = seckillGoodsDOMapper.selectByActivityId(activityId);
        if (CollUtil.isEmpty(seckillGoodsDOS)) {
            log.info("==> 该活动下无秒杀商品，activityId={}", activityId);
            return Response.success(Collections.emptyList());
        }

        // 3.批量查询关联的商品信息
        List<Long> goodsIds = seckillGoodsDOS.stream().map(SeckillGoodsDO::getGoodsId).toList();

        // 查询商品原价
        List<GoodsDO> goodsDOS = goodsDOMapper.selectByIds(goodsIds);

        // 将商品 ID 和商品信息映射为 Map，方便后续查找
        Map<Long, GoodsDO> goodsMap = goodsDOS.stream()
                .collect(Collectors.toMap(GoodsDO::getId, goodsDO -> goodsDO));

        // 4.计算活动状态
        ActivityStatusEnum activityStatusEnum = calculateActivityStatus(activityDO);

        // 5. 组装响应数据
        List<FindSeckillGoodsListRspVO> rspVOS = new ArrayList<>();
        for (SeckillGoodsDO seckillGoodsDO : seckillGoodsDOS) {
            FindSeckillGoodsListRspVO rspVO = new FindSeckillGoodsListRspVO();
            rspVO.setId(seckillGoodsDO.getId());
            rspVO.setActivityId(seckillGoodsDO.getActivityId());
            rspVO.setSeckillTitle(seckillGoodsDO.getSeckillTitle());
            rspVO.setSeckillImg(seckillGoodsDO.getSeckillImg());
            rspVO.setSeckillPrice(seckillGoodsDO.getSeckillPrice());
            rspVO.setSeckillTotal(seckillGoodsDO.getSeckillTotal());
            rspVO.setSeckillStock(seckillGoodsDO.getSeckillStock());
            rspVO.setActivityStatus(activityStatusEnum.getStatus());
            rspVO.setBeginTime(activityDO.getBeginTime());
            rspVO.setEndTime(activityDO.getEndTime());

            // 设置商品原价
            GoodsDO goodsDO = goodsMap.get(seckillGoodsDO.getGoodsId());
            if (Objects.nonNull(goodsDO)) {
                rspVO.setGoodsPrice(goodsDO.getGoodsPrice());
            }

            rspVOS.add(rspVO);
        }

        return Response.success(rspVOS);
    }

    /**
     * 根据当前时间动态计算活动状态
     *
     * @param activityDO
     * @return
     */
    private ActivityStatusEnum calculateActivityStatus(SeckillActivityDO activityDO) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(activityDO.getBeginTime())) { // 当前时间早于活动开始时间，则活动未开始
            return ActivityStatusEnum.NOT_STARTED;
        } else if (now.isAfter(activityDO.getEndTime())) { // 当前时间晚于活动结束时间，则活动已结束
            return ActivityStatusEnum.ENDED;
        } else { // 活动进行中
            return ActivityStatusEnum.ING;
        }
    }
}
