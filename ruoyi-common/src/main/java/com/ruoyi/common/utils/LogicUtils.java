package com.ruoyi.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.exception.ParamException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LogicUtils {
    /**
     * 期待传入的参数 为 true
     *
     * @param flag 传入的参数
     */
    public static void assertTrue(boolean flag, String msg) {
        if (!flag) {
            throw new ParamException(msg);
        }
    }
    public static boolean isEquals(BigDecimal obj1, BigDecimal obj2) {
        return (obj1 == null && obj2 == null) || (obj1 != null && obj2 != null && obj1.compareTo(obj2) == 0);
    }
    public static void assertFalse(boolean flag, String msg) {
        if (flag) {
            throw new ParamException(msg);
        }
    }

    public static void assertNotNull(Object obj, String msg) {
        if (isNull(obj)) {
            throw new ParamException(msg);
        }
    }

    public static void assertNull(Object obj, String msg) {
        if (isNotNull(obj)) {
            throw new ParamException(msg);
        }
    }

    public static void assertNotBlank(String obj, String msg) {
        if (isBlank(obj)) {
            throw new ParamException(msg);
        }
    }

    public static void assertNotEmpty(List<?> obj, String msg) {
        if (isEmpty(obj)) {
            throw new ParamException(msg);
        }
    }

    public static boolean isNotBlank(String obj) {
        return !isBlank(obj);
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isBlank(String obj) {
        return isNull(obj) || obj.trim().isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> arr) {
        return !isEmpty(arr);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isEmpty(Collection<?> arr) {
        return isNull(arr) || arr.isEmpty();
    }

    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }

    public static boolean isEmpty(Object[] arr) {
        return isNull(arr) || arr.length == 0;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static String join(List<String> list, String separator) {
        return StringUtils.join(list, separator);
    }


    public static void assertEquals(Object obj1, Object obj2, String msg) {
        if (isNotEquals(obj1, obj2)) {
            throw new ParamException(msg);
        }
    }

    public static void assertNotEquals(Object obj1, Object obj2, String msg) {
        if (isEquals(obj1, obj2)) {
            throw new ParamException(msg);
        }
    }

    public static boolean isNotEquals(Object obj1, Object obj2) {
        return !isEquals(obj1, obj2);
    }

    public static boolean isEquals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }


    public static boolean isNumber(String num) {
        try {
            double v = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static BigDecimal getRandom(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String getRandomNumber(int count) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < count; i++) {
            sb.append(random.nextInt(9) + (i == 0 ? 1 : 0));
        }
        return sb.toString();
    }


    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static String getOrderSn(String model) {
        long l = snowflake.nextId();
        return model + l;
    }
}
