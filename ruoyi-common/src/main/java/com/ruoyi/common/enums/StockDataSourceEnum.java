package com.ruoyi.common.enums;

import com.ruoyi.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StockDataSourceEnum {
    /**
     * 匠山数据源
     */
    JS("JS"),
    /**
     * 路透交易所
     */
    LT("LT"),
    /**
     * A股
     */
    CNA("CNA"),
    /**
     * HK
     */
    HK("HK"),
    /**
     * 虚拟币-火币
     */
    COIN("COIN"),
    /**
     * 启源
     */
    QY("QY"),
    ;
    private final String type;

    public static StockDataSourceEnum getEnum(String type) {
        for (StockDataSourceEnum e : StockDataSourceEnum.values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        throw new GlobalException("枚举异常");
    }

    public boolean isStockHis(){
        return StockDataSourceEnum.CNA == this || StockDataSourceEnum.HK == this;
    }
}
