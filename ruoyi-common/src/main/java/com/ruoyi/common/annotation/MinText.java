package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义数据脱敏
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinText {

    /**
     * 脱敏位数 默认4位
     */
    public int position() default 4;

    /**
     * 是否执行脱敏 默认执行
     */
    public boolean execute() default true;
}
