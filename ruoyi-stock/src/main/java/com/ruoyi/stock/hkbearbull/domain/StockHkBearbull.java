package com.ruoyi.stock.hkbearbull.domain;

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
 * 牛熊市股对象 stock_hk_bearbull
 *
 * @author ruoyi
 * @date 2024-12-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHkBearbull implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 股票代码 */
    @TableId
    private String sym;

    /** 股票名称 */
    @Excel(name = "股票名称")
    private String desp;

    /** 热门 */
    @Excel(name = "热门")
    private String rank;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 相关资产 */
    @Excel(name = "相关资产")
    private String udly;

    /** 发行 */
    @Excel(name = "发行")
    private String issuer;

    /** 现价 */
    @Excel(name = "现价")
    private String last;

    /** 跌涨额 */
    @Excel(name = "跌涨额")
    private String chg;

    /** 跌涨幅 */
    @Excel(name = "跌涨幅")
    private String pctchg;

    /** 成交额 */
    @Excel(name = "成交额")
    private String turn;

    /** 溢价 */
    @Excel(name = "溢价")
    private String premi;

    /** 行使价 */
    @Excel(name = "行使价")
    private String strike;

    /** 回收价 */
    @Excel(name = "回收价")
    private String calllv;

    /** 杠杆 */
    @Excel(name = "杠杆")
    private String gear;

    /** 换股比率 */
    @Excel(name = "换股比率")
    private String enratio;

    /** 街货(%) */
    @Excel(name = "街货(%)")
    private String pctout;

    /** 街货量 */
    @Excel(name = "街货量")
    private String outq;

    /** 最后交易日 */
    @Excel(name = "最后交易日")
    private String ldate;

    /** sign */
    @Excel(name = "sign")
    private String sign;

    /** mostatus */
    @Excel(name = "mostatus")
    private String mostatus;

    /** 历史最高 */
    @Excel(name = "历史最高")
    private String highlow;

    /** 价内/外 */
    @Excel(name = "价内/外")
    private String movalue;

    /** 每手股数 */
    @Excel(name = "每手股数")
    private String lots;

    /** 距回收价 */
    @Excel(name = "距回收价")
    private String spcall;

    /** 距回收价(%) */
    @Excel(name = "距回收价(%)")
    private String spcallpct;

    /** 剩余交易日 */
    @Excel(name = "剩余交易日")
    private String day;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
