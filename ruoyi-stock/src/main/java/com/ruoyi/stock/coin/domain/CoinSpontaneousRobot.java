package com.ruoyi.stock.coin.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 机器人对象 coin_spontaneous_robot
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinSpontaneousRobot implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 交易对: 自发币ID */
    @Excel(name = "交易对: 自发币ID")
    private Long csId;

    /** 小于现价 */
    @Excel(name = "小于现价")
    private BigDecimal minPrice;

    /** 大于现价 */
    @Excel(name = "大于现价")
    private BigDecimal maxPrice;

    /** 频率:毫秒 */
    @Excel(name = "频率:毫秒")
    private Long minFrequency;

    /** 频率:毫秒 */
    @Excel(name = "频率:毫秒")
    private Long maxFrequency;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal minNum;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal maxNum;

    /** 波幅 */
    @Excel(name = "波幅")
    private BigDecimal minAmplitude;

    /** 波幅 */
    @Excel(name = "波幅")
    private BigDecimal maxAmplitude;

    /** 优先级 */
    @Excel(name = "优先级")
    private Long priority;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
