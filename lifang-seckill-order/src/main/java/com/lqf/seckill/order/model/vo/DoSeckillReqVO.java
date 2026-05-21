package com.lqf.seckill.order.model.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2026/5/21 16:28
 * @Description: 秒杀下单入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoSeckillReqVO {

    /**
     * 活动 ID
     */
    @NotNull(message = "活动 ID 不能为空")
    @Positive(message = "活动 ID 不合法") // 被标注的元素必须是正数，null值不做检查，配合“@NotNull”可保证是不为空的正数
    private Long activityId;

    /**
     * 商品 ID
     */
    @NotNull(message = "商品 ID 不能为空")
    @Positive(message = "商品 ID 不合法")
    private Long goodsId;
}
