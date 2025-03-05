package com.ruoyi.stock.experience.service;

import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.stock.experience.domain.Experience;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 体验金Service接口
 *
 * @author ruoyi
 * @date 2024-12-26
 */
public interface IExperienceService {
    /**
     * 查询体验金
     *
     * @param id 体验金主键
     * @return 体验金
     */
    public Experience selectExperienceById(Long id);

    /**
     * 查询体验金列表
     *
     * @param experience 体验金
     * @return 体验金集合
     */
    public List<Experience> selectExperienceList(Experience experience);

    /**
     * 新增体验金
     *
     * @param experience 体验金
     * @return 结果
     */
    public int insertExperience(Experience experience);

    /**
     * 修改体验金
     *
     * @param experience 体验金
     * @return 结果
     */
    public int updateExperience(Experience experience);

    /**
     * 批量删除体验金
     *
     * @param ids 需要删除的体验金主键集合
     * @return 结果
     */
    public int deleteExperienceByIds(List<Long> ids);

    /**
     * 删除体验金信息
     *
     * @param id 体验金主键
     * @return 结果
     */
    public int deleteExperienceById(Long id);

    Map<Long, Map<Long, BigDecimal>> queryEnableAmt(List<Long> memberIds);

    BigDecimal use(Long marketId, Long memberId, FundsSourceEnum fundsSourceEnum, Long sourceId, BigDecimal needAmt);

    BigDecimal queryUseAmt(FundsSourceEnum fundsSourceEnum, Long sourceId);

    void rollback(FundsSourceEnum fundsSourceEnum, Long sourceId);

    void recharge(Long memberId, Long marketId,BigDecimal amt);
}
