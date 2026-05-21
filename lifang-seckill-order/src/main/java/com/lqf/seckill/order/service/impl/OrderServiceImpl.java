package com.lqf.seckill.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.lqf.seckill.common.domain.dataobject.GoodsDO;
import com.lqf.seckill.common.domain.dataobject.SeckillActivityDO;
import com.lqf.seckill.common.domain.dataobject.SeckillGoodsDO;
import com.lqf.seckill.common.domain.dataobject.SeckillOrderDO;
import com.lqf.seckill.common.domain.mapper.GoodsDOMapper;
import com.lqf.seckill.common.domain.mapper.SeckillActivityDOMapper;
import com.lqf.seckill.common.domain.mapper.SeckillGoodsDOMapper;
import com.lqf.seckill.common.domain.mapper.SeckillOrderDOMapper;
import com.lqf.seckill.common.enums.ResponseCodeEnum;
import com.lqf.seckill.common.exception.BizException;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.order.enums.OrderStatusEnum;
import com.lqf.seckill.order.model.vo.DoSeckillReqVO;
import com.lqf.seckill.order.model.vo.DoSeckillRspVO;
import com.lqf.seckill.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final SeckillActivityDOMapper seckillActivityDOMapper;
    private final SeckillGoodsDOMapper seckillGoodsDOMapper;
    private final GoodsDOMapper goodsDOMapper;
    private final SeckillOrderDOMapper seckillOrderDOMapper;

    /**
     * 秒杀下单
     *
     * @param reqVO
     * @return Response
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response<DoSeckillRspVO> doSeckill(DoSeckillReqVO reqVO) {
        // 活动 ID
        Long activityId = reqVO.getActivityId();
        // 商品 ID
        Long goodsId = reqVO.getGoodsId();

        // 1.获取当前登录用户 ID
        long userId = StpUtil.getLoginIdAsLong();

        // 2.校验活动是否存在
        SeckillActivityDO activityDO = seckillActivityDOMapper.selectByPrimaryKey(activityId);
        if (Objects.isNull(activityDO)) {
            throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_NOT_EXIST);
        }

        // 3.校验秒杀活动时间
        LocalDateTime now = LocalDateTime.now();
        // 活动还没开始
        if (now.isBefore(activityDO.getBeginTime())) {
            throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_NOT_STARTED);
        }

        // 活动已经结束
        if (now.isAfter(activityDO.getEndTime())) {
            throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_ENDED);
        }

        // 4.根据活动 ID 和商品 ID 查询秒杀商品，校验秒杀商品是否存在
        SeckillGoodsDO seckillGoodsDO = seckillGoodsDOMapper.selectByActivityIdAndGoodsId(activityId, goodsId);
        if (Objects.isNull(seckillGoodsDO)) {
            throw new BizException(ResponseCodeEnum.SECKILL_GOODS_NOT_EXIST);
        }

        // 5.库存校验，必需大于 0
        if (seckillGoodsDO.getSeckillStock() <= 0) {
            throw new BizException(ResponseCodeEnum.SECKILL_GOODS_SOLD_OUT);
        }

        // 6.扣减库存
        int i = seckillGoodsDOMapper.deductStock(seckillGoodsDO.getId());
        if (i <= 0) {
            throw new BizException(ResponseCodeEnum.SECKILL_GOODS_SOLD_OUT);
        }

        // 7.查询商品信息，冗余至订单信息中
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(goodsId);

        // 8.创建订单，使用Hutool库生成雪花ID订单号，设置订单过期时间为当前时间 + 30 min，插入订单记录
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        LocalDateTime expireTime = now.plusMinutes(30);

        SeckillOrderDO orderDO = SeckillOrderDO.builder()
                .userId(userId)
                .activityId(activityId)
                .goodsId(goodsId)
                .orderNo(orderNo)
                .seckillPrice(seckillGoodsDO.getSeckillPrice())
                .goodsName(goodsDO.getGoodsName())
                .goodsImg(goodsDO.getGoodsImg())
                .status(OrderStatusEnum.PENDING_PAYMENT.getStatus())
                .expireTime(expireTime)
                .isDeleted(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        try {
            seckillOrderDOMapper.insert(orderDO);
        } catch (DuplicateKeyException e) {
            log.warn("==> 重复下单, userId: {}, activityId: {}, goodsId: {}", userId, activityId, goodsId);
            throw new BizException(ResponseCodeEnum.SECKILL_ORDER_DUPLICATE);
        }

        log.info("==> 秒杀下单成功, orderId: {}, orderNo: {}", orderDO.getId(), orderNo);

        // 9. 组装响应数据
        DoSeckillRspVO rspVO = DoSeckillRspVO.builder()
                .orderId(orderDO.getId())
                .orderNo(orderNo)
                .goodsName(goodsDO.getGoodsName())
                .goodsImg(goodsDO.getGoodsImg())
                .seckillPrice(seckillGoodsDO.getSeckillPrice())
                .status(OrderStatusEnum.PENDING_PAYMENT.getStatus())
                .expireTime(expireTime)
                .build();

        return Response.success(rspVO);
    }
}
