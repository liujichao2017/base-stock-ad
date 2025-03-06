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
 * 用户提现对象 member_withdraw
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberWithdraw implements Serializable {
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
     * 市场ID
     */
    @Excel(name = "市场ID")
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
    private String status;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String orderDesc;

    @Excel(name = "账号类型")
    private String accountType;


    //新增字段货币类型
    @Excel(name = "货币类型")
    private String currencyType;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;

    /**
     * 预留字段,根据项目需要使用: 银行名称
     */
    @Excel(name = "预留字段,根据项目需要使用: 银行名称")
    private String bank1;

    /**
     * 预留字段,根据项目需要使用: 银行卡号
     */
    @Excel(name = "预留字段,根据项目需要使用: 银行卡号")
    private String bank2;

    /**
     * 预留字段,根据项目需要使用: 银行代码
     */
    @Excel(name = "预留字段,根据项目需要使用: 银行代码")
    private String bank3;

    /**
     * 预留字段,根据项目需要使用: 开户人名称
     */
    @Excel(name = "预留字段,根据项目需要使用: 开户人名称")
    private String bank4;

    /**
     * 预留字段,根据项目需要使用
     */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank5;

    /**
     * 预留字段,根据项目需要使用
     */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank6;

    /**
     * 预留字段,根据项目需要使用
     */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank7;

    /**
     * 预留字段,根据项目需要使用
     */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank8;


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

//    @TableField(exist = false)
//    private String accountType;

    @TableField(exist = false)
    private String memberRealName;

    @TableField(exist = false)
    @Sensitive
    private String memberPhone;
}
