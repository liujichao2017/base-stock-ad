package com.ruoyi.stock.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.stock.funds.mapper.MemberFundsLogsMapper;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.mapper.MarketMapper;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.stock.member.mapper.MemberMapper;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.reset.AppApi;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.mapper.MemberPositionMapper;
import com.ruoyi.stock.stock.service.IMemberPositionService;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户持仓Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Service
public class MemberPositionServiceImpl implements IMemberPositionService {
    @Autowired
    private MemberPositionMapper memberPositionMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IStockService stockService;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private MemberFundsLogsMapper memberFundsLogsMapper;

    /**
     * 查询用户持仓
     *
     * @param id 用户持仓主键
     * @return 用户持仓
     */
    @Override
    public MemberPosition selectMemberPositionById(Long id) {
        return memberPositionMapper.selectById(id);
    }

    /**
     * 查询用户持仓列表
     *
     * @param memberPosition 用户持仓
     * @return 用户持仓
     */
    @Override
    public List<MemberPosition> selectMemberPositionList(MemberPosition memberPosition) {
        QueryWrapper<MemberPosition> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(memberPosition.getMemberId()), "b.member_id", memberPosition.getMemberId());
        ew.like(LogicUtils.isNotNull(memberPosition.getMemberPhone()), "m.phone", memberPosition.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(memberPosition.getUserId()), "u.user_id", memberPosition.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");

        ew.isNull("1".equals(memberPosition.getState()), "b.sell_order_time");
        ew.isNotNull("0".equals(memberPosition.getState()), "b.sell_order_time");
        ew.like(LogicUtils.isNotNull(memberPosition.getPositionSn()), "b.position_sn", memberPosition.getPositionSn());
        ew.eq(LogicUtils.isNotNull(memberPosition.getMarketId()), "b.market_id", memberPosition.getMarketId());
        ew.like(LogicUtils.isNotNull(memberPosition.getStockName()), "b.stock_name", memberPosition.getStockName());
        ew.like(LogicUtils.isNotNull(memberPosition.getStockCode()), "b.stock_code", memberPosition.getStockCode());
        ew.like(LogicUtils.isNotNull(memberPosition.getStockGid()), "b.stock_gid", memberPosition.getStockGid());
        ew.like(LogicUtils.isNotNull(memberPosition.getIsLock()), "b.is_lock", memberPosition.getIsLock());
        ew.like(LogicUtils.isNotNull(memberPosition.getSource()), "b.source", memberPosition.getSource());
        ew.like(LogicUtils.isNotNull(memberPosition.getSourceId()), "b.sourceId", memberPosition.getSourceId());
        ew.orderByDesc("b.create_time");
        List<MemberPosition> memberPositions = memberPositionMapper.selectByList(ew);
        Map<Long, Market> marketMap = new HashMap<>();
        for (MemberPosition position : memberPositions) {
            if (LogicUtils.isNull(position.getSellOrderPrice())) {
                Stock stock = stockService.selectStock(position.getStockGid());
                if (LogicUtils.isNull(stock)) {
                    continue;
                }
                position.setSellOrderPrice(stock.getLast());
                Market market = marketMap.computeIfAbsent(position.getMarketId(), marketMapper::selectById);
                if (LogicUtils.isNull(market)) {
                    continue;
                }
                setProfitAndLose(market, position, stock.getLast());
            }

        }
        return memberPositions;
    }

    /**
     * 新增用户持仓
     *
     * @param body 用户持仓
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMemberPosition(MemberPosition body) {
        Member member = memberMapper.selectById(body.getMemberId());
        LogicUtils.assertNotNull(member, "用户不存在");

        Stock stock = stockService.selectStock(body.getStockGid());
        if (LogicUtils.isNull(stock)) stock = stockService.selectStock(body.getStockGid());
        LogicUtils.assertNotNull(stock, "股票不存在");


        Market market = marketMapper.selectById(stock.getMarketId());
        LogicUtils.assertNotNull(market, "市场不存在");

        BigDecimal totalAmt = body.getBuyOrderPrice().multiply(BigDecimal.valueOf(body.getNum()));
        BigDecimal principalAmt = totalAmt.divide(BigDecimal.valueOf(body.getLever()), 2, RoundingMode.HALF_UP);

        MemberPosition memberPosition = createPosition(body, member, market, stock, totalAmt, principalAmt);

        LogicUtils.assertTrue(memberPositionMapper.insert(memberPosition) == 1, "数据异常");

        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(
                StringUtils.isEmpty(body.getSource()) ? FundsSourceEnum.STOCK : FundsSourceEnum.getEnum(
                        body.getSource()));
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.BUY);
        fundsOperateVo.setMemberId(member.getId());
        fundsOperateVo.setMarketId(market.getId());
        fundsOperateVo.setAmt(principalAmt);
        fundsOperateVo.setSourceId(stock.getId());
        fundsOperateVo.setName(stock.getName());
        fundsOperateVo.setCode(stock.getCode());

        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, stock.getName());
        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_CODE, stock.getCode());
        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.OCCUPANCY_AMT, principalAmt);
        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.FEE_AMT, memberPosition.getAllProfitAndLose());

        fundsOperateVo.setAccountType(memberPosition.getPositionType());
        fundsOperateVo.setCurrencyType(memberPosition.getCurrencyType());

        fundsOperateVo.build();

        memberFundsService.addOccupancyAmt(fundsOperateVo);
        return 1;
    }

    /**
     * 修改用户持仓
     *
     * @param memberPosition 用户持仓
     * @return 结果
     */
    @Override
    public int updateMemberPosition(MemberPosition memberPosition) {
        memberPosition.setUpdateTime(DateUtils.getNowDate());
        int i = memberPositionMapper.updateById(memberPosition);
        if (memberPosition.getSellTimeLimit() == null) {
            memberPositionMapper.deleteSellTimeLimit(memberPosition.getId());
        }
        return i;
    }

