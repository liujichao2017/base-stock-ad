package com.ruoyi.stock.loan.domain;

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
 * 用户贷款申请对象 member_loan_record
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoanRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    private Long marketId;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 借款金额
     */
    @Excel(name = "借款金额")
    private BigDecimal loanAmount;

    /**
     * 日利率
     */
    @Excel(name = "日利率")
    private BigDecimal interestRate;

    /**
     * 通过金额
     */
    @Excel(name = "通过金额")
    private BigDecimal passAmount;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /**
     * 通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "通过时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date passTime;

    /**
     * 状态：1 申请中，2 已通过，3  未通过，4 已结清，5 申请还款
     */
    @Excel(name = "状态：1 申请中，2 已通过，3  未通过，4 已结清，5 申请还款")
    private Integer status;

    /**
     * 还款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "还款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repaymentTime;

    /**
     * 拒绝理由
     */
    @Excel(name = "拒绝理由")
    private String rejectContent;

    @Excel(name = "手续费")
    private BigDecimal feeAmt;

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
