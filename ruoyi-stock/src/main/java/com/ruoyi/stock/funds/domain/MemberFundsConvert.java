package com.ruoyi.stock.funds.domain;

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
 * 兑换记录对象 member_funds_convert
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFundsConvert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 转出市场
     */
    @Excel(name = "转出市场")
    private Long fromId;

    /**
     * 转入市场
     */
    @Excel(name = "转入市场")
    private Long toId;

    /**
     * 转出金额
     */
    @Excel(name = "转出金额")
    private BigDecimal fromAmt;

    /**
     * 转入金额
     */
    @Excel(name = "转入金额")
    private BigDecimal toAmt;

    /**
     * 汇率: 转出市场 : 转入市场, 韩对美0.00072, 美兑韩: 1382.28
     */
    @Excel(name = "汇率: 转出市场 : 转入市场, 韩对美0.00072, 美兑韩: 1382.28")
    private BigDecimal exchangeRate;


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
