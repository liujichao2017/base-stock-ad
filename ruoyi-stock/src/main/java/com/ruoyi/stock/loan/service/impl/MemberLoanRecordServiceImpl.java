package com.ruoyi.stock.loan.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.loan.domain.MemberLoanRecord;
import com.ruoyi.stock.loan.mapper.MemberLoanRecordMapper;
import com.ruoyi.stock.loan.service.IMemberLoanRecordService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户贷款申请Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class MemberLoanRecordServiceImpl implements IMemberLoanRecordService {
    @Autowired
    private MemberLoanRecordMapper memberLoanRecordMapper;
    @Autowired
    private MemberFundsService memberFundsService;

    /**
     * 查询用户贷款申请
     *
     * @param id 用户贷款申请主键
     * @return 用户贷款申请
     */
    @Override
    public MemberLoanRecord selectMemberLoanRecordById(Long id) {
        return memberLoanRecordMapper.selectById(id);
    }

    /**
     * 查询用户贷款申请列表
     *
     * @param memberLoanRecord 用户贷款申请
     * @return 用户贷款申请
     */
    @Override
    public List<MemberLoanRecord> selectMemberLoanRecordList(MemberLoanRecord memberLoanRecord) {
        QueryWrapper<MemberLoanRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(memberLoanRecord.getMemberId()), "b.member_id", memberLoanRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(memberLoanRecord.getMemberPhone()), "m.phone", memberLoanRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(memberLoanRecord.getUserId()), "u.user_id", memberLoanRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(memberLoanRecord.getMarketId()), "b.market_id", memberLoanRecord.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.eq(LogicUtils.isNotNull(memberLoanRecord.getStatus()), "b.status", memberLoanRecord.getStatus());

        return memberLoanRecordMapper.selectByList(ew);
    }

    /**
     * 新增用户贷款申请
     *
     * @param memberLoanRecord 用户贷款申请
     * @return 结果
     */
    @Override
    public int insertMemberLoanRecord(MemberLoanRecord memberLoanRecord) {
        memberLoanRecord.setCreateTime(DateUtils.getNowDate());
        return memberLoanRecordMapper.insert(memberLoanRecord);
    }

    /**
     * 修改用户贷款申请
     *
     * @param body 用户贷款申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMemberLoanRecord(MemberLoanRecord body) {
        MemberLoanRecord memberLoanRecord = memberLoanRecordMapper.selectById(body.getId());
        LogicUtils.assertNotNull(memberLoanRecord, "记录不存在");
        LogicUtils.assertTrue(("1".equals(memberLoanRecord.getStatus().toString()) && ("2".equals(
                body.getStatus().toString()) || "3".equals(body.getStatus().toString())))
                                      || (("2".equals(memberLoanRecord.getStatus().toString()) || "5".equals(
                memberLoanRecord.getStatus().toString())) && "4".equals(body.getStatus().toString())), "已审批");
        memberLoanRecord.setStatus(body.getStatus());


        if ("2".equals(memberLoanRecord.getStatus().toString())) {
            BigDecimal passAmount = body.getPassAmount();
            passAmount = passAmount == null ? memberLoanRecord.getLoanAmount() : passAmount;

            FundsOperateVo fundsOperateVo1 = new FundsOperateVo();
            fundsOperateVo1.setSource(FundsSourceEnum.LOAN);
            fundsOperateVo1.setOperateType(FundsOperateTypeEnum.LOAN);
            fundsOperateVo1.setMemberId(memberLoanRecord.getMemberId());
            fundsOperateVo1.setMarketId(memberLoanRecord.getMarketId());
            fundsOperateVo1.setAmt(passAmount);
            fundsOperateVo1.setSourceId(memberLoanRecord.getId());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.LOANS_AMT, passAmount)
                    .build();
            memberFundsService.addEnableAmt(fundsOperateVo1);


            memberLoanRecord.setPassAmount(passAmount);
            memberLoanRecord.setPassTime(new Date());
        }
        if ("3".equals(memberLoanRecord.getStatus().toString())) {
            memberLoanRecord.setRejectContent(body.getRejectContent());
        }
        if ("4".equals(memberLoanRecord.getStatus().toString())) {
            BigDecimal passAmount = memberLoanRecord.getPassAmount();
            BigDecimal interest = BigDecimal.ZERO;
            BigDecimal interestRate = memberLoanRecord.getInterestRate();
            long betweenDay = DateUtil.between(memberLoanRecord.getPassTime(), new Date(), DateUnit.DAY);
            if (interestRate.compareTo(BigDecimal.ZERO) > 0 && betweenDay > 0) {
                interestRate = interestRate.divide(new BigDecimal("100"), 4, 4);
                interest = interestRate.multiply(passAmount);
                interest = interest.multiply(BigDecimal.valueOf(betweenDay));
            }
            FundsOperateVo fundsOperateVo1 = new FundsOperateVo();
            fundsOperateVo1.setSource(FundsSourceEnum.LOAN);
            fundsOperateVo1.setOperateType(FundsOperateTypeEnum.LOAN_RECYCLE);
            fundsOperateVo1.setMemberId(memberLoanRecord.getMemberId());
            fundsOperateVo1.setMarketId(memberLoanRecord.getMarketId());
            fundsOperateVo1.setAmt(passAmount.add(interest));
            fundsOperateVo1.setSourceId(memberLoanRecord.getId());
            fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.LOAN_RECYCLE_AMT, passAmount.add(interest));
            if (interest.compareTo(BigDecimal.ZERO) > 0) {
                fundsOperateVo1.set(FundsOperateVo.FundsInfoKey.INTEREST_AMT, interest);
            }
            fundsOperateVo1.build();
            memberFundsService.subEnableAmt(fundsOperateVo1);

            memberLoanRecord.setFeeAmt(interest);
            memberLoanRecord.setRepaymentTime(new Date());
        }
        memberLoanRecord.setUpdateTime(DateUtils.getNowDate());
        return memberLoanRecordMapper.updateById(memberLoanRecord);
    }

    public static void main(String[] args) {

    }

    /**
     * 批量删除用户贷款申请
     *
     * @param ids 需要删除的用户贷款申请主键
     * @return 结果
     */
    @Override
    public int deleteMemberLoanRecordByIds(List<Long> ids) {
        QueryWrapper<MemberLoanRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberLoanRecordMapper.delete(ew);
    }

    /**
     * 删除用户贷款申请信息
     *
     * @param id 用户贷款申请主键
     * @return 结果
     */
    @Override
    public int deleteMemberLoanRecordById(Long id) {
        return memberLoanRecordMapper.deleteById(id);
    }
}
