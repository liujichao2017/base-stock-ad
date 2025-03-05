package com.ruoyi.stock.coin.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 虚拟币合约委托对象 coin_contract_delegation
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinContractDelegation implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long memberId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 方向 */
    @Excel(name = "方向")
    private Long direction;

    /** 状态 */
    @Excel(name = "状态")
    private String priceType;

    /** 限价 */
    @Excel(name = "限价")
    private BigDecimal limitPrice;

    /** 交割类型 */
    @Excel(name = "交割类型")
    private String deliveryType;

    /** 交割配置配置 */
    @Excel(name = "交割配置配置")
    private Long deliveryId;

    /** 币数 */
    @Excel(name = "币数")
    private BigDecimal coinNum;

    /** 张数 */
    @Excel(name = "张数")
    private Long sheetNum;

    /** 杠杆 */
    @Excel(name = "杠杆")
    private Long level;

    /** 总价 */
    @Excel(name = "总价")
    private BigDecimal totalAmt;

    /** 保证金 */
    @Excel(name = "保证金")
    private BigDecimal marginAmt;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal feeAmt;

    /** 止盈价格 */
    @Excel(name = "止盈价格")
    private BigDecimal stopProfitAmt;

    /** 止损价格 */
    @Excel(name = "止损价格")
    private BigDecimal stopLossAmt;

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

    @TableField(exist = false)
    private String deliveryName;
}
