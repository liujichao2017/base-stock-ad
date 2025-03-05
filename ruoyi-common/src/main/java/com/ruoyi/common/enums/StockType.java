package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockType {

    GP("1","股票"),ZS("2","指数");
    private final String type;
    private final String remark;
}
