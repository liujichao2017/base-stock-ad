package com.ruoyi.stock.borrow.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 存股借卷配置对象 borrow_coupons
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowCoupons implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 股票名称 */
    @Excel(name = "股票名称")
    private String stockName;

    /** 股票代码 */
    @Excel(name = "股票代码")
    private String stockCode;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 最低购买额度 */
    @Excel(name = "最低购买额度")
    private BigDecimal minBuyAmt;

    /** 总获利率(%) */
    @Excel(name = "总获利率(%)")
    private BigDecimal rebate;

    /** 周期天数 */
    @Excel(name = "周期天数")
    private Integer cycle;

    /** 市场 */
    @Excel(name = "市场")
    private Long marketId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 需求张数 */
    @Excel(name = "需求张数")
    private Long needNumber;

    /** 已借张数 */
    @Excel(name = "已借张数")
    private Long borrowNumber;

    /** 股数/张 */
    @Excel(name = "股数/张")
    private Long num;

    /** 上架 */
    @Excel(name = "上架")
    private String status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
