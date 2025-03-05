package com.ruoyi.stock.member.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MemberFundsVo {

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 市场ID */
    @Excel(name = "市场名称")
    private String marketName;

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

    /** 体验金 */
    @Excel(name = "盈利金额: 购买产品结算后, 获得的收益")
    private BigDecimal experienceAmt;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
