package com.ruoyi.stock.dashboard.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CountResult {


    /**
     * 今日注册用户
     */
    private Long todayRegisterMemberNum = 0L;

    /**
     * 总注册数
     */
    private Long totalRegisterMemberNum = 0L;
    /**
     * 冻结用户数
     */
    private Long freezeMemberNum = 0L;

    /**
     * 充值用户数
     */
    private Long rechargeMemberNum = 0L;

    /**
     * 今日充值
     */
    private BigDecimal todayRechargeAmount = BigDecimal.ZERO;

    /**
     * 总充值
     */
    private BigDecimal totalRechargeAmount = BigDecimal.ZERO;
    /**
     * 今日提现数
     */
    private BigDecimal todayWithdrawalAmount = BigDecimal.ZERO;
    /**
     * 提现数
     */
    private BigDecimal totalWithdrawalAmount = BigDecimal.ZERO;
    /**
     * 可用
     */
    private BigDecimal totalEnableAmount = BigDecimal.ZERO;

    /**
     * 占用金额: 购买产品后, 正在盈利的本金
     */
    private BigDecimal totalOccupancyAmt = BigDecimal.ZERO;

    /**
     * 冻结金额: 购买产品后, 等待审批的本金
     */
    private BigDecimal totalFreezeAmt = BigDecimal.ZERO;

    /**
     * 盈利金额: 购买产品结算后, 获得的收益
     */
    private BigDecimal totalProfitAmt = BigDecimal.ZERO;
}
