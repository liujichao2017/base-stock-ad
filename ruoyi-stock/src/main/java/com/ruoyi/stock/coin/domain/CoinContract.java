package com.ruoyi.stock.coin.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Sensitive;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 虚拟币合约对象 coin_contract
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinContract implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 合约委托ID */
    @Excel(name = "合约委托ID")
    private Long contractDelegationId;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long memberId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 币名 */
    @Excel(name = "币名")
    private String bcdn;

    /** 计价币名 */
    @Excel(name = "计价币名")
    private String qcdn;

    /** 买入价 */
    @Excel(name = "买入价")
    private BigDecimal buyAmt;

    /** 买入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "买入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyTime;

    /** 卖出价 */
    @Excel(name = "卖出价")
    private BigDecimal sellAmt;

    /** 卖出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "卖出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sellTime;

    /** 币数 */
    @Excel(name = "币数")
    private BigDecimal coinNum;

    /** 张数 */
    @Excel(name = "张数")
    private Long sheetNum;

    /** 杠杆 */
    @Excel(name = "杠杆")
    private Long level;

    /** 方向 */
    @Excel(name = "方向")
    private Long direction;

    /** 总价 */
    @Excel(name = "总价")
    private BigDecimal totalAmt;

    /** 保证金 */
    @Excel(name = "保证金")
    private BigDecimal marginAmt;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal feeAmt;

    /** 盈利资金 */
    @Excel(name = "盈利资金")
    private BigDecimal profitAmt;

    /** 止盈价格 */
    @Excel(name = "止盈价格")
    private BigDecimal stopProfitAmt;

    /** 止损价格 */
    @Excel(name = "止损价格")
    private BigDecimal stopLossAmt;

    /** 强制平仓 */
    @Excel(name = "强制平仓")
    private BigDecimal forcedSellAmt;

    /** 交割交割ID */
    @Excel(name = "交割交割ID")
    private Long deliveryId;

    /** 交割时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "交割时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
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
