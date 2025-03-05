package com.ruoyi.stock.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.message.domain.MemberMessage;
import com.ruoyi.stock.message.mapper.MemberMessageMapper;
import com.ruoyi.stock.message.service.IMemberMessageService;
import com.ruoyi.stock.reset.AppApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户消息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Service
public class MemberMessageServiceImpl implements IMemberMessageService {
    @Autowired
    private MemberMessageMapper memberMessageMapper;

    /**
     * 查询客户消息
     *
     * @param id 客户消息主键
     * @return 客户消息
     */
    @Override
    public MemberMessage selectMemberMessageById(Long id) {
        return memberMessageMapper.selectById(id);
    }

    /**
     * 查询客户消息列表
     *
     * @param memberMessage 客户消息
     * @return 客户消息
     */
    @Override
    public List<MemberMessage> selectMemberMessageList(MemberMessage memberMessage) {
        QueryWrapper<MemberMessage> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(memberMessage.getMemberId()), "b.member_id", memberMessage.getMemberId());
        ew.like(LogicUtils.isNotNull(memberMessage.getMemberPhone()), "m.phone", memberMessage.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(memberMessage.getUserId()), "u.user_id", memberMessage.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");

        ew.eq(LogicUtils.isNotNull(memberMessage.getType()), "b.type", memberMessage.getType());
        ew.eq(LogicUtils.isNotNull(memberMessage.getSource()), "b.source", memberMessage.getSource());
        ew.eq(LogicUtils.isNotNull(memberMessage.getReadStatus()), "b.read_status", memberMessage.getReadStatus());

        ew.orderByDesc("b.create_time");
        return memberMessageMapper.selectByList(ew);
    }

    /**
     * 新增客户消息
     *
     * @param memberMessage 客户消息
     * @return 结果
     */
    @Override
    public int insertMemberMessage(MemberMessage memberMessage) {
        memberMessage.setCreateTime(DateUtils.getNowDate());
        int insert = memberMessageMapper.insert(memberMessage);
        AppApi.changeMember(memberMessage.getMemberId());
        return insert;
    }

    /**
     * 修改客户消息
     *
     * @param memberMessage 客户消息
     * @return 结果
     */
    @Override
    public int updateMemberMessage(MemberMessage memberMessage) {
        memberMessage.setUpdateTime(DateUtils.getNowDate());
        int i = memberMessageMapper.updateById(memberMessage);
        AppApi.changeMember(memberMessage.getMemberId());
        return i;
    }

    /**
     * 批量删除客户消息
     *
     * @param ids 需要删除的客户消息主键
     * @return 结果
     */
    @Override
    public int deleteMemberMessageByIds(List<Long> ids) {
        QueryWrapper<MemberMessage> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberMessageMapper.delete(ew);
    }

    /**
     * 删除客户消息信息
     *
     * @param id 客户消息主键
     * @return 结果
     */
    @Override
    public int deleteMemberMessageById(Long id) {
        return memberMessageMapper.deleteById(id);
    }
}
