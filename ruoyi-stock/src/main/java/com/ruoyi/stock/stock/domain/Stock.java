package com.ruoyi.stock.stock.domain;

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
 * 股票产品对象 stock
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 股票名称 */
    @Excel(name = "股票名称")
    private String name;

    /** 股票代码 */
    @Excel(name = "股票代码")
    private String code;

    /** 交易所 */
    @Excel(name = "交易所")
    private String exchanges;

    /** 股票GID */
    @Excel(name = "股票GID")
    private String gid;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 禁止交易 */
    @Excel(name = "禁止交易")
    private String isLock;

    /** 显示 */
    @Excel(name = "显示")
    private String isShow;

    /** 热门 */
    @Excel(name = "热门")
    private String isPopular;

    /** 最新价 */
    @Excel(name = "最新价")
    private BigDecimal last;

    /** 涨跌额 */
    @Excel(name = "涨跌额")
    private BigDecimal chg;

    /** 涨跌幅 */
    @Excel(name = "涨跌幅")
    private BigDecimal chgPct;

    /** 最高 */
    @Excel(name = "最高")
    private BigDecimal high;

    /** 最低 */
    @Excel(name = "最低")
    private BigDecimal low;

    /** 今开 */
    @Excel(name = "今开")
    private BigDecimal open;

    /** 昨收 */
    @Excel(name = "昨收")
    private BigDecimal close;

    /** 成交量 */
    @Excel(name = "成交量")
    private BigDecimal volume;

    /** 成交额 */
    @Excel(name = "成交额")
    private BigDecimal amounts;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
