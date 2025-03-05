package com.ruoyi.stock.otc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.exception.ParamException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.otc.domain.OtcRecord;
import com.ruoyi.stock.otc.domain.vo.OtcRecordVo;
import com.ruoyi.stock.otc.mapper.OtcRecordMapper;
import com.ruoyi.stock.otc.service.IOtcRecordService;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.service.IMemberPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * otc(大宗)记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class OtcRecordServiceImpl implements IOtcRecordService {
    @Autowired
    private OtcRecordMapper otcRecordMapper;

    @Autowired
    private IMemberPositionService iMemberPositionService;
    @Autowired
    private MemberFundsService memberFundsService;

    /**
     * 查询otc(大宗)记录
     *
     * @param id otc(大宗)记录主键
     * @return otc(大宗)记录
     */
    @Override
    public OtcRecord selectOtcRecordById(Long id) {
        return otcRecordMapper.selectById(id);
    }

    /**
     * 查询otc(大宗)记录列表
     *
     * @param otcRecord otc(大宗)记录
     * @return otc(大宗)记录
     */
    @Override
    public List<OtcRecordVo> selectOtcRecordList(OtcRecordVo otcRecord) {
        QueryWrapper<OtcRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(otcRecord.getMemberId()), "b.member_id", otcRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(otcRecord.getMemberPhone()), "m.phone", otcRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(otcRecord.getUserId()), "u.user_id", otcRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(otcRecord.getMarketId()), "b.market_id", otcRecord.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.like(LogicUtils.isNotNull(otcRecord.getStockName()), "b.stock_name", otcRecord.getStockName());
        ew.like(LogicUtils.isNotNull(otcRecord.getStockCode()), "b.stock_code", otcRecord.getStockCode());
        ew.like(LogicUtils.isNotNull(otcRecord.getStockGid()), "b.stock_gid", otcRecord.getStockGid());
        ew.eq(LogicUtils.isNotNull(otcRecord.getStatus()), "b.status", otcRecord.getStatus());
        ew.eq(LogicUtils.isNotNull(otcRecord.getType()), "b.type", otcRecord.getType());


        return otcRecordMapper.selectOtcRecordList(ew);
    }

    /**
     * 新增otc(大宗)记录
     *
     * @param otcRecord otc(大宗)记录
     * @return 结果
     */
    @Override
    public int insertOtcRecord(OtcRecord otcRecord) {
        otcRecord.setCreateTime(DateUtils.getNowDate());
        return otcRecordMapper.insert(otcRecord);
    }

    /**
     * 修改otc(大宗)记录
     *
     * @param otcRecord otc(大宗)记录
     * @return 结果
     */
    @Override
    public int updateOtcRecord(OtcRecord otcRecord) {
        otcRecord.setUpdateTime(DateUtils.getNowDate());
        return otcRecordMapper.updateById(otcRecord);
    }

    /**
     * 批量删除otc(大宗)记录
     *
     * @param ids 需要删除的otc(大宗)记录主键
     * @return 结果
     */
    @Override
    public int deleteOtcRecordByIds(List<Long> ids) {
        QueryWrapper<OtcRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return otcRecordMapper.delete(ew);
    }

    /**
     * 删除otc(大宗)记录信息
     *
     * @param id otc(大宗)记录主键
     * @return 结果
     */
    @Override
    public int deleteOtcRecordById(Long id) {
        return otcRecordMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int examOtcRecord(OtcRecord otcRecord) {
        LogicUtils.assertFalse(otcRecord.getAdoptNum() <= 0, "通过数量不能小于等于0");
        LogicUtils.assertFalse(otcRecord.getNums() < otcRecord.getAdoptNum(), "通过数量不能大于申购数量");
        OtcRecord otcRecordByDb = selectOtcRecordById(otcRecord.getId());
        // 退还冻结资金
        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.OTC);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.UNFREEZE);
        fundsOperateVo.setMemberId(otcRecord.getMemberId());
        fundsOperateVo.setMarketId(otcRecord.getMarketId());
        fundsOperateVo.setAmt(otcRecord.getBuyAmt());
        fundsOperateVo.setSourceId(otcRecord.getId());
        fundsOperateVo.setName(otcRecord.getStockName());
        fundsOperateVo.setCode(otcRecord.getStockCode());
        fundsOperateVo.setVisible(YNEnum.NO.getType());
        memberFundsService.subFreezeAmt(fundsOperateVo);
        if (otcRecord.getStatus().equals("2")) {
            //中签金额
            BigDecimal adoptAmt = otcRecord.getPrice()
                    .multiply(BigDecimal.valueOf(otcRecord.getAdoptNum()))
                    .divide(BigDecimal.valueOf(otcRecord.getLever()), 2, RoundingMode.HALF_UP);
            otcRecord.setAdoptAmt(adoptAmt);

            //转持仓
            MemberPosition memberPosition = new MemberPosition();
            memberPosition.setMemberId(otcRecord.getMemberId());
            memberPosition.setStockGid(otcRecord.getStockGid());
            memberPosition.setBuyOrderTime(DateUtils.getNowDate());
            memberPosition.setLever(otcRecord.getLever());
            memberPosition.setNum(otcRecord.getAdoptNum());
            memberPosition.setDirection(otcRecord.getDirection());
            memberPosition.setBuyOrderPrice(otcRecord.getPrice());
            memberPosition.setSource(FundsSourceEnum.OTC.getType());
            if (LogicUtils.isNotNull(otcRecordByDb.getSellTime()) && otcRecordByDb.getSellTime() > 0) {
                memberPosition.setSellTimeLimit(DateUtils.addMinutes(new Date(), otcRecordByDb.getSellTime()));
            }
            iMemberPositionService.insertMemberPosition(memberPosition);
        } else if (otcRecord.getStatus().equals("3")) {
            otcRecord.setAdoptAmt(BigDecimal.ZERO);
            otcRecord.setAdoptNum(0L);
        } else {
            throw new ParamException("审批状态异常, 请重试");
        }
        otcRecord.setUpdateTime(DateUtils.getNowDate());
        return otcRecordMapper.updateById(otcRecord);
    }
}
