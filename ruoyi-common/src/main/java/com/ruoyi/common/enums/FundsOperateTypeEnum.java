package com.ruoyi.common.enums;

import com.ruoyi.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundsOperateTypeEnum {
    /**
     * 其他
     */
    OTHER("0", "其他"),
    /**
     * 买入
     */
    BUY("1", "买入"),
    /**
     * 卖出
     */
    SELL("2", "卖出"),
    /**
     * 盈亏
     */
    PROFIT_LOSS("3", "盈亏"),
    /**
     * 退款
     */
    ROLLBACK("4", "驳回"),
    /**
     * 冻结
     */
    FREEZE("5", "冻结"),
    /**
     * 解冻冻结
     */
    UNFREEZE("6", "解冻"),

    /**
     * 放款
     */
    LOAN("7", "放款"),
    /**
     * 还款
     */
    LOAN_RECYCLE("8", "还款"),
    ;
    private final String type;
    private final String name;

    public static FundsOperateTypeEnum getEnum(String type) {
        for (FundsOperateTypeEnum f : FundsOperateTypeEnum.values()) {
            if (f.getType().equals(type)) {
                return f;
            }
        }
        throw new GlobalException("枚举异常");
    }
}
