package com.ruoyi.stock.fund.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 日收益对象 member_fund_interest_rate
 *
 * @author ruoyi
 * @date 2025-01-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundInterestRate implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 基金ID */
    @Excel(name = "基金ID")
    private Long fundId;

    /** 日利率 */
    @Excel(name = "日利率")
    private BigDecimal interestRate;

    /** 周期 */
    @Excel(name = "周期")
    private Long cycle;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
