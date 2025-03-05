package com.ruoyi.stock.funds.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.funds.mapper.MemberFundsLogsMapper;
import com.ruoyi.stock.funds.domain.MemberFundsLogs;
import com.ruoyi.stock.funds.service.IMemberFundsLogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 资金动态Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberFundsLogsServiceImpl implements IMemberFundsLogsService
{
    @Autowired
    private MemberFundsLogsMapper memberFundsLogsMapper;

    /**
     * 查询资金动态
     *
     * @param id 资金动态主键
     * @return 资金动态
     */
    @Override
    public MemberFundsLogs selectMemberFundsLogsById(Long id) {
        return memberFundsLogsMapper.selectById(id);
    }

    /**
     * 查询资金动态列表
     *
     * @param query 资金动态
     * @return 资金动态
     */
    @Override
    public List<MemberFundsLogs> selectMemberFundsLogsList(MemberFundsLogs query) {
        QueryWrapper<MemberFundsLogs> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(query.getMemberId()), "b.member_id", query.getMemberId());
        ew.like(LogicUtils.isNotNull(query.getMemberPhone()), "m.phone", query.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(query.getUserId()), "u.user_id", query.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(query.getMarketId()), "b.market_id", query.getMarketId());
        ew.orderByDesc("b.id");

        ew.eq(LogicUtils.isNotNull(query.getVisible()), "b.visible", query.getVisible());
        ew.eq(LogicUtils.isNotNull(query.getSource()), "b.source", query.getSource());
        ew.eq(LogicUtils.isNotNull(query.getOperateType()), "b.operate_type", query.getOperateType());
        ew.eq(LogicUtils.isNotNull(query.getSourceId()), "b.source_id", query.getSourceId());
        return memberFundsLogsMapper.selectByList(ew);
    }

    /**
     * 新增资金动态
     *
     * @param memberFundsLogs 资金动态
     * @return 结果
     */
    @Override
    public int insertMemberFundsLogs(MemberFundsLogs memberFundsLogs)
    {
        memberFundsLogs.setCreateTime(DateUtils.getNowDate());
        return memberFundsLogsMapper.insert(memberFundsLogs);
    }

    /**
     * 修改资金动态
     *
     * @param memberFundsLogs 资金动态
     * @return 结果
     */
    @Override
    public int updateMemberFundsLogs(MemberFundsLogs memberFundsLogs)
    {
        memberFundsLogs.setUpdateTime(DateUtils.getNowDate());
        return memberFundsLogsMapper.updateById(memberFundsLogs);
    }

    /**
     * 批量删除资金动态
     *
     * @param ids 需要删除的资金动态主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundsLogsByIds(List<Long> ids)
    {
        QueryWrapper<MemberFundsLogs> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberFundsLogsMapper.delete(ew);
    }

    /**
     * 删除资金动态信息
     *
     * @param id 资金动态主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundsLogsById(Long id)
    {
        return memberFundsLogsMapper.deleteById(id);
    }
}
