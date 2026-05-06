package com.lqf.seckill.common.domain.mapper;

import com.lqf.seckill.common.domain.dataobject.GoodsDO;

import java.util.List;

public interface GoodsDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsDO record);

    int insertSelective(GoodsDO record);

    GoodsDO selectByPrimaryKey(Long id);

    /**
     * 根据主键批量查询商品
     */
    List<GoodsDO> selectByIds(List<Long> ids);

    int updateByPrimaryKeySelective(GoodsDO record);

    int updateByPrimaryKey(GoodsDO record);
}