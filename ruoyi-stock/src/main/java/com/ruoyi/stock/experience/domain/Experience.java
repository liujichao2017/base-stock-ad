package com.ruoyi.stock.experience.domain;

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
 * 体验金对象 experience
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID
     */
    @Excel(name = "客户ID")
    private Long memberId;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    private Long marketId;

    /**
     * 体验金
     */
    @Excel(name = "体验金")
    private BigDecimal amt;

    /**
     * 已使用金额
     */
    @Excel(name = "已使用金额")
    private BigDecimal useAmt;

    /**
     * 使用次数
     */
    @Excel(name = "使用次数")
    private Long count;

    /**
     * 已使用次数
     */
    @Excel(name = "已使用次数")
    private Long useCount;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;


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


    /**
     * 需要充值
     */
    @Excel(name = "需要充值")
    private BigDecimal needRechargeAmt;


    /**
     * 已充值
     */
    @Excel(name = "已充值")
    private BigDecimal rechargeAmt;


    /**
     * 锁定状态: 1:锁定,0:未锁定
     */
    @Excel(name = "锁定状态: 1:锁定,0:未锁定")
    private String lockStatus;

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
