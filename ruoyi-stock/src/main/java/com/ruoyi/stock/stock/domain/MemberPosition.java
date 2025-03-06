package com.ruoyi.stock.stock.domain;

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
 * 用户持仓对象 member_position
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPosition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 持仓号
     */
    @Excel(name = "持仓号")
    private String positionSn;

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
     * 股票GID
     */
    @Excel(name = "股票GID")
    private String stockGid;

    /**
     * 购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "购买时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyOrderTime;

    /**
     * 买入价格
     */
    @Excel(name = "买入价格")
    private BigDecimal buyOrderPrice;

    /**
     * 卖出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "卖出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sellOrderTime;

    /**
     * 卖出价格
     */
    @Excel(name = "卖出价格")
    private BigDecimal sellOrderPrice;

    /**
     * 购买方向
     */
    @Excel(name = "购买方向")
    private String direction;

    /**
     * 买入数量
     */
    @Excel(name = "买入数量")
    private Long num;

    /**
     * 杠杆
     */
    @Excel(name = "杠杆")
    private Long lever;

    /**
     * 总金额
     */
    @Excel(name = "总金额")
    private BigDecimal totalAmt;

    /**
     * 本金
     */
    @Excel(name = "本金")
    private BigDecimal principalAmt;

    /**
     * 买入手续费
     */
    @Excel(name = "买入手续费")
    private BigDecimal buyFee;

    /**
     * 卖出手续费
     */
    @Excel(name = "卖出手续费")
    private BigDecimal sellFee;

    /**
     * 其他手续费
     */
    @Excel(name = "其他手续费")
    private BigDecimal otherFee;

    /**
     * 盈亏
     */
    @Excel(name = "盈亏")
    private BigDecimal profitAndLose;

    /**
     * 总盈亏
     */
    @Excel(name = "总盈亏")
    private BigDecimal allProfitAndLose;

    /**
     * 是否锁仓 see YNEnum, 0: 否. 1: 是
     */
    @Excel(name = "是否锁仓 see YNEnum, 0: 否. 1: 是 ")
    private String isLock;

    /**
     * 锁仓原因
     */
    @Excel(name = "锁仓原因")
    private String lockMsg;

    /**
     * 来源
     */
    @Excel(name = "来源")
    private String source;

    /**
     * 来源ID
     */
    @Excel(name = "来源ID")
    private Long sourceId;


    /**
     * 持仓类型  1普通持仓  2-量化交易
     */
    @Excel(name = "持仓类型  1普通持仓  2-量化交易")
    private String positionType;

    /**
     * 货币类型
     */
    @Excel(name = "货币类型")
    private String currencyType;

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
     * 平仓时间限制
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sellTimeLimit;


    @TableField(exist = false)
    private String state;

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
