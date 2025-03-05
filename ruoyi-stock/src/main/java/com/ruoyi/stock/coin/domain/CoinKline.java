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
 * 虚拟币K线对象 coin_kline
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinKline implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 时间阶段 */
    @Excel(name = "时间阶段")
    private String period;

    /** 调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id */
    @Excel(name = "调整为新加坡时间的时间戳，单位秒，并以此作为此K线柱的id")
    private Long ts;

    /** 交易次数 */
    @Excel(name = "交易次数")
    private Integer count;

    /** 以基础币种计量的交易量 */
    @Excel(name = "以基础币种计量的交易量")
    private BigDecimal amount;

    /** 本阶段开盘价 */
    @Excel(name = "本阶段开盘价")
    private BigDecimal open;

    /** 本阶段收盘价 */
    @Excel(name = "本阶段收盘价")
    private BigDecimal close;

    /** 本阶段最低价 */
    @Excel(name = "本阶段最低价")
    private BigDecimal low;

    /** 本阶段最高价 */
    @Excel(name = "本阶段最高价")
    private BigDecimal high;

    /** 以报价币种计量的交易量 */
    @Excel(name = "以报价币种计量的交易量")
    private BigDecimal vol;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
