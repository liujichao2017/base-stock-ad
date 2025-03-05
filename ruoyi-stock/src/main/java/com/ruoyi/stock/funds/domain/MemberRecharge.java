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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户充值对象 member_recharge
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRecharge implements Serializable {
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
    @NotNull
    private Long memberId;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    @NotNull
    private Long marketId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderSn;

    /**
     * 金额
     */
    @Excel(name = "金额")
    @NotNull
    private BigDecimal amt;

    /**
     * 实际金额
     */
    @Excel(name = "实际金额")
    private BigDecimal actualAmt;

    /**
     * 手续费
     */
    @Excel(name = "手续费")
    private BigDecimal fee;

    /**
     * 状态: 0: 待审批, 1: 通过, 2: 拒绝
     */
    @Excel(name = "状态: 0: 待审批, 1: 通过, 2: 拒绝")
    @NotNull
    private String status;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String orderDesc;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;


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
