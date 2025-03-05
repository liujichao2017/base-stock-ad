package com.ruoyi.stock.fund.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.fund.domain.FundLists;
import com.ruoyi.stock.fund.domain.MemberFundRecord;
import com.ruoyi.stock.fund.mapper.FundListsMapper;
import com.ruoyi.stock.fund.mapper.MemberFundInterestRecordMapper;
import com.ruoyi.stock.fund.mapper.MemberFundRecordMapper;
import com.ruoyi.stock.fund.service.IMemberFundRecordService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 基金购买记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class MemberFundRecordServiceImpl implements IMemberFundRecordService {
    @Autowired
    private MemberFundRecordMapper memberFundRecordMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private IExperienceService experienceService;
    @Autowired
    private FundListsMapper fundListsMapper;
    @Autowired
    private MemberFundInterestRecordMapper memberFundInterestRecordMapper;

    /**
     * 查询基金购买记录
     *
     * @param id 基金购买记录主键
     * @return 基金购买记录
     */
    @Override
    public MemberFundRecord selectMemberFundRecordById(Long id) {
        return memberFundRecordMapper.selectById(id);
    }

    /**
     * 查询基金购买记录列表
     *
     * @param memberFundRecord 基金购买记录
     * @return 基金购买记录
     */
    @Override
    public List<MemberFundRecord> selectMemberFundRecordList(MemberFundRecord memberFundRecord) {
        QueryWrapper<MemberFundRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(memberFundRecord.getMemberId()), "b.member_id", memberFundRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(memberFundRecord.getMemberPhone()), "m.phone", memberFundRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(memberFundRecord.getUserId()), "u.user_id", memberFundRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(memberFundRecord.getMarketId()), "b.market_id", memberFundRecord.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.eq(LogicUtils.isNotNull(memberFundRecord.getStatus()), "b.status", memberFundRecord.getStatus());
        ew.like(LogicUtils.isNotNull(memberFundRecord.getFundName()), "f.`name`", memberFundRecord.getFundName());
        ew.like(LogicUtils.isNotNull(memberFundRecord.getFundCode()), "f.`code`", memberFundRecord.getFundCode());
        ew.eq(LogicUtils.isNotNull(memberFundRecord.getFundType()), "f.`type`", memberFundRecord.getFundType());
        List<MemberFundRecord> memberFundRecords = memberFundRecordMapper.selectByList(ew);
        for (MemberFundRecord fundRecord : memberFundRecords) {
            fundRecord.setIncome(memberFundInterestRecordMapper.sumInterest(fundRecord.getId()));

        }
        return memberFundRecords;
    }

    /**
     * 新增基金购买记录
     *
     * @param memberFundRecord 基金购买记录
     * @return 结果
     */
    @Override
    public int insertMemberFundRecord(MemberFundRecord memberFundRecord) {
        memberFundRecord.setCreateTime(DateUtils.getNowDate());
        return memberFundRecordMapper.insert(memberFundRecord);
    }

    /**
     * 修改基金购买记录
     *
     * @param memberFundRecord 基金购买记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMemberFundRecord(MemberFundRecord memberFundRecord) {
        MemberFundRecord memberFundRecordByDb = memberFundRecordMapper.selectById(memberFundRecord.getId());
        LogicUtils.assertNotNull(memberFundRecordByDb, "产品不存在");
        LogicUtils.assertEquals("1", memberFundRecordByDb.getStatus(), "已审批");
        memberFundRecordByDb.setStatus(memberFundRecord.getStatus());

        FundLists fundLists = fundListsMapper.selectById(memberFundRecordByDb.getFundId());
        LogicUtils.assertNotNull(fundLists, "产品不存在");
        if ("2".equals(memberFundRecordByDb.getStatus())) {
            FundsOperateVo fundsOperateVo2 = new FundsOperateVo();
            fundsOperateVo2.setSource(FundsSourceEnum.FUND);
            fundsOperateVo2.setOperateType(FundsOperateTypeEnum.BUY);
            fundsOperateVo2.setMemberId(memberFundRecordByDb.getMemberId());
            fundsOperateVo2.setMarketId(memberFundRecordByDb.getMarketId());
            fundsOperateVo2.setAmt(memberFundRecordByDb.getAmt());
            fundsOperateVo2.setSourceId(memberFundRecordByDb.getId());
            fundsOperateVo2.setName(fundLists.getName());
//            fundsOperateVo2.setName(fundLists.getCode());
            fundsOperateVo2.set(FundsOperateVo.FundsInfoKey.PRODUCT_NAME, fundLists.getName())
//                    .set(FundsOperateVo.FundsInfoKey.PRODUCT_CODE, fundLists.getCode())
                    .set(FundsOperateVo.FundsInfoKey.APPLY_AMT, memberFundRecordByDb.getAmt())
                    .set(FundsOperateVo.FundsInfoKey.PASS_AMT, memberFundRecordByDb.getAmt())
                    .build();
            memberFundsService.freezeToOccupancyAmt(fundsOperateVo2);

            memberFundRecordByDb.setPassTime(new Date());

        }
        if ("3".equals(memberFundRecordByDb.getStatus())) {
            FundsOperateVo fundsOperateVo1 = new FundsOperateVo();
            fundsOperateVo1.setSource(FundsSourceEnum.FUND);
            fundsOperateVo1.setOperateType(FundsOperateTypeEnum.ROLLBACK);
            fundsOperateVo1.setMemberId(memberFundRecordByDb.getMemberId());
            fundsOperateVo1.setMarketId(memberFundRecordByDb.getMarketId());
            fundsOperateVo1.setAmt(memberFundRecordByDb.getAmt());
            fundsOperateVo1.setSourceId(memberFundRecordByDb.getId());
            fundsOperateVo1.setName(fundLists.getName());
//            fundsOperateVo1.setName(fundLists.getCode());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.PRODUCT_NAME, fundLists.getName())
//                    .set(FundsOperateVo.FundsInfoKey.PRODUCT_CODE, fundLists.getCode())
                    .set(FundsOperateVo.FundsInfoKey.ROLLBACK_AMT, memberFundRecordByDb.getAmt())
                    .build();
            memberFundsService.subFreezeAmt(fundsOperateVo1);
        }

        memberFundRecordByDb.setUpdateTime(DateUtils.getNowDate());
        return memberFundRecordMapper.updateById(memberFundRecordByDb);
    }

    /**
     * 批量删除基金购买记录
     *
     * @param ids 需要删除的基金购买记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundRecordByIds(List<Long> ids) {
        QueryWrapper<MemberFundRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberFundRecordMapper.delete(ew);
    }

    /**
     * 删除基金购买记录信息
     *
     * @param id 基金购买记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundRecordById(Long id) {
        return memberFundRecordMapper.deleteById(id);
    }
}
