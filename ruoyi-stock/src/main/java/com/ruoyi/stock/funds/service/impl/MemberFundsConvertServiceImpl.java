package com.ruoyi.stock.funds.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.funds.mapper.MemberFundsConvertMapper;
import com.ruoyi.stock.funds.domain.MemberFundsConvert;
import com.ruoyi.stock.funds.service.IMemberFundsConvertService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 兑换记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberFundsConvertServiceImpl implements IMemberFundsConvertService
{
    @Autowired
    private MemberFundsConvertMapper memberFundsConvertMapper;

    /**
     * 查询兑换记录
     *
     * @param id 兑换记录主键
     * @return 兑换记录
     */
    @Override
    public MemberFundsConvert selectMemberFundsConvertById(Long id) {
        return memberFundsConvertMapper.selectById(id);
    }

    /**
     * 查询兑换记录列表
     *
     * @param query 兑换记录
     * @return 兑换记录
     */
    @Override
    public List<MemberFundsConvert> selectMemberFundsConvertList(MemberFundsConvert query) {
        QueryWrapper<MemberFundsConvert> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(query.getMemberId()), "b.member_id", query.getMemberId());
        ew.like(LogicUtils.isNotNull(query.getMemberPhone()), "m.phone", query.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(query.getUserId()), "u.user_id", query.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(query.getToId()), "b.to_id", query.getToId());
        ew.eq(LogicUtils.isNotNull(query.getFromId()), "b.from_id", query.getFromId());
        ew.orderByDesc("b.create_time");
        return memberFundsConvertMapper.selectByList(ew);
    }

    /**
     * 新增兑换记录
     *
     * @param memberFundsConvert 兑换记录
     * @return 结果
     */
    @Override
    public int insertMemberFundsConvert(MemberFundsConvert memberFundsConvert)
    {
        memberFundsConvert.setCreateTime(DateUtils.getNowDate());
        return memberFundsConvertMapper.insert(memberFundsConvert);
    }

    /**
     * 修改兑换记录
     *
     * @param memberFundsConvert 兑换记录
     * @return 结果
     */
    @Override
    public int updateMemberFundsConvert(MemberFundsConvert memberFundsConvert)
    {
        memberFundsConvert.setUpdateTime(DateUtils.getNowDate());
        return memberFundsConvertMapper.updateById(memberFundsConvert);
    }

    /**
     * 批量删除兑换记录
     *
     * @param ids 需要删除的兑换记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundsConvertByIds(List<Long> ids)
    {
        QueryWrapper<MemberFundsConvert> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberFundsConvertMapper.delete(ew);
    }

    /**
     * 删除兑换记录信息
     *
     * @param id 兑换记录主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundsConvertById(Long id)
    {
        return memberFundsConvertMapper.deleteById(id);
    }
}
