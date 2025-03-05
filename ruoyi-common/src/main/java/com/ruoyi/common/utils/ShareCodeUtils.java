package com.ruoyi.common.utils;

import com.ruoyi.common.utils.uuid.UUID;

import java.util.Random;

/**
 * 邀请码工具类
 */
public class ShareCodeUtils {

    /**
     * 获取自定义长度邀请码
     * @param count 长度
     * @return 邀请码
     */
    public static String getCode(int count){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        String uuidStr = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        String[] split = uuidStr.split("");
        for (int i = count; i > 0; i--) {
            builder.append(split[random.nextInt(split.length)]);
        }
        return builder.toString();
    }

    /**
     * 获取自定义长度邀请码
     * @param count 长度
     * @return 邀请码
     */
    public static String getCodeNumber(int count){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        String uuidStr = System.currentTimeMillis() + "";
        String[] split = uuidStr.split("");
        for (int i = count; i > 0; i--) {
            builder.append(split[random.nextInt(split.length)]);
        }
        return builder.toString();
    }
}