    /**
     * 批量删除用户持仓
     *
     * @param ids 需要删除的用户持仓主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMemberPositionByIds(List<Long> ids) {
        for (Long id : ids) {
            deleteMemberPositionById(id);
        }
        return ids.size();
    }

    /**
     * 删除用户持仓信息
     *
     * @param id 用户持仓主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMemberPositionById(Long id) {
        MemberPosition memberPosition = selectMemberPositionById(id);
        LogicUtils.assertNotNull(memberPosition, "持仓记录不存在");
        LogicUtils.assertNull(memberPosition.getSellOrderTime(), "平仓记录无法删除");
        // 退还本金
        BigDecimal principalAmt = memberPosition.getPrincipalAmt();
        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.STOCK);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.ROLLBACK);
        fundsOperateVo.setMemberId(memberPosition.getMemberId());
        fundsOperateVo.setMarketId(memberPosition.getMarketId());
        fundsOperateVo.setAmt(principalAmt);
        fundsOperateVo.setSourceId(memberPosition.getId());
        fundsOperateVo.setName(memberPosition.getStockName());
        fundsOperateVo.setCode(memberPosition.getStockCode());
        fundsOperateVo.setVisible("0");
        memberFundsService.subOccupancyAmt(fundsOperateVo);

        // 将购买的记录改为不可见
        LogicUtils.assertEquals(1,
                memberFundsLogsMapper.updateVisible(FundsSourceEnum.STOCK.getType(), FundsOperateTypeEnum.BUY.getType(),
                        memberPosition.getId()), "操作异常");
        // 将购买的记录改为不可见
        LogicUtils.assertEquals(1, memberPositionMapper.deleteById(id), "操作异常");
        // 删除持仓记录
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sell(MemberPosition body) {
        MemberPosition memberPosition = memberPositionMapper.selectById(body.getId());
        LogicUtils.assertNotNull(memberPosition, "记录不存在");
        LogicUtils.assertTrue(memberPosition.getSellOrderTime() == null, "已平仓");

        Market market = marketMapper.selectById(memberPosition.getMarketId());
        LogicUtils.assertNotNull(market, "市场不存在");
        Stock stock = stockService.selectStock(memberPosition.getStockGid());
        LogicUtils.assertNotNull(stock, "股票不存在");

        // 最新的股票价格
        BigDecimal last = body.getSellOrderPrice();
        if (LogicUtils.isNull(last)) {
            stock = AppApi.getStockByLast(stock.getId());
            last = stock.getLast();
        }

        // 设置费用
        setProfitAndLose(market, memberPosition, last);
        memberPosition.setSellOrderTime(new Date());

        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.STOCK);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.SELL);
        fundsOperateVo.setMemberId(memberPosition.getMemberId());
        fundsOperateVo.setAmt(memberPosition.getPrincipalAmt());
        fundsOperateVo.setMarketId(market.getId());
        fundsOperateVo.setSourceId(memberPosition.getId());
        fundsOperateVo.setName(memberPosition.getStockName());
        fundsOperateVo.setCode(memberPosition.getStockCode());

        fundsOperateVo.setCurrencyType(memberPosition.getCurrencyType());
        fundsOperateVo.setAccountType(memberPosition.getAccountType());

        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, memberPosition.getStockName())
                .set(FundsOperateVo.FundsInfoKey.STOCK_CODE, memberPosition.getStockCode())
                .set(FundsOperateVo.FundsInfoKey.OCCUPANCY_AMT, memberPosition.getPrincipalAmt())
                .set(FundsOperateVo.FundsInfoKey.PROFIT_LOSS_AMT, memberPosition.getProfitAndLose())
                .set(FundsOperateVo.FundsInfoKey.ALL_PROFIT_LOSS_AMT, memberPosition.getAllProfitAndLose())
                .set(FundsOperateVo.FundsInfoKey.FEE_AMT, memberPosition.getProfitAndLose().subtract(memberPosition.getAllProfitAndLose()))
                .build();

        memberFundsService.subOccupancyAmt(fundsOperateVo);

        fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.STOCK);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.PROFIT_LOSS);
        fundsOperateVo.setMemberId(memberPosition.getMemberId());
        fundsOperateVo.setAmt(memberPosition.getAllProfitAndLose());
        fundsOperateVo.setMarketId(market.getId());
        fundsOperateVo.setSourceId(memberPosition.getId());
        fundsOperateVo.setName(memberPosition.getStockName());
        fundsOperateVo.setCode(memberPosition.getStockCode());
        fundsOperateVo.setVisible(YNEnum.NO.getType());
        fundsOperateVo.setCurrencyType(memberPosition.getCurrencyType());
        fundsOperateVo.setAccountType(memberPosition.getAccountType());

        memberFundsService.addProfitAmt(fundsOperateVo);

        QueryWrapper<MemberPosition> ew = new QueryWrapper<>();
        ew.eq("id", memberPosition.getId());
        ew.isNull("sell_order_time");
        LogicUtils.assertTrue(memberPositionMapper.update(memberPosition, ew) == 1, "平仓失败");
        return true;
    }

    private static MemberPosition createPosition(MemberPosition body, Member member, Market market,
                                                 Stock stock, BigDecimal totalAmt, BigDecimal principalAmt) {
        MemberPosition memberPosition = new MemberPosition();
        memberPosition.setPositionSn(LogicUtils.getOrderSn("S"));
        memberPosition.setMemberId(member.getId());
        memberPosition.setMarketId(market.getId());
        memberPosition.setStockName(stock.getName());
        memberPosition.setStockCode(stock.getCode());
        memberPosition.setStockGid(stock.getGid());
        memberPosition.setBuyOrderTime(body.getBuyOrderTime());
        memberPosition.setBuyOrderPrice(body.getBuyOrderPrice());
        memberPosition.setDirection(body.getDirection());
        memberPosition.setNum(body.getNum());
        memberPosition.setLever(body.getLever());
        memberPosition.setTotalAmt(totalAmt);
        memberPosition.setPrincipalAmt(principalAmt);
        memberPosition.setSellTimeLimit(body.getSellTimeLimit());

        memberPosition.setAccountType(body.getAccountType());
        memberPosition.setCurrencyType(body.getCurrencyType());

        BigDecimal totalFee = BigDecimal.ZERO;
        BigDecimal buyFee = market.getBuyFee();
        buyFee = totalAmt.multiply(buyFee);
        buyFee = buyFee.setScale(2, 4);
        totalFee = totalFee.add(buyFee);

        // 其他费用-印花税
        BigDecimal otherFee = totalAmt.multiply(market.getOtherFee());
        otherFee = otherFee.setScale(2, 4);
        totalFee = totalFee.add(otherFee);

        memberPosition.setBuyFee(buyFee);
        memberPosition.setAllProfitAndLose(totalFee.negate());
        String source = StringUtils.isEmpty(body.getSource()) ? FundsSourceEnum.STOCK.getType() : body.getSource();
        memberPosition.setSource(source);
        return memberPosition;
    }

    private static void setProfitAndLose(Market market, MemberPosition memberPosition, BigDecimal last) {
        BigDecimal sellAmt = last.multiply(new BigDecimal(memberPosition.getNum()));
        BigDecimal profitAndLose = sellAmt.subtract(memberPosition.getTotalAmt());
        BigDecimal totalFee = memberPosition.getBuyFee().add(memberPosition.getOtherFee());

        // 卖出
        BigDecimal sellFee = sellAmt.multiply(market.getSellFee());
        sellFee = sellFee.setScale(2, 4);
        totalFee = totalFee.add(sellFee);

        // 其他费用-印花税
        BigDecimal otherFee = sellAmt.multiply(market.getSellOtherFee());
        otherFee = otherFee.setScale(2, 4);
        totalFee = totalFee.add(otherFee);

        memberPosition.setSellFee(sellFee);
        memberPosition.setOtherFee(memberPosition.getOtherFee().add(otherFee));
        memberPosition.setSellOrderPrice(last);
        memberPosition.setProfitAndLose(profitAndLose);
        memberPosition.setAllProfitAndLose(profitAndLose.subtract(totalFee));
    }
}
