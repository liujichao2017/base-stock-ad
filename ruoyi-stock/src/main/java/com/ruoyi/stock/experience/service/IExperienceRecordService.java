package com.ruoyi.stock.experience.service;

import java.util.List;
import com.ruoyi.stock.experience.domain.ExperienceRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 体验金使用记录Service接口
 *
 * @author ruoyi
 * @date 2024-12-26
 */
public interface IExperienceRecordService
{
    /**
     * 查询体验金使用记录
     *
     * @param id 体验金使用记录主键
     * @return 体验金使用记录
     */
    public ExperienceRecord selectExperienceRecordById(Long id);

    /**
     * 查询体验金使用记录列表
     *
     * @param experienceRecord 体验金使用记录
     * @return 体验金使用记录集合
     */
    public List<ExperienceRecord> selectExperienceRecordList(ExperienceRecord experienceRecord);

    /**
     * 新增体验金使用记录
     *
     * @param experienceRecord 体验金使用记录
     * @return 结果
     */
    public int insertExperienceRecord(ExperienceRecord experienceRecord);

    /**
     * 修改体验金使用记录
     *
     * @param experienceRecord 体验金使用记录
     * @return 结果
     */
    public int updateExperienceRecord(ExperienceRecord experienceRecord);

    /**
     * 批量删除体验金使用记录
     *
     * @param ids 需要删除的体验金使用记录主键集合
     * @return 结果
     */
    public int deleteExperienceRecordByIds(List<Long> ids);

    /**
     * 删除体验金使用记录信息
     *
     * @param id 体验金使用记录主键
     * @return 结果
     */
    public int deleteExperienceRecordById(Long id);
}
