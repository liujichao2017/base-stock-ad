package com.ruoyi.stock.funds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.funds.domain.MemberRecharge;
import com.ruoyi.stock.funds.mapper.MemberRechargeMapper;
import com.ruoyi.stock.funds.service.IMemberRechargeService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户充值Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberRechargeServiceImpl implements IMemberRechargeService {
    @Autowired
    private MemberRechargeMapper memberRechargeMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private IExperienceService experienceService;
    /**
     * 查询客户充值
     *
     * @param id 客户充值主键
     * @return 客户充值
     */
    @Override
    public MemberRecharge selectMemberRechargeById(Long id) {
        return memberRechargeMapper.selectById(id);
    }

    /**
     * 查询客户充值列表
     *
     * @param query 客户充值
     * @return 客户充值
     */
    @Override
    public List<MemberRecharge> selectMemberRechargeList(MemberRecharge query) {
        QueryWrapper<MemberRecharge> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(query.getMemberId()), "b.member_id", query.getMemberId());
        ew.like(LogicUtils.isNotNull(query.getMemberPhone()), "m.phone", query.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(query.getUserId()), "u.user_id", query.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(query.getMarketId()), "b.market_id", query.getMarketId());
        ew.orderByDesc("b.create_time");


        ew.eq(LogicUtils.isNotNull(query.getOrderSn()), "b.order_sn", query.getOrderSn());
        ew.eq(LogicUtils.isNotNull(query.getStatus()), "b.status", query.getStatus());

        return memberRechargeMapper.selectByList(ew);
    }

    @Override
    @Transactional
    public int checkMemberRecharge(MemberRecharge memberRecharge) {
        memberRecharge.setUpdateTime(DateUtils.getNowDate());
        memberRecharge.setApprovalTime(new Date());
        if ("1".equals(memberRecharge.getStatus())) {

            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.RECHARGE);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.OTHER);
            fundsOperateVo.setMemberId(memberRecharge.getMemberId());
            fundsOperateVo.setMarketId(memberRecharge.getMarketId());
            fundsOperateVo.setAmt(memberRecharge.getAmt());
            fundsOperateVo.setNegative(false);
            fundsOperateVo.setSourceId(memberRecharge.getId());
            fundsOperateVo.setCurrencyType(memberRecharge.getCurrencyType());
            fundsOperateVo.setAccountType(memberRecharge.getAccountType());
            memberFundsService.addEnableAmt(fundsOperateVo);

            experienceService.recharge(memberRecharge.getMemberId(), memberRecharge.getMarketId(),
                    memberRecharge.getAmt());
        }
//        if (!"0".equals(memberRecharge.getStatus())) {
//
//
//        }

        return memberRechargeMapper.updateById(memberRecharge);
    }

    /**
     * 新增客户充值
     *
     * @param body 客户充值
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMemberRecharge(MemberRecharge body) {
        MemberRecharge memberRecharge = new MemberRecharge();
        memberRecharge.setMemberId(body.getMemberId());
        memberRecharge.setMarketId(body.getMarketId());
        memberRecharge.setOrderSn(LogicUtils.getOrderSn("in"));
        memberRecharge.setAmt(body.getAmt());
        memberRecharge.setActualAmt(body.getAmt());
        memberRecharge.setFee(new BigDecimal("0"));
        memberRecharge.setStatus(body.getStatus());
        memberRecharge.setOrderDesc(body.getOrderDesc());


        approval(memberRecharge);

        return memberRechargeMapper.insert(memberRecharge);
    }

    /**
     * 修改客户充值
     *
     * @param memberRecharge 客户充值
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMemberRecharge(MemberRecharge memberRecharge) {
        MemberRecharge memberRechargeByDb = memberRechargeMapper.selectById(memberRecharge.getId());
        LogicUtils.assertNotNull(memberRechargeByDb, "充值记录不存在");
        LogicUtils.assertNotEquals(memberRechargeByDb.getStatus(), "1", "审批通过的记录不能再审批");
        memberRechargeByDb.setStatus(memberRecharge.getStatus());
        memberRechargeByDb.setOrderDesc(memberRecharge.getOrderDesc());
        approval(memberRechargeByDb);
        memberRecharge.setUpdateTime(DateUtils.getNowDate());
        return memberRechargeMapper.updateById(memberRechargeByDb);
    }

    /**
     * 批量删除客户充值
     *
     * @param ids 需要删除的客户充值主键
     * @return 结果
     */
    @Override
    public int deleteMemberRechargeByIds(List<Long> ids) {
        QueryWrapper<MemberRecharge> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberRechargeMapper.delete(ew);
    }

    /**
     * 删除客户充值信息
     *
     * @param id 客户充值主键
     * @return 结果
     */
    @Override
    public int deleteMemberRechargeById(Long id) {
        return memberRechargeMapper.deleteById(id);
    }


    private void approval(MemberRecharge memberRecharge) {
        if ("1".equals(memberRecharge.getStatus())) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.RECHARGE);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.OTHER);
            fundsOperateVo.setMemberId(memberRecharge.getMemberId());
            fundsOperateVo.setMarketId(memberRecharge.getMarketId());
            fundsOperateVo.setAmt(memberRecharge.getAmt());
            fundsOperateVo.setNegative(false);
            fundsOperateVo.setSourceId(memberRecharge.getId());
            memberFundsService.addEnableAmt(fundsOperateVo);

            experienceService.recharge(memberRecharge.getMemberId(), memberRecharge.getMarketId(),
                                       memberRecharge.getAmt());
        }
        if (!"0".equals(memberRecharge.getStatus())) {
            memberRecharge.setApprovalTime(new Date());
        }
    }
}
