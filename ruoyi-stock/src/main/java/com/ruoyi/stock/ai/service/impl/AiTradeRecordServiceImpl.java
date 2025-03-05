package com.ruoyi.stock.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import com.ruoyi.stock.ai.mapper.AiTradeRecordMapper;
import com.ruoyi.stock.ai.service.IAiTradeRecordService;
import com.ruoyi.stock.ai.vo.AiTradeVo;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.mapper.MemberPositionMapper;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI交易记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class AiTradeRecordServiceImpl implements IAiTradeRecordService {
    @Autowired
    private AiTradeRecordMapper aiTradeRecordMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private IStockService stockService;
    @Autowired
    private MemberPositionMapper memberPositionMapper;
    @Autowired
    private IExperienceService experienceService;

    /**
     * 查询AI交易记录
     *
     * @param id AI交易记录主键
     * @return AI交易记录
     */
    @Override
    public AiTradeRecord selectAiTradeRecordById(Long id) {
        return aiTradeRecordMapper.selectById(id);
    }

    /**
     * 查询AI交易记录列表
     *
     * @param aiTradeRecord AI交易记录
     * @return AI交易记录
     */
    @Override
    public List<AiTradeRecord> selectAiTradeRecordList(AiTradeRecord aiTradeRecord) {
        QueryWrapper<AiTradeRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(aiTradeRecord.getMemberId()), "b.member_id", aiTradeRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(aiTradeRecord.getMemberPhone()), "m.phone", aiTradeRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(aiTradeRecord.getUserId()), "u.user_id", aiTradeRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(aiTradeRecord.getMarketId()), "b.market_id", aiTradeRecord.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.like(LogicUtils.isNotNull(aiTradeRecord.getName()), "b.name", aiTradeRecord.getName());
        ew.eq(LogicUtils.isNotNull(aiTradeRecord.getStatus()), "b.status", aiTradeRecord.getStatus());
        return aiTradeRecordMapper.selectByList(ew);
    }

    /**
     * 新增AI交易记录
     *
     * @param aiTradeRecord AI交易记录
     * @return 结果
     */
    @Override
    public int insertAiTradeRecord(AiTradeRecord aiTradeRecord) {
        aiTradeRecord.setCreateTime(DateUtils.getNowDate());
        return aiTradeRecordMapper.insert(aiTradeRecord);
    }

    /**
     * 修改AI交易记录
     *
     * @param aiTradeRecord AI交易记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAiTradeRecord(AiTradeRecord body) {
        AiTradeRecord aiTradeRecord = selectAiTradeRecordById(body.getId());
        LogicUtils.assertNotNull(aiTradeRecord, "记录不存在");

        aiTradeRecord.setStatus(body.getStatus());
        aiTradeRecord.setUpdateTime(DateUtils.getNowDate());

        if (LogicUtils.isEquals("2", aiTradeRecord.getStatus())) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.AI);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.BUY);
            fundsOperateVo.setMemberId(aiTradeRecord.getMemberId());
            fundsOperateVo.setMarketId(aiTradeRecord.getMarketId());
            fundsOperateVo.setAmt(aiTradeRecord.getBuyAmt());
            fundsOperateVo.setSourceId(aiTradeRecord.getId());
            fundsOperateVo.setName(aiTradeRecord.getName());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.PRODUCT_NAME, aiTradeRecord.getName());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.APPLY_AMT, aiTradeRecord.getBuyAmt());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.PASS_AMT, aiTradeRecord.getBuyAmt());
            fundsOperateVo.build();
            memberFundsService.freezeToOccupancyAmt(fundsOperateVo);
        }
        if (LogicUtils.isEquals("3", aiTradeRecord.getStatus())) {
            FundsOperateVo fundsOperateVo1 = new FundsOperateVo();
            fundsOperateVo1.setSource(FundsSourceEnum.AI);
            fundsOperateVo1.setOperateType(FundsOperateTypeEnum.ROLLBACK);
            fundsOperateVo1.setMemberId(aiTradeRecord.getMemberId());
            fundsOperateVo1.setMarketId(aiTradeRecord.getMarketId());
            fundsOperateVo1.setAmt(aiTradeRecord.getBuyAmt());
            fundsOperateVo1.setSourceId(aiTradeRecord.getId());
            fundsOperateVo1.setName(aiTradeRecord.getName());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.PRODUCT_NAME, aiTradeRecord.getName());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.OCCUPANCY_AMT, aiTradeRecord.getBuyAmt());
            fundsOperateVo1.build();
            memberFundsService.subFreezeAmt(fundsOperateVo1);
        }
        if (LogicUtils.isEquals("4", aiTradeRecord.getStatus())) {
            if (aiTradeRecord.getIncomeAmt() != null && aiTradeRecord.getIncomeAmt().compareTo(BigDecimal.ZERO) != 0) {
                FundsOperateVo fundsOperateVo2 = new FundsOperateVo();
                fundsOperateVo2.setSource(FundsSourceEnum.AI);
                fundsOperateVo2.setOperateType(FundsOperateTypeEnum.PROFIT_LOSS);
                fundsOperateVo2.setMemberId(aiTradeRecord.getMemberId());
                fundsOperateVo2.setMarketId(aiTradeRecord.getMarketId());
                fundsOperateVo2.setAmt(aiTradeRecord.getIncomeAmt());
                fundsOperateVo2.setSourceId(aiTradeRecord.getId());
                fundsOperateVo2.setName(aiTradeRecord.getName());
                fundsOperateVo2.setVisible(YNEnum.NO.getType());

                memberFundsService.addProfitAmt(fundsOperateVo2);
            }

            FundsOperateVo fundsOperateVo1 = new FundsOperateVo();
            fundsOperateVo1.setSource(FundsSourceEnum.AI);
            fundsOperateVo1.setOperateType(FundsOperateTypeEnum.SELL);
            fundsOperateVo1.setMemberId(aiTradeRecord.getMemberId());
            fundsOperateVo1.setMarketId(aiTradeRecord.getMarketId());
            fundsOperateVo1.setAmt(aiTradeRecord.getBuyAmt());
            fundsOperateVo1.setSourceId(aiTradeRecord.getId());
            fundsOperateVo1.setName(aiTradeRecord.getName());

            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.PRODUCT_NAME, aiTradeRecord.getName());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.OCCUPANCY_AMT, aiTradeRecord.getBuyAmt());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.PROFIT_LOSS_AMT, aiTradeRecord.getIncomeAmt());
            fundsOperateVo1.build();
            memberFundsService.subOccupancyAmt(fundsOperateVo1);

        }
        return aiTradeRecordMapper.updateById(aiTradeRecord);
    }

    /**
     * 批量删除AI交易记录
     *
     * @param ids 需要删除的AI交易记录主键
     * @return 结果
     */
    @Override
    public int deleteAiTradeRecordByIds(List<Long> ids) {
        QueryWrapper<AiTradeRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return aiTradeRecordMapper.delete(ew);
    }

    /**
     * 删除AI交易记录信息
     *
     * @param id AI交易记录主键
     * @return 结果
     */
    @Override
    public int deleteAiTradeRecordById(Long id) {
        return aiTradeRecordMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean position(AiTradeVo body) {
        Map<String, Stock> stockMap = new HashMap<>();
        for (Long aiRecordId : body.getAiRecordIds()) {
            AiTradeRecord aiTradeRecord = aiTradeRecordMapper.selectById(aiRecordId);
            LogicUtils.assertNotNull(aiRecordId, "交易记录不存在");
            BigDecimal incomeAmt = aiTradeRecord.getIncomeAmt();
            incomeAmt = incomeAmt == null ? BigDecimal.ZERO : incomeAmt;
            for (AiTradeVo.StockInfo stockInfo : body.getStocks()) {
                Stock stock = stockMap.computeIfAbsent(stockInfo.getStockGid(), stockService::selectStock);
                LogicUtils.assertNotNull(aiRecordId, "股票GID不存在: " + stockInfo.getStockGid());
                BigDecimal buyAmt = stockInfo.getBuyAmt().multiply(BigDecimal.valueOf(stockInfo.getNum()));
                BigDecimal sellAmt = stockInfo.getSellAmt().multiply(BigDecimal.valueOf(stockInfo.getNum()));
                BigDecimal profitAndLose = sellAmt.subtract(buyAmt);
                incomeAmt = incomeAmt.add(profitAndLose);
                MemberPosition memberPosition = new MemberPosition();
                memberPosition.setPositionSn(LogicUtils.getOrderSn("S"));
                memberPosition.setMemberId(aiTradeRecord.getMemberId());
                memberPosition.setMarketId(stock.getMarketId());
                memberPosition.setStockName(stock.getName());
                memberPosition.setStockCode(stock.getCode());
                memberPosition.setStockGid(stock.getGid());
                memberPosition.setBuyOrderTime(stockInfo.getBuyTime());
                memberPosition.setBuyOrderPrice(stockInfo.getBuyAmt());
                memberPosition.setSellOrderTime(stockInfo.getSellTime());
                memberPosition.setSellOrderPrice(stockInfo.getSellAmt());
                memberPosition.setDirection("1");
                memberPosition.setNum(stockInfo.getNum());
                memberPosition.setLever(1L);
                memberPosition.setTotalAmt(buyAmt);
                memberPosition.setPrincipalAmt(buyAmt);
                memberPosition.setBuyFee(new BigDecimal("0"));
                memberPosition.setSellFee(new BigDecimal("0"));
                memberPosition.setOtherFee(new BigDecimal("0"));
                memberPosition.setProfitAndLose(profitAndLose);
                memberPosition.setAllProfitAndLose(profitAndLose);
                memberPosition.setIsLock("0");
                memberPosition.setSource(FundsSourceEnum.AI.getType());
                memberPosition.setSourceId(aiTradeRecord.getId());
                memberPositionMapper.insert(memberPosition);
            }
            aiTradeRecord.setIncomeAmt(incomeAmt);
            aiTradeRecordMapper.updateById(aiTradeRecord);
        }
        return false;
    }
}
