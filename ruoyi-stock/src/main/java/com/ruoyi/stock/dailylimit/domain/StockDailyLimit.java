package com.ruoyi.stock.dailylimit.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 涨停列表对象 stock_daily_limit
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDailyLimit implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 股票gid */
    @Excel(name = "股票gid")
    private String stockGid;

    /** 抢筹价格 */
    @Excel(name = "抢筹价格")
    private BigDecimal price;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private String status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



    @TableField(exist = false)
    private BigDecimal last;


    @TableField(exist = false)
    private BigDecimal chgPct;


    @TableField(exist = false)
    private String stockName;


    @TableField(exist = false)
    private String stockCode;
}
