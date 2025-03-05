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
 * A股数据同步对象 stock_his
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHis implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** GID */
    @Excel(name = "GID")
    private String stockFullCode;

    /** 最新价 last */
    @Excel(name = "最新价 last")
    private BigDecimal zuix;

    /** 昨收 close */
    @Excel(name = "昨收 close")
    private BigDecimal zuos;

    /** 涨跌幅 chg_pct */
    @Excel(name = "涨跌幅 chg_pct")
    private BigDecimal zdf;

    /** 总涨跌 chg */
    @Excel(name = "总涨跌 chg")
    private BigDecimal zzd;

    /** 今开 open */
    @Excel(name = "今开 open")
    private BigDecimal jk;

    /** 最高价 high */
    @Excel(name = "最高价 high")
    private BigDecimal zg;

    /** 最低价 low */
    @Excel(name = "最低价 low")
    private BigDecimal zd;

    /** 涨停 */
    @Excel(name = "涨停")
    private BigDecimal zt;

    /** 成交量 volume */
    @Excel(name = "成交量 volume")
    private BigDecimal cjl;

    /** 成交额 amounts */
    @Excel(name = "成交额 amounts")
    private BigDecimal cje;

    /** Gu代号*/
    @Excel(name = "Gu代号")
    private String stockCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String buy;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sell;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String body;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
