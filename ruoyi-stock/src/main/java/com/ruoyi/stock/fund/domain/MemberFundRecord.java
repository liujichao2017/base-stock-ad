package com.ruoyi.stock.fund.domain;

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
 * 基金购买记录对象 member_fund_record
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFundRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    private Long marketId;

    /**
     * 基金ID
     */
    @Excel(name = "基金ID")
    private Long fundId;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 购买金额
     */
    @Excel(name = "购买金额")
    private BigDecimal amt;

    /**
     * 状态: 1 待审核，2 通过，3 拒绝,4 赎回
     */
    @Excel(name = "状态: 1 待审核，2 通过，3 拒绝,4 赎回")
    private String status;

    /**
     * 通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "通过时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date passTime;


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
    private String fundName;

    @TableField(exist = false)
    private String fundCode;

    @TableField(exist = false)
    private String fundType;

    @TableField(exist = false)
    private BigDecimal income;


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
