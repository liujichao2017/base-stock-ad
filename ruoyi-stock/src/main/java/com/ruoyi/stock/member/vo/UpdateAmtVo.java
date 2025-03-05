package com.ruoyi.stock.member.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAmtVo {
    private Long marketId;
    private Long memberId;
    private BigDecimal amt;
    /**
     * 1: 上分
     * 2: 下分
     */
    private String type;
}
