package com.ruoyi.stock.member.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.funds.domain.MemberFundsLogs;
import com.ruoyi.stock.funds.mapper.MemberFundsLogsMapper;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.stock.member.domain.MemberFunds;
import com.ruoyi.stock.member.mapper.MemberFundsMapper;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class MemberFundsServiceImpl implements MemberFundsService {

    @Autowired
    private MemberFundsMapper memberFundsMapper;

    @Autowired
    private MemberFundsLogsMapper memberFundsLogsMapper;
    @Autowired
    private IExperienceService experienceService;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addEnableAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        MemberFunds memberFunds = getMemberFunds(memberId, marketId, fundsOperateVo.getAccountType(),fundsOperateVo.getCurrencyType());
        int count = 0;
        BigDecimal amt = fundsOperateVo.getAmt();
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.addEnableAmtByNegative(amt, memberFunds.getId());
        } else {
            count = memberFundsMapper.addEnableAmt(amt, memberFunds.getId());
        }
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt);
            logs.setEnableAmt(memberFunds.getEnableAmt().add(amt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void subEnableAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        MemberFunds memberFunds = getMemberFunds(memberId, marketId);
        BigDecimal amt = fundsOperateVo.getAmt();

        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.subEnableAmtByNegative(amt, memberFunds.getId());
        } else {
            count = memberFundsMapper.subEnableAmt(amt, memberFunds.getId());
        }
        LogicUtils.assertTrue(count == 1, "可用资金不足");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt.negate());
            logs.setEnableAmt(memberFunds.getEnableAmt().subtract(amt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addOccupancyAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId, fundsOperateVo.getAccountType(), fundsOperateVo.getCurrencyType());

        // 增加体验金逻辑
        BigDecimal experienceAmt = useExperienceAmt(fundsOperateVo);
        BigDecimal enableAmt = amt.subtract(experienceAmt);

        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.subEnableAmtByNegative(enableAmt, memberFunds.getId());
        } else {
            count = memberFundsMapper.subEnableAmt(enableAmt, memberFunds.getId());
        }
        LogicUtils.assertTrue(LogicUtils.isEquals(enableAmt, BigDecimal.ZERO) || count == 1, "可用资金不足");

        count = memberFundsMapper.addOccupancyAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt.negate());
            logs.setEnableAmt(memberFunds.getEnableAmt().subtract(enableAmt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt().add(amt));
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void subOccupancyAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId, fundsOperateVo.getAccountType(), fundsOperateVo.getCurrencyType());

        // 增加体验金逻辑
        BigDecimal experienceAmt = queryExperienceAmt(fundsOperateVo);
        BigDecimal enableAmt = amt.subtract(experienceAmt);
        if (fundsOperateVo.getOperateType() == FundsOperateTypeEnum.ROLLBACK) {
            rollbackExperienceAmt(fundsOperateVo);
        }
        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.addEnableAmtByNegative(enableAmt, memberFunds.getId());
        } else {
            count = memberFundsMapper.addEnableAmt(enableAmt, memberFunds.getId());
        }
        LogicUtils.assertTrue(LogicUtils.isEquals(enableAmt, BigDecimal.ZERO) || count == 1, "可用资金不足");

        count = memberFundsMapper.subOccupancyAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt);
            logs.setEnableAmt(memberFunds.getEnableAmt().add(enableAmt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt().subtract(amt));
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addFreezeAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId);

        // 增加体验金逻辑
        BigDecimal experienceAmt = useExperienceAmt(fundsOperateVo);
        BigDecimal enableAmt = amt.subtract(experienceAmt);

        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.subEnableAmtByNegative(enableAmt, memberFunds.getId());
        } else {
            count = memberFundsMapper.subEnableAmt(enableAmt, memberFunds.getId());
        }
        LogicUtils.assertTrue(LogicUtils.isEquals(enableAmt, BigDecimal.ZERO) || count == 1, "可用资金不足");

        count = memberFundsMapper.addFreezeAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt.negate());
            logs.setEnableAmt(memberFunds.getEnableAmt().subtract(enableAmt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt().add(amt));
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void subFreezeAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId);

        // 增加体验金逻辑
        BigDecimal experienceAmt = queryExperienceAmt(fundsOperateVo);
        BigDecimal enableAmt = amt.subtract(experienceAmt);
        if (fundsOperateVo.getOperateType() == FundsOperateTypeEnum.ROLLBACK
                || fundsOperateVo.getOperateType() == FundsOperateTypeEnum.UNFREEZE) {
            rollbackExperienceAmt(fundsOperateVo);
        }
        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.addEnableAmtByNegative(enableAmt, memberFunds.getId());
        } else {
            count = memberFundsMapper.addEnableAmt(enableAmt, memberFunds.getId());
        }
        LogicUtils.assertTrue(LogicUtils.isEquals(enableAmt, BigDecimal.ZERO) || count == 1, "可用资金不足");

        count = memberFundsMapper.subFreezeAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");


        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt);
            logs.setEnableAmt(memberFunds.getEnableAmt().add(enableAmt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt().subtract(amt));
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void freezeToOccupancyAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId);

        int count = memberFundsMapper.subFreezeAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        count = memberFundsMapper.addOccupancyAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt);
            logs.setEnableAmt(memberFunds.getEnableAmt());
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt().add(amt));
            logs.setFreezeAmt(memberFunds.getFreezeAmt().subtract(amt));
            logs.setProfitAmt(memberFunds.getProfitAmt());
        });
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addProfitAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId, fundsOperateVo.getAccountType(), fundsOperateVo.getCurrencyType());
        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.addEnableAmtByNegative(amt, memberFunds.getId());
        } else {
            count = memberFundsMapper.addEnableAmt(amt, memberFunds.getId());
        }
        LogicUtils.assertTrue(count == 1, "可用资金不足");

        count = memberFundsMapper.addProfitAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt);
            logs.setEnableAmt(memberFunds.getEnableAmt().add(amt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt().add(amt));
        });
    }

    @Override
    public void subProfitAmt(FundsOperateVo fundsOperateVo) {
        Long memberId = getMemberId(fundsOperateVo);
        Long marketId = getMarketId(fundsOperateVo);

        BigDecimal amt = fundsOperateVo.getAmt();
        MemberFunds memberFunds = getMemberFunds(memberId, marketId);
        int count = 0;
        if (fundsOperateVo.isNegative()) {
            count = memberFundsMapper.subEnableAmtByNegative(amt, memberFunds.getId());
        } else {
            count = memberFundsMapper.subEnableAmt(amt, memberFunds.getId());
        }
        LogicUtils.assertTrue(count == 1, "可用资金不足");

        count = memberFundsMapper.subProfitAmt(amt, memberFunds.getId());
        LogicUtils.assertTrue(count == 1, "数据异常");

        createMemberFundsLogs(fundsOperateVo, logs -> {
            logs.setAmt(amt.negate());
            logs.setEnableAmt(memberFunds.getEnableAmt().subtract(amt));
            logs.setOccupancyAmt(memberFunds.getOccupancyAmt());
            logs.setFreezeAmt(memberFunds.getFreezeAmt());
            logs.setProfitAmt(memberFunds.getProfitAmt().subtract(amt));
        });
    }

    @Override
    public MemberFunds getFundsRecord(Market market, Member member) {
        return getMemberFunds(member.getId(), market.getId());
    }

    @Override
    public MemberFunds getFundsRecord(Long marketId, Long memberId) {
        return getMemberFunds(memberId, marketId);
    }

    @Override
    public List<MemberFunds> getQuantificationFundsRecord(Long memberId) {
        QueryWrapper<MemberFunds> ew = new QueryWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("account_type", "QUANTIFICATION");
//        ew.eq("market_id", marketId);
//        ew.last("limit 1");
        return memberFundsMapper.selectList(ew);

    }

    private MemberFunds getMemberFunds(Long memberId, Long marketId, String accountType, String currencyType) {
        QueryWrapper<MemberFunds> ew = new QueryWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("market_id", marketId);
        if (LogicUtils.isNotBlank(accountType)) {
            ew.eq("account_type", accountType);
        }
        if (LogicUtils.isNotBlank(currencyType)) {
            ew.eq("currency_type", currencyType);
        }
        ew.last("limit 1");
        MemberFunds memberFunds = memberFundsMapper.selectOne(ew);
        if (LogicUtils.isNull(memberFunds)) {
            memberFunds = new MemberFunds();
            memberFunds.setMemberId(memberId);
            memberFunds.setMarketId(marketId);
            memberFunds.setEnableAmt(BigDecimal.ZERO);
            memberFunds.setFreezeAmt(BigDecimal.ZERO);
            memberFunds.setOccupancyAmt(BigDecimal.ZERO);
            memberFunds.setProfitAmt(BigDecimal.ZERO);
            memberFunds.setAccountType(accountType);
            memberFunds.setCurrencyType(currencyType);

            LogicUtils.assertTrue(memberFundsMapper.insert(memberFunds) == 1, "数据异常");
        }
        Map<Long, Map<Long, BigDecimal>> experienceMap = experienceService.queryEnableAmt(
                Collections.singletonList(memberId));
        memberFunds.setEnableAmt(memberFunds.getEnableAmt()
                                         .add(experienceMap.getOrDefault(memberId, Collections.emptyMap())
                                                      .getOrDefault(marketId, BigDecimal.ZERO)));
        return memberFunds;
    }

    // Overloaded method for backward compatibility
    private MemberFunds getMemberFunds(Long memberId, Long marketId) {
        return getMemberFunds(memberId, marketId, null, null);
    }

    private void createMemberFundsLogs(FundsOperateVo fundsOperateVo, Consumer<MemberFundsLogs> consumer) {
        Long memberId = fundsOperateVo.getMemberId();
        if (LogicUtils.isNull(memberId)) {
            memberId = fundsOperateVo.getMember().getId();
        }
        Long marketId = fundsOperateVo.getMarketId();
        if (LogicUtils.isNull(marketId)) {
            marketId = fundsOperateVo.getMarket().getId();
        }
        MemberFundsLogs memberFundsLogs = new MemberFundsLogs();
        memberFundsLogs.setMemberId(memberId);
        memberFundsLogs.setMarketId(marketId);
        memberFundsLogs.setVisible(
                LogicUtils.isNotBlank(fundsOperateVo.getVisible()) ?
                        fundsOperateVo.getVisible() : fundsOperateVo.getSource().getVisible());
        memberFundsLogs.setSource(fundsOperateVo.getSource().getType());
        memberFundsLogs.setSourceId(fundsOperateVo.getSourceId());
        memberFundsLogs.setOperateType(fundsOperateVo.getOperateType().getType());

        memberFundsLogs.setAccountType(fundsOperateVo.getAccountType());
        memberFundsLogs.setCurrencyType(fundsOperateVo.getCurrencyType());
        consumer.accept(memberFundsLogs);

        StringBuilder content = new StringBuilder();
        content.append(fundsOperateVo.getSource().getName()).append(" ");
        content.append(fundsOperateVo.getOperateType().getName()).append(" ");
        Map<String, String> contentMap = new HashMap<>();
        if (LogicUtils.isNotBlank(fundsOperateVo.getName())) {
            content.append("名称: ").append(fundsOperateVo.getName());
            contentMap.put(FundsOperateVo.FundsInfoKey.STOCK_NAME, fundsOperateVo.getName());
        }
        if (LogicUtils.isNotBlank(fundsOperateVo.getCode())) {
            content.append("代码: ").append(fundsOperateVo.getCode());
            contentMap.put(FundsOperateVo.FundsInfoKey.STOCK_NAME, fundsOperateVo.getCode());
        }
        memberFundsLogs.setContent(content.toString());
        memberFundsLogs.setContentJson(LogicUtils.isNotBlank(fundsOperateVo.getContentJson())
                                               ? fundsOperateVo.getContentJson() : JSONObject.toJSONString(contentMap));
        LogicUtils.assertTrue(memberFundsLogsMapper.insert(memberFundsLogs) == 1, "数据异常");
    }

    private static Long getMarketId(FundsOperateVo fundsOperateVo) {
        Long marketId = fundsOperateVo.getMarketId();
        if (LogicUtils.isNull(marketId)) {
            LogicUtils.assertNotNull(fundsOperateVo.getMarket(), "数据异常");
            marketId = fundsOperateVo.getMarket().getId();
        }
        return marketId;
    }

    private static Long getMemberId(FundsOperateVo fundsOperateVo) {
        Long memberId = fundsOperateVo.getMemberId();
        if (LogicUtils.isNull(memberId)) {
            LogicUtils.assertNotNull(fundsOperateVo.getMember(), "数据异常");
            memberId = fundsOperateVo.getMember().getId();
        }
        return memberId;
    }


    private BigDecimal queryExperienceAmt(FundsOperateVo fundsOperateVo) {
        if (LogicUtils.isNull(fundsOperateVo.getSource()) || LogicUtils.isNull(fundsOperateVo.getSourceId())) {
            return BigDecimal.ZERO;
        }

        return experienceService.queryUseAmt(fundsOperateVo.getSource(), fundsOperateVo.getSourceId());
    }

    private BigDecimal useExperienceAmt(FundsOperateVo fundsOperateVo) {
        if (LogicUtils.isNull(fundsOperateVo.getSource()) || LogicUtils.isNull(fundsOperateVo.getSourceId())) {
            return BigDecimal.ZERO;
        }

        Long memberId = fundsOperateVo.getMemberId();
        if (LogicUtils.isNull(memberId)) {
            memberId = fundsOperateVo.getMember().getId();
        }
        Long marketId = fundsOperateVo.getMarketId();
        if (LogicUtils.isNull(marketId)) {
            marketId = fundsOperateVo.getMarket().getId();
        }
        return experienceService.use(marketId, memberId, fundsOperateVo.getSource(), fundsOperateVo.getSourceId(),
                                     fundsOperateVo.getAmt());
    }


    private void rollbackExperienceAmt(FundsOperateVo fundsOperateVo) {
        if (LogicUtils.isNull(fundsOperateVo.getSource()) || LogicUtils.isNull(fundsOperateVo.getSourceId())) {
            return;
        }
        experienceService.rollback(fundsOperateVo.getSource(), fundsOperateVo.getSourceId());
    }
}
