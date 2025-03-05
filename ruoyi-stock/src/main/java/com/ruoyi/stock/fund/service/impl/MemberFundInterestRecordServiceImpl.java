package com.ruoyi.stock.fund.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.fund.mapper.MemberFundInterestRecordMapper;
import com.ruoyi.stock.fund.domain.MemberFundInterestRecord;
import com.ruoyi.stock.fund.service.IMemberFundInterestRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 基金盈利Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class MemberFundInterestRecordServiceImpl implements IMemberFundInterestRecordService
{
    @Autowired
    private MemberFundInterestRecordMapper memberFundInterestRecordMapper;

    /**
     * 查询基金盈利
     *
     * @param id 基金盈利主键
     * @return 基金盈利
     */
    @Override
    public MemberFundInterestRecord selectMemberFundInterestRecordById(Long id) {
        return memberFundInterestRecordMapper.selectById(id);
    }

    /**
     * 查询基金盈利列表
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 基金盈利
     */
    @Override
    public List<MemberFundInterestRecord> selectMemberFundInterestRecordList(MemberFundInterestRecord memberFundInterestRecord) {
        QueryWrapper<MemberFundInterestRecord> ew = new QueryWrapper<>();
        ew.setEntity(memberFundInterestRecord);
        ew.orderByDesc("create_time");
        return memberFundInterestRecordMapper.selectList(ew);
    }

    /**
     * 新增基金盈利
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 结果
     */
    @Override
    public int insertMemberFundInterestRecord(MemberFundInterestRecord memberFundInterestRecord)
    {
        memberFundInterestRecord.setCreateTime(DateUtils.getNowDate());
        return memberFundInterestRecordMapper.insert(memberFundInterestRecord);
    }

    /**
     * 修改基金盈利
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 结果
     */
    @Override
    public int updateMemberFundInterestRecord(MemberFundInterestRecord memberFundInterestRecord)
    {
        memberFundInterestRecord.setUpdateTime(DateUtils.getNowDate());
        return memberFundInterestRecordMapper.updateById(memberFundInterestRecord);
    }

    /**
     * 批量删除基金盈利
     *
     * @param ids 需要删除的基金盈利主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundInterestRecordByIds(List<Long> ids)
    {
        QueryWrapper<MemberFundInterestRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberFundInterestRecordMapper.delete(ew);
    }

    /**
     * 删除基金盈利信息
     *
     * @param id 基金盈利主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundInterestRecordById(Long id)
    {
        return memberFundInterestRecordMapper.deleteById(id);
    }
}
