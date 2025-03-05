package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScoreType {

    SF(1,"上分"),XF(2,"下分");

    private final Integer type;
    private final String remark;
}
