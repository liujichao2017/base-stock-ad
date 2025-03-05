package com.ruoyi.stock.experience.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.experience.mapper.ExperienceRecordMapper;
import com.ruoyi.stock.experience.domain.ExperienceRecord;
import com.ruoyi.stock.experience.service.IExperienceRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 体验金使用记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@Service
public class ExperienceRecordServiceImpl implements IExperienceRecordService
{
    @Autowired
    private ExperienceRecordMapper experienceRecordMapper;

    /**
     * 查询体验金使用记录
     *
     * @param id 体验金使用记录主键
     * @return 体验金使用记录
     */
    @Override
    public ExperienceRecord selectExperienceRecordById(Long id) {
        return experienceRecordMapper.selectById(id);
    }

    /**
     * 查询体验金使用记录列表
     *
     * @param body 体验金使用记录
     * @return 体验金使用记录
     */
    @Override
    public List<ExperienceRecord> selectExperienceRecordList(ExperienceRecord body) {
        QueryWrapper<ExperienceRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(body.getMemberId()), "b.member_id", body.getMemberId());
        ew.like(LogicUtils.isNotNull(body.getMemberPhone()), "m.phone", body.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(body.getUserId()), "u.user_id", body.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(body.getMarketId()), "b.market_id", body.getMarketId());
        ew.orderByDesc("b.create_time");

        ew.eq(LogicUtils.isNotNull(body.getSource()), "b.source", body.getSource());
        ew.eq(LogicUtils.isNotNull(body.getSourceId()), "b.source_id", body.getSourceId());
        return experienceRecordMapper.selectByList(ew);
    }

    /**
     * 新增体验金使用记录
     *
     * @param experienceRecord 体验金使用记录
     * @return 结果
     */
    @Override
    public int insertExperienceRecord(ExperienceRecord experienceRecord)
    {
        experienceRecord.setCreateTime(DateUtils.getNowDate());
        return experienceRecordMapper.insert(experienceRecord);
    }

    /**
     * 修改体验金使用记录
     *
     * @param experienceRecord 体验金使用记录
     * @return 结果
     */
    @Override
    public int updateExperienceRecord(ExperienceRecord experienceRecord)
    {
        experienceRecord.setUpdateTime(DateUtils.getNowDate());
        return experienceRecordMapper.updateById(experienceRecord);
    }

    /**
     * 批量删除体验金使用记录
     *
     * @param ids 需要删除的体验金使用记录主键
     * @return 结果
     */
    @Override
    public int deleteExperienceRecordByIds(List<Long> ids)
    {
        QueryWrapper<ExperienceRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return experienceRecordMapper.delete(ew);
    }

    /**
     * 删除体验金使用记录信息
     *
     * @param id 体验金使用记录主键
     * @return 结果
     */
    @Override
    public int deleteExperienceRecordById(Long id)
    {
        return experienceRecordMapper.deleteById(id);
    }
}
