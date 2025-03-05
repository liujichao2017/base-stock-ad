package com.ruoyi.common.enums;

import com.ruoyi.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YNEnum {
    YES("1"),
    NO("0"),
    ;
    private final String type;

    public static YNEnum getEnum(String type) {
        for (YNEnum e : YNEnum.values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        throw new GlobalException("枚举异常");
    }

    public static boolean yes(String type) {
        return getEnum(type) == YNEnum.YES;
    }

    public static boolean no(String type) {
        return getEnum(type) == YNEnum.NO;
    }
}
