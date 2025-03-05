package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RechargeStatus {

    SPZ("0","审批中"),TG("1","通过"),JJ("2","拒绝");
    private final String type;
    private final String remark;
}
