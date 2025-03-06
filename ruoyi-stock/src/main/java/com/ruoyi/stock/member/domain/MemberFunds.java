package com.ruoyi.stock.member.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 会员资金对象 member_funds
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFunds implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 会员Id */
    @Excel(name = "会员Id")
    private Long memberId;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 可用余额 */
    @Excel(name = "可用余额")
    private BigDecimal enableAmt;

    /** 占用金额: 购买产品后, 正在盈利的本金 */
    @Excel(name = "占用金额: 购买产品后, 正在盈利的本金")
    private BigDecimal occupancyAmt;

    /** 冻结金额: 购买产品后, 等待审批的本金 */
    @Excel(name = "冻结金额: 购买产品后, 等待审批的本金")
    private BigDecimal freezeAmt;

    /** 盈利金额: 购买产品结算后, 获得的收益 */
    @Excel(name = "盈利金额: 购买产品结算后, 获得的收益")
    private BigDecimal profitAmt;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 货币类型 */
    @Excel(name = "货币类型")
    private String currencyType;

    /** 账号类型 */
    @Excel(name = "账号类型")
    private String accountType;
}
