package com.lqf.seckill.common.domain.mapper;

import com.lqf.seckill.common.domain.dataobject.GoodsDetailDO;

public interface GoodsDetailDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsDetailDO record);

    int insertSelective(GoodsDetailDO record);

    GoodsDetailDO selectByPrimaryKey(Long id);

    /**
     * 根据商品 ID 查询商品详情
     */
    GoodsDetailDO selectByGoodsId(Long goodsId);

    int updateByPrimaryKeySelective(GoodsDetailDO record);

    int updateByPrimaryKeyWithBLOBs(GoodsDetailDO record);

    int updateByPrimaryKey(GoodsDetailDO record);
}