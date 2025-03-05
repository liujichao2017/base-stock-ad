package com.ruoyi.stock.funds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.funds.domain.MemberWithdraw;
import com.ruoyi.stock.funds.mapper.MemberWithdrawMapper;
import com.ruoyi.stock.funds.service.IMemberWithdrawService;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.stock.member.mapper.MemberBankMapper;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户提现Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberWithdrawServiceImpl implements IMemberWithdrawService {
    @Autowired
    private MemberWithdrawMapper memberWithdrawMapper;
    @Autowired
    private MemberBankMapper memberBankMapper;
    @Autowired
    private MemberFundsService memberFundsService;

    /**
     * 查询用户提现
     *
     * @param id 用户提现主键
     * @return 用户提现
     */
    @Override
    public MemberWithdraw selectMemberWithdrawById(Long id) {
        return memberWithdrawMapper.selectById(id);
    }

    /**
     * 查询用户提现列表
     *
     * @param query 用户提现
     * @return 用户提现
     */
    @Override
    public List<MemberWithdraw> selectMemberWithdrawList(MemberWithdraw query) {
        QueryWrapper<MemberWithdraw> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(query.getMemberId()), "b.member_id", query.getMemberId());
        ew.like(LogicUtils.isNotNull(query.getMemberPhone()), "m.phone", query.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(query.getUserId()), "u.user_id", query.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(query.getMarketId()), "b.market_id", query.getMarketId());

        ew.eq(LogicUtils.isNotNull(query.getOrderSn()), "b.order_sn", query.getOrderSn());
        ew.eq(LogicUtils.isNotNull(query.getStatus()), "b.status", query.getStatus());
        ew.orderByDesc("b.create_time");
        List<MemberWithdraw> res = memberWithdrawMapper.selectByList(ew);
        for (MemberWithdraw record : res) {
            if (!"1".equals(record.getStatus())) {
                setBeak(record);
            }
        }
        return res;
    }

    /**
     * 新增用户提现
     *
     * @param body 用户提现
     * @return 结果
     */
    @Override
    public int insertMemberWithdraw(MemberWithdraw body) {
        MemberWithdraw memberWithdraw = new MemberWithdraw();
        memberWithdraw.setMemberId(body.getMemberId());
        memberWithdraw.setMarketId(body.getMarketId());
        memberWithdraw.setOrderSn(LogicUtils.getOrderSn("in"));
        memberWithdraw.setAmt(body.getAmt());
        memberWithdraw.setActualAmt(body.getActualAmt());
        memberWithdraw.setFee(new BigDecimal("0"));
        memberWithdraw.setStatus("0");
        memberWithdraw.setOrderDesc(body.getOrderDesc());
        memberWithdraw.setCreateTime(DateUtils.getNowDate());
        memberWithdraw.setUpdateTime(DateUtils.getNowDate());
        return memberWithdrawMapper.insert(memberWithdraw);
    }

    private void setBeak(MemberWithdraw memberWithdraw) {
        QueryWrapper<MemberBank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberWithdraw.getMemberId());
        queryWrapper.last("limit 1");
        MemberBank memberBank = memberBankMapper.selectOne(queryWrapper);
        LogicUtils.assertNotNull(memberBank, "用户未填写银行卡");
        memberWithdraw.setBank1(memberBank.getBank1());
        memberWithdraw.setBank2(memberBank.getBank2());
        memberWithdraw.setBank3(memberBank.getBank3());
        memberWithdraw.setBank4(memberBank.getBank4());
        memberWithdraw.setBank5(memberBank.getBank5());
        memberWithdraw.setBank6(memberBank.getBank6());
        memberWithdraw.setBank7(memberBank.getBank7());
        memberWithdraw.setBank8(memberBank.getBank8());
    }

    /**
     * 修改用户提现
     *
     * @param body 用户提现
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMemberWithdraw(MemberWithdraw body) {
        MemberWithdraw memberWithdraw = memberWithdrawMapper.selectById(body.getId());
        LogicUtils.assertNotNull(memberWithdraw, "提现记录不存在");
        LogicUtils.assertNotEquals(memberWithdraw.getStatus(), "2", "已拒绝的记录不能再审批");
        LogicUtils.assertNotEquals(memberWithdraw.getStatus(), "3", "用户已经取消的记录不能再审批");
        memberWithdraw.setStatus(body.getStatus());
        memberWithdraw.setOrderDesc(body.getOrderDesc());
        approval(memberWithdraw);
        memberWithdraw.setUpdateTime(DateUtils.getNowDate());
        return memberWithdrawMapper.updateById(memberWithdraw);
    }

    /**
     * 批量删除用户提现
     *
     * @param ids 需要删除的用户提现主键
     * @return 结果
     */
    @Override
    public int deleteMemberWithdrawByIds(List<Long> ids) {
        QueryWrapper<MemberWithdraw> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberWithdrawMapper.delete(ew);
    }

    /**
     * 删除用户提现信息
     *
     * @param id 用户提现主键
     * @return 结果
     */
    @Override
    public int deleteMemberWithdrawById(Long id) {
        return memberWithdrawMapper.deleteById(id);
    }

    private void approval(MemberWithdraw withdraw) {
        if ("2".equals(withdraw.getStatus())) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.RECHARGE);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.OTHER);
            fundsOperateVo.setMemberId(withdraw.getMemberId());
            fundsOperateVo.setMarketId(withdraw.getMarketId());
            fundsOperateVo.setAmt(withdraw.getAmt());
            fundsOperateVo.setNegative(false);
            memberFundsService.addEnableAmt(fundsOperateVo);
        }
        if (!"0".equals(withdraw.getStatus())) {
            withdraw.setApprovalTime(new Date());
        }
        if ("1".equals(withdraw.getStatus())) {
            setBeak(withdraw);
        }
    }
}
