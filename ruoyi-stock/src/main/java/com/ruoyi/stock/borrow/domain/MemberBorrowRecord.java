package com.ruoyi.stock.borrow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借劵记录对象 member_borrow_record
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberBorrowRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 股票名称
     */
    @Excel(name = "股票名称")
    private String stockName;

    /**
     * 股票代码
     */
    @Excel(name = "股票代码")
    private String stockCode;

    /**
     * 客户id
     */
    @Excel(name = "客户id")
    private Long memberId;

    /**
     * 借劵ID
     */
    @Excel(name = "客户id")
    private Long bcId;

    /**
     * 股数/张
     */
    @Excel(name = "股数/张")
    private Long num;

    /**
     * 买入张数
     */
    @Excel(name = "买入张数")
    private Long buyNum;

    /**
     * 通过张数
     */
    @Excel(name = "通过张数")
    private Long applyNum;

    /**
     * 买入价格
     */
    @Excel(name = "买入价格")
    private BigDecimal buyPrice;

    /**
     * 买入总价
     */
    @Excel(name = "买入总价")
    private BigDecimal buyAmt;

    /**
     * 买入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "买入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyTime;

    /**
     * 卖出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "卖出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sellTime;

    /**
     * 周期
     */
    @Excel(name = "周期")
    private Integer cycle;

    /**
     * 周期结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "周期结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cycleTime;

    /**
     * 日利率(%)
     */
    @Excel(name = "日利率(%)")
    private BigDecimal rebate;

    /**
     * 总获利
     */
    @Excel(name = "总获利")
    private BigDecimal totalIncome;

    /**
     * 每天应该返利
     */
    @Excel(name = "每天应该返利")
    private BigDecimal dayIncome;

    /**
     * 到账收益
     */
    @Excel(name = "到账收益")
    private BigDecimal income;

    /**
     * 市场
     */
    @Excel(name = "市场")
    private Long marketId;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;


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


    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String accountType;

    @TableField(exist = false)
    private String memberRealName;

    @TableField(exist = false)
    @Sensitive
    private String memberPhone;
}
