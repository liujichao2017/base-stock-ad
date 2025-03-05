package com.ruoyi.stock.ai.domain;

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
 * AI交易记录对象 ai_trade_record
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiTradeRecord implements Serializable {
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
     * AI交易ID
     */
    @Excel(name = "AI交易ID")
    private Long aiTradeId;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;


    /**
     * AI交易名称
     */
    @Excel(name = "AI交易名称")
    private String name;

    /**
     * 交易成功率(%)
     */
    @Excel(name = "交易成功率(%)")
    private String tradeSucRate;

    /**
     * 预期收益(%)
     */
    @Excel(name = "预期收益(%)")
    private String futureEarningsRate;

    /**
     * 交易周期(天)
     */
    @Excel(name = "交易周期(天)")
    private String tradeCycle;

    /**
     * 买入金额
     */
    @Excel(name = "买入金额")
    private BigDecimal buyAmt;

    /**
     * 实际收益
     */
    @Excel(name = "实际收益")
    private BigDecimal incomeAmt;

    /**
     * 状态: 1: 申请中. 2. 申请通过(交易中) ,3.申请不通过, 4, 完成
     */
    @Excel(name = "状态: 1: 申请中. 2. 申请通过(交易中) ,3.申请不通过, 4, 完成")
    private String status;


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
