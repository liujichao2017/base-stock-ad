package com.ruoyi.stock.borrow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.exception.ParamException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.borrow.domain.MemberBorrowRecord;
import com.ruoyi.stock.borrow.mapper.BorrowCouponsMapper;
import com.ruoyi.stock.borrow.mapper.MemberBorrowRecordMapper;
import com.ruoyi.stock.borrow.service.IMemberBorrowRecordService;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 借劵记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@Service
public class MemberBorrowRecordServiceImpl implements IMemberBorrowRecordService {
    @Autowired
    private MemberBorrowRecordMapper memberBorrowRecordMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private BorrowCouponsMapper borrowCouponsMapper;

    @Autowired
    private IExperienceService experienceService;

    /**
     * 查询借劵记录
     *
     * @param id 借劵记录主键
     * @return 借劵记录
     */
    @Override
    public MemberBorrowRecord selectMemberBorrowRecordById(Long id) {
        return memberBorrowRecordMapper.selectById(id);
    }

    /**
     * 查询借劵记录列表
     *
     * @param body 借劵记录
     * @return 借劵记录
     */
    @Override
    public List<MemberBorrowRecord> selectMemberBorrowRecordList(MemberBorrowRecord body) {
        QueryWrapper<MemberBorrowRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(body.getMemberId()), "b.member_id", body.getMemberId());
        ew.like(LogicUtils.isNotNull(body.getMemberPhone()), "m.phone", body.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(body.getUserId()), "u.user_id", body.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(body.getMarketId()), "b.market_id", body.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.like(LogicUtils.isNotNull(body.getStockCode()), "b.stock_code", body.getStockCode());
        ew.like(LogicUtils.isNotNull(body.getStockName()), "b.stock_name", body.getStockName());
        ew.eq(LogicUtils.isNotNull(body.getStatus()), "b.status", body.getStatus());
        return memberBorrowRecordMapper.selectByList(ew);
    }

    /**
     * 新增借劵记录
     *
     * @param memberBorrowRecord 借劵记录
     * @return 结果
     */
    @Override
    public int insertMemberBorrowRecord(MemberBorrowRecord memberBorrowRecord) {
        memberBorrowRecord.setCreateTime(DateUtils.getNowDate());
        return memberBorrowRecordMapper.insert(memberBorrowRecord);
    }

    /**
     * 修改借劵记录
     *
     * @param body 借劵记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMemberBorrowRecord(MemberBorrowRecord body) {
        LogicUtils.assertNotNull(body.getId(), "id未空");
        MemberBorrowRecord db = memberBorrowRecordMapper.selectById(body.getId());
        LogicUtils.assertNotNull(db, "记录不存在");
        LogicUtils.assertEquals(db.getStatus(), 1, "已审批");
        LogicUtils.assertTrue(LogicUtils.isNotEquals(body.getStatus(), 1) || LogicUtils.isNotNull(body.getApplyNum()),
                              "已审批");

        MemberBorrowRecord update = new MemberBorrowRecord();
        update.setId(db.getId());
        update.setStatus(body.getStatus());
        if (LogicUtils.isNotEquals(body.getStatus(), 2)) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.BORROW);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.ROLLBACK);
            fundsOperateVo.setMemberId(db.getMemberId());
            fundsOperateVo.setMarketId(db.getMarketId());
            fundsOperateVo.setAmt(db.getBuyAmt());
            fundsOperateVo.setSourceId(db.getId());
            fundsOperateVo.setName(db.getStockName());
            fundsOperateVo.setCode(db.getStockCode());

            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, db.getStockName())
                    .set(FundsOperateVo.FundsInfoKey.STOCK_CODE, db.getStockCode())
                    .set(FundsOperateVo.FundsInfoKey.ROLLBACK_AMT, db.getBuyAmt())
                    .build();

            memberFundsService.subFreezeAmt(fundsOperateVo);
        } else if (LogicUtils.isNotEquals(body.getStatus(), 1)) {
            LogicUtils.assertTrue(body.getApplyNum() > 0, "通过张数需要大于0");
            LogicUtils.assertTrue(db.getBuyNum() >= body.getApplyNum(), "通过张数不能大于买入张数");
            update.setApplyNum(body.getApplyNum());
            BigDecimal buyAmtByOld = db.getBuyAmt();

            long stockNum = update.getApplyNum() * db.getNum();
            BigDecimal buyMoney = BigDecimal.valueOf(stockNum).multiply(db.getBuyPrice());
            update.setBuyAmt(buyMoney);
            update.setCycleTime(DateUtils.getDate(db.getCycle()));
            if (!Objects.equals(db.getBuyNum(), body.getApplyNum())) {
                // 重新计算收益
                BigDecimal rebate = db.getRebate();
                rebate = rebate.divide(BigDecimal.valueOf(100), 4, 4);
                BigDecimal totalIncome = buyMoney.multiply(rebate);
                BigDecimal dayIncome = totalIncome.divide(BigDecimal.valueOf(db.getCycle()), 2, 4);
                update.setTotalIncome(totalIncome);
                update.setDayIncome(dayIncome);
            }

            BigDecimal buyAmtByNew = update.getBuyAmt();

            borrowCouponsMapper.addBorrowNumber(db.getBcId(), update.getApplyNum());

            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.BORROW);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.UNFREEZE);
            fundsOperateVo.setMemberId(db.getMemberId());
            fundsOperateVo.setMarketId(db.getMarketId());
            fundsOperateVo.setAmt(buyAmtByOld);
            fundsOperateVo.setSourceId(db.getId());
            fundsOperateVo.setName(db.getStockName());
            fundsOperateVo.setCode(db.getStockCode());
            fundsOperateVo.setVisible(YNEnum.NO.getType());

            memberFundsService.subFreezeAmt(fundsOperateVo);

            fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.BORROW);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.BUY);
            fundsOperateVo.setMemberId(db.getMemberId());
            fundsOperateVo.setMarketId(db.getMarketId());
            fundsOperateVo.setAmt(buyAmtByNew);
            fundsOperateVo.setSourceId(db.getId());
            fundsOperateVo.setName(db.getStockName());
            fundsOperateVo.setCode(db.getStockCode());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, db.getStockName());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_CODE, db.getStockCode());
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.APPLY_AMT, buyAmtByOld);
            fundsOperateVo.set(FundsOperateVo.FundsInfoKey.PASS_AMT, buyAmtByNew);
            if (buyAmtByOld.subtract(buyAmtByNew).compareTo(BigDecimal.ZERO) > 0) {
                fundsOperateVo.set(FundsOperateVo.FundsInfoKey.ROLLBACK_AMT, buyAmtByOld.subtract(buyAmtByNew));
            }
            fundsOperateVo.build();
            memberFundsService.addOccupancyAmt(fundsOperateVo);
        } else {
            throw new ParamException("状态不符合预期: 只能同意或拒绝");
        }
        LogicUtils.assertTrue(memberBorrowRecordMapper.updateById(update) == 1, "修改记录失败");
        return 1;
    }

    /**
     * 批量删除借劵记录
     *
     * @param ids 需要删除的借劵记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberBorrowRecordByIds(List<Long> ids) {
        QueryWrapper<MemberBorrowRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberBorrowRecordMapper.delete(ew);
    }

    /**
     * 删除借劵记录信息
     *
     * @param id 借劵记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberBorrowRecordById(Long id) {
        return memberBorrowRecordMapper.deleteById(id);
    }
}
