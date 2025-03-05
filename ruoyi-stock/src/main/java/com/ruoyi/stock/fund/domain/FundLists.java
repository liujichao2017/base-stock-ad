package com.ruoyi.stock.fund.domain;

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

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * 基金产品对象 fund_lists
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundLists implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 基金名称 */
    @Excel(name = "基金名称")
    private String name;

    /** 基金代码 */
    @Excel(name = "基金代码")
    private String code;

    /** 类型: 1: 定投, 2: 日投日返 */
    @Excel(name = "类型: 1: 定投, 2: 日投日返")
    private String type;

    /** 最低购买金额 */
    @Excel(name = "最低购买金额")
    private BigDecimal minAmt;

    /** 最高购买金额 */
    @Excel(name = "最高购买金额")
    private BigDecimal maxAmt;

    /** 开始购买时间: 09:00 */
    @Excel(name = "开始购买时间: 09:00")
    private String startBuyTime;

    /** 购买结束时间 15:30 */
    @Excel(name = "购买结束时间 15:30")
    private String endBuyTime;

    /** 利率(%) */
    @Excel(name = "利率(%)")
    @NotNull
    private BigDecimal interestRate;

    /** 周期 */
    @Excel(name = "周期")
    @NotNull
    private Long cycle;

    /** 存放展示性字段 */
    @Excel(name = "存放展示性字段")
    private String extra;

    /** 上架状态 see YNEnum, 0: 否. 1: 是  */
    @Excel(name = "上架状态 see YNEnum, 0: 否. 1: 是 ")
    private Integer status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
