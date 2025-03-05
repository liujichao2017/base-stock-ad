package com.ruoyi.stock.wite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.mapper.MarketMapper;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.service.IMemberPositionService;
import com.ruoyi.stock.stock.service.IStockService;
import com.ruoyi.stock.wite.domain.WithinDaysRecord;
import com.ruoyi.stock.wite.domain.dto.CreateWareHouseDto;
import com.ruoyi.stock.wite.domain.vo.WithinDaysRecordVo;
import com.ruoyi.stock.wite.mapper.WithinDaysRecordMapper;
import com.ruoyi.stock.wite.service.IWithinDaysRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * 用户日内交易记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class WithinDaysRecordServiceImpl implements IWithinDaysRecordService {
    @Autowired
    private WithinDaysRecordMapper withinDaysRecordMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private IMemberPositionService iMemberPositionService;
    @Autowired
    private IStockService stockService;

    /**
     * 查询用户日内交易记录
     *
     * @param id 用户日内交易记录主键
     * @return 用户日内交易记录
     */
    @Override
    public WithinDaysRecord selectWithinDaysRecordById(Long id) {
        return withinDaysRecordMapper.selectById(id);
    }

    /**
     * 查询用户日内交易记录列表
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 用户日内交易记录
     */
    @Override
    public List<WithinDaysRecordVo> selectWithinDaysRecordList(WithinDaysRecordVo withinDaysRecord) {
        QueryWrapper<WithinDaysRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(withinDaysRecord.getMemberId()), "b.member_id", withinDaysRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(withinDaysRecord.getMemberPhone()), "m.phone", withinDaysRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(withinDaysRecord.getUserId()), "u.user_id", withinDaysRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(withinDaysRecord.getMarketId()), "b.market_id", withinDaysRecord.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.eq(LogicUtils.isNotNull(withinDaysRecord.getStatus()), "b.status", withinDaysRecord.getStatus());
        return withinDaysRecordMapper.selectByList(ew);
    }

    /**
     * 新增用户日内交易记录
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 结果
     */
    @Override
    public int insertWithinDaysRecord(WithinDaysRecord withinDaysRecord) {
        withinDaysRecord.setCreateTime(DateUtils.getNowDate());
        return withinDaysRecordMapper.insert(withinDaysRecord);
    }

    /**
     * 修改用户日内交易记录
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateWithinDaysRecord(WithinDaysRecord withinDaysRecord) {
        if (withinDaysRecord.getStatus() == 4) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setMemberId(withinDaysRecord.getMemberId());
            fundsOperateVo.setSource(FundsSourceEnum.WITH_DAYS);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.ROLLBACK);
            fundsOperateVo.setMemberId(withinDaysRecord.getMemberId());
            fundsOperateVo.setMarketId(withinDaysRecord.getMarketId());
            fundsOperateVo.setAmt(withinDaysRecord.getAmt());
            fundsOperateVo.setSourceId(withinDaysRecord.getId());
            fundsOperateVo
                    .set(FundsOperateVo.FundsInfoKey.ROLLBACK_AMT, withinDaysRecord.getAmt())
                    .build();
            //扣冻结加可用
            memberFundsService.subFreezeAmt(fundsOperateVo);
        }
        withinDaysRecord.setUpdateTime(DateUtils.getNowDate());
        return withinDaysRecordMapper.updateById(withinDaysRecord);
    }

    /**
     * 批量删除用户日内交易记录
     *
     * @param ids 需要删除的用户日内交易记录主键
     * @return 结果
     */
    @Override
    public int deleteWithinDaysRecordByIds(List<Long> ids) {
        QueryWrapper<WithinDaysRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return withinDaysRecordMapper.delete(ew);
    }

    /**
     * 删除用户日内交易记录信息
     *
     * @param id 用户日内交易记录主键
     * @return 结果
     */
    @Override
    public int deleteWithinDaysRecordById(Long id) {
        return withinDaysRecordMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createHouse(CreateWareHouseDto createWareHouseDto) {
        WithinDaysRecord withinDaysRecord = withinDaysRecordMapper.selectById(createWareHouseDto.getWiteId());
        Market market = marketMapper.selectById(withinDaysRecord.getMarketId());
        LogicUtils.assertNotNull(market, "市场不存在");
        Stock stock = stockService.selectStock(createWareHouseDto.getStockGid());
        if (LogicUtils.isNull(stock)) stock = stockService.selectStock(createWareHouseDto.getStockGid());
        LogicUtils.assertFalse(!Objects.equals(stock.getMarketId(), withinDaysRecord.getMarketId()),
                "股票市场与当前日内市场不一致，请选择对应市场的股票");
        BigDecimal totalAmt = createWareHouseDto.getBuyOrderPrice()
                .multiply(BigDecimal.valueOf(createWareHouseDto.getNum()));
        BigDecimal principalAmt = totalAmt.divide(BigDecimal.valueOf(createWareHouseDto.getLever()), 2,
                RoundingMode.HALF_UP);
        LogicUtils.assertFalse(withinDaysRecord.getAmt().compareTo(principalAmt) < 0, "当前日内资金不足，建仓失败");

        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setMemberId(withinDaysRecord.getMemberId());
        fundsOperateVo.setSource(FundsSourceEnum.WITH_DAYS);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.UNFREEZE);
        fundsOperateVo.setMemberId(withinDaysRecord.getMemberId());
        fundsOperateVo.setMarketId(withinDaysRecord.getMarketId());
        fundsOperateVo.setAmt(withinDaysRecord.getAmt());
        fundsOperateVo.setSourceId(withinDaysRecord.getId());
        fundsOperateVo.setVisible(YNEnum.NO.getType());
        //扣冻结加可用
        memberFundsService.subFreezeAmt(fundsOperateVo);
        withinDaysRecord.setStatus(3);
        //建仓
        MemberPosition memberPosition = new MemberPosition();
        memberPosition.setMemberId(withinDaysRecord.getMemberId());
        memberPosition.setStockGid(createWareHouseDto.getStockGid());
        memberPosition.setBuyOrderTime(DateUtils.getNowDate());
        memberPosition.setLever(createWareHouseDto.getLever());
        memberPosition.setNum(createWareHouseDto.getNum());
        memberPosition.setDirection(createWareHouseDto.getDirection());
        memberPosition.setBuyOrderPrice(createWareHouseDto.getBuyOrderPrice());
        memberPosition.setSource(FundsSourceEnum.WITH_DAYS.getType());
        iMemberPositionService.insertMemberPosition(memberPosition);
        return withinDaysRecordMapper.updateById(withinDaysRecord);
    }
}
