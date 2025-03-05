package com.ruoyi.stock.coin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易对对象 coin_symbols
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinSymbols implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 交易对
     */
    @Excel(name = "交易对")
    private String symbol;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String state;

    /**
     * 前段显示
     */
    @Excel(name = "前段显示")
    private Integer hot;

    /**
     * 每张币数
     */
    @Excel(name = "每张币数")
    private BigDecimal sheet;

    /**
     * 交易手续费
     */
    @Excel(name = "交易手续费")
    private BigDecimal feeAmt;

    /**
     * 币种类型
     */
    @Excel(name = "币种类型")
    private String type;

    /**
     * 基础币种显示名称
     */
    @Excel(name = "基础币种显示名称")
    private String bcdn;

    /**
     * 计价币种显示名称
     */
    @Excel(name = "计价币种显示名称")
    private String qcdn;

    /**
     * 币图标
     */
    @Excel(name = "币图标")
    private String icon;

    /**
     * 可交易
     */
    @Excel(name = "可交易")
    private Integer tradeState;

    /**
     * 交易对名称
     */
    @Excel(name = "交易对名称")
    private String sn;

    /**
     * 最新价
     */
    @Excel(name = "最新价")
    private BigDecimal price;

    /**
     * 开盘
     */
    @Excel(name = "开盘")
    private BigDecimal open;

    /**
     * 收盘价
     */
    @Excel(name = "收盘价")
    private BigDecimal close;

    /**
     * 交易量
     */
    @Excel(name = "交易量")
    private BigDecimal amount;

    /**
     * 交易次数
     */
    @Excel(name = "交易次数")
    private Long counts;

    /**
     * 最低
     */
    @Excel(name = "最低")
    private BigDecimal low;

    /**
     * 最高
     */
    @Excel(name = "最高")
    private BigDecimal high;

    /**
     * 币种计量的交易量
     */
    @Excel(name = "币种计量的交易量")
    private BigDecimal vol;

    /**
     * 当前的最高买价
     */
    @Excel(name = "当前的最高买价")
    private BigDecimal bid;

    /**
     * 最高买价对应的量
     */
    @Excel(name = "最高买价对应的量")
    private BigDecimal bidSize;

    /**
     * 当前的最低卖价
     */
    @Excel(name = "当前的最低卖价")
    private BigDecimal ask;

    /**
     * 最低卖价对应的量
     */
    @Excel(name = "最低卖价对应的量")
    private BigDecimal askSize;

    /**
     * 最新成交价对应的量
     */
    @Excel(name = "最新成交价对应的量")
    private BigDecimal lastSize;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
