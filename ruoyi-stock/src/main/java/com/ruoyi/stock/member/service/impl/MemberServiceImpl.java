package com.ruoyi.stock.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.mapper.MarketMapper;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.stock.member.domain.MemberFunds;
import com.ruoyi.stock.member.mapper.MemberBankMapper;
import com.ruoyi.stock.member.mapper.MemberFundsMapper;
import com.ruoyi.stock.member.mapper.MemberMapper;
import com.ruoyi.stock.member.service.IMemberService;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.member.vo.MemberFundsVo;
import com.ruoyi.stock.member.vo.SendSmsVo;
import com.ruoyi.stock.member.vo.UpdateAmtVo;
import com.ruoyi.stock.reset.AppApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberFundsMapper memberFundsMapper;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private MemberBankMapper memberBankMapper;
    @Autowired
    private MemberFundsService memberFundsService;
    @Autowired
    private IExperienceService iExperienceService;

    /**
     * 查询会员
     *
     * @param id 会员主键
     * @return 会员
     */
    @Override
    public Member selectMemberById(Long id) {
        Member member = memberMapper.selectById(id);
        if (LogicUtils.isNotNull(member)) {
            setFunds(Collections.singletonList(member));
        }
        return member;
    }

    /**
     * 查询会员列表
     *
     * @param member 会员
     * @return 会员
     */
    @Override
    public List<Member> selectMemberList(Member member) {
        QueryWrapper<Member> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(member.getId()), "member.id", member.getId());
        ew.eq(LogicUtils.isNotNull(member.getUserId()), "member.user_id", member.getUserId());
        ew.like(LogicUtils.isNotNull(member.getPhone()), "member.phone", member.getPhone());
        ew.eq(LogicUtils.isNotNull(member.getAccountType()), "member.account_type", member.getAccountType());
        ew.eq(LogicUtils.isNotNull(member.getIsActive()), "member.is_active", member.getIsActive());
        ew.eq(LogicUtils.isNotNull(member.getIsLogin()), "member.is_login", member.getIsLogin());
        ew.like(LogicUtils.isNotNull(member.getUserName()), "sys_user.user_name", member.getUserName());
        ew.like(SessionInfo.isAgent(), "sys_user.tree", "/" + SessionInfo.getUserId());

        ew.orderByDesc("create_time");
        List<Member> res = memberMapper.selectByList(ew);
        if (LogicUtils.isNotEmpty(res)) {
            setBank(res);
            setFunds(res);
        }
        return res;
    }

    private void setFunds(List<Member> list) {
        List<Long> memberIds = list.stream().map(Member::getId).collect(Collectors.toList());
        Map<Long, List<MemberFunds>> memberFundsMap = getMemberFunds(memberIds);
        List<Market> markets = marketMapper.selectList(null);
        markets.sort(Comparator.comparing(Market::getSort));
        Map<Long, Map<Long, BigDecimal>> experienceMap = iExperienceService.queryEnableAmt(memberIds);
        for (Member member : list) {
            List<MemberFunds> memberFundsList = memberFundsMap.get(member.getId());
            memberFundsList = LogicUtils.isNotEmpty(memberFundsList) ? memberFundsList : Collections.EMPTY_LIST;
            Map<Long, MemberFunds> fundsMap = memberFundsList.stream()
                    .collect(Collectors.toMap(MemberFunds::getMarketId, m -> m));

            List<MemberFundsVo> memberFundsVos = new ArrayList<>();

            Map<Long, BigDecimal> marketExperienceMap = experienceMap.getOrDefault(member.getId(),
                    Collections.emptyMap());
            for (Market market : markets) {
                MemberFunds memberFunds = fundsMap.get(market.getId());
                if (LogicUtils.isNull(memberFunds)) {
                    memberFunds = new MemberFunds();
                    memberFunds.setMarketId(market.getId());
                    memberFunds.setEnableAmt(BigDecimal.ZERO);
                    memberFunds.setOccupancyAmt(BigDecimal.ZERO);
                    memberFunds.setFreezeAmt(BigDecimal.ZERO);
                    memberFunds.setProfitAmt(BigDecimal.ZERO);
                }
                MemberFundsVo memberFundsVo = new MemberFundsVo();
                BeanUtils.copyProperties(memberFunds, memberFundsVo);
                memberFundsVo.setMarketName(market.getName());
                memberFundsVo.setExperienceAmt(marketExperienceMap.get(market.getId()));
                memberFundsVos.add(memberFundsVo);
            }
//            memberFundsVos.sort(Comparator.comparing(MemberFundsVo::getMarketId));
            member.setFunds(memberFundsVos);
        }
    }

    private Map<Long, List<MemberFunds>> getMemberFunds(List<Long> memberIds) {
        QueryWrapper<MemberFunds> ew = new QueryWrapper<>();
        ew.in("member_id", memberIds);
        List<MemberFunds> memberFunds = memberFundsMapper.selectList(ew);
        Map<Long, List<MemberFunds>> memberFundsMap = new HashMap<>();
        for (MemberFunds memberFund : memberFunds) {
            memberFundsMap.computeIfAbsent(memberFund.getMemberId(), key -> new ArrayList<>()).add(memberFund);
        }
        return memberFundsMap;
    }

    private void setBank(List<Member> list) {
        List<Long> memberIds = list.stream().map(Member::getId).collect(Collectors.toList());
        QueryWrapper<MemberBank> ew = new QueryWrapper<>();
        ew.in("member_id", memberIds);
        ew.groupBy("member_id");
        List<MemberBank> memberBanks = memberBankMapper.selectList(ew);
        Map<Long, MemberBank> bankMap = memberBanks.stream().collect(Collectors.toMap(MemberBank::getMemberId, m -> m));
        for (Member member : list) {
            member.setBank(bankMap.get(member.getId()));
        }
    }

    /**
     * 新增会员
     *
     * @param member 会员
     * @return 结果
     */
    @Override
    public int insertMember(Member member) {
        QueryWrapper<Member> ew = new QueryWrapper<>();
        ew.eq("phone", member.getPhone());
        LogicUtils.assertEquals(memberMapper.selectCount(ew), 0L, "手机号码重复");

        member.setCreateTime(DateUtils.getNowDate());
        return memberMapper.insert(member);
    }

    /**
     * 修改会员
     *
     * @param member 会员
     * @return 结果
     */
    @Override
    public int updateMember(Member member) {
        member.setUpdateTime(DateUtils.getNowDate());
        member.setPhone(null);
        int i = memberMapper.updateById(member);
        AppApi.changeMember(member.getId());
        return i;
    }

    /**
     * 批量删除会员
     *
     * @param ids 需要删除的会员主键
     * @return 结果
     */
    @Override
    public int deleteMemberByIds(List<Long> ids) {
        QueryWrapper<Member> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberMapper.delete(ew);
    }

    /**
     * 删除会员信息
     *
     * @param id 会员主键
     * @return 结果
     */
    @Override
    public int deleteMemberById(Long id) {
        return memberMapper.deleteById(id);
    }


    @Override
    public boolean realName(Member member) {
        Member memberByDb = memberMapper.selectById(member.getId());
        LogicUtils.assertNotNull(memberByDb, "会员不存在");
        memberByDb.setIsActive(member.getIsActive());
        memberByDb.setActiveMsg(member.getActiveMsg());
        memberByDb.setPhone(null);
        memberMapper.updateById(memberByDb);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAmt(UpdateAmtVo updateAmtVo) {
        Member member = memberMapper.selectById(updateAmtVo.getMemberId());
        LogicUtils.assertNotNull(member, "会员不存在");
        Market market = marketMapper.selectById(updateAmtVo.getMarketId());
        LogicUtils.assertNotNull(market, "市场不存在");
        LogicUtils.assertNotNull(updateAmtVo.getAmt(), "操作资金不能为空");
        LogicUtils.assertNotNull(updateAmtVo.getAmt().compareTo(BigDecimal.ZERO) < 0, "操作资金不能为0");
        if (LogicUtils.isEquals(updateAmtVo.getType(), "1")) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.ADMIN_AMT);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.OTHER);
            fundsOperateVo.setMember(member);
            fundsOperateVo.setMarket(market);
            fundsOperateVo.setAmt(updateAmtVo.getAmt());
            memberFundsService.addEnableAmt(fundsOperateVo);
        }
        if (LogicUtils.isEquals(updateAmtVo.getType(), "2")) {
            FundsOperateVo fundsOperateVo = new FundsOperateVo();
            fundsOperateVo.setSource(FundsSourceEnum.ADMIN_AMT);
            fundsOperateVo.setOperateType(FundsOperateTypeEnum.OTHER);
            fundsOperateVo.setMember(member);
            fundsOperateVo.setMarket(market);
            fundsOperateVo.setAmt(updateAmtVo.getAmt());
            memberFundsService.subEnableAmt(fundsOperateVo);
        }
        return true;
    }

    @Override
    public boolean bankCard(MemberBank bank) {
        LogicUtils.assertNotNull(bank.getMemberId(), "用户ID不能为空");
        QueryWrapper<MemberBank> ew = new QueryWrapper<>();
        ew.eq("member_id", bank.getMemberId());
        MemberBank memberBank = memberBankMapper.selectOne(ew);
        if (LogicUtils.isNull(memberBank)) {
            memberBank = new MemberBank();
            BeanUtils.copyProperties(bank, memberBank);
            memberBank.setMemberId(bank.getMemberId());
            memberBank.setId(null);
            memberBankMapper.insert(memberBank);
        } else {
            Long id = memberBank.getId();
            Long memberId = memberBank.getMemberId();
            BeanUtils.copyProperties(bank, memberBank);
            memberBank.setMemberId(memberId);
            memberBank.setId(id);
            memberBankMapper.updateById(memberBank);
        }
        return true;
    }

    @Override
    public boolean bankCardReset(MemberBank bank) {
        memberBankMapper.deleteById(bank.getId());
        return true;
    }

    @Override
    public String sendSms(SendSmsVo body) {
        return AppApi.sendSms(body);
    }
}
