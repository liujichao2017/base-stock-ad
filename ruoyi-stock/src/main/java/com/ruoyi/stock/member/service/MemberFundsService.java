package com.ruoyi.stock.member.service;

import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.stock.member.domain.MemberFunds;
import com.ruoyi.stock.member.vo.FundsOperateVo;

import java.util.List;

public interface MemberFundsService {

    /**
     * 增加可用资金
     *
     * @param fundsOperateVo 入参
     */
    void addEnableAmt(FundsOperateVo fundsOperateVo);

    /**
     * 减少可用资金
     *
     * @param fundsOperateVo 入参
     */
    void subEnableAmt(FundsOperateVo fundsOperateVo);

    /**
     * 增减占用资金, 减少可用资金
     *
     * @param fundsOperateVo 入参
     */
    void addOccupancyAmt(FundsOperateVo fundsOperateVo);

    /**
     * 减少占用资金, 增加可用资金
     *
     * @param fundsOperateVo 入参
     */
    void subOccupancyAmt(FundsOperateVo fundsOperateVo);

    /**
     * 增减冻结资金, 减少可用资金
     *
     * @param fundsOperateVo 入参
     */
    void addFreezeAmt(FundsOperateVo fundsOperateVo);

    /**
     * 减少冻结资金, 增加可用资金
     *
     * @param fundsOperateVo 入参
     */
    void subFreezeAmt(FundsOperateVo fundsOperateVo);

    void freezeToOccupancyAmt(FundsOperateVo fundsOperateVo);

    /**
     * 收益到账, 增加可用资金
     *
     * @param fundsOperateVo 入参
     */
    void addProfitAmt(FundsOperateVo fundsOperateVo);

    /**
     * 收益回退, 减少可用资金
     *
     * @param fundsOperateVo 入参
     */
    void subProfitAmt(FundsOperateVo fundsOperateVo);

    MemberFunds getFundsRecord(Market market, Member member);
    MemberFunds getFundsRecord(Long marketId, Long memberId);

    List<MemberFunds> getQuantificationFundsRecord(Long memberId);
}
