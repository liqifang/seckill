package com.lqf.seckill.common.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDetailDO {
    private Long id;

    private Long goodsId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String detailContent;
}