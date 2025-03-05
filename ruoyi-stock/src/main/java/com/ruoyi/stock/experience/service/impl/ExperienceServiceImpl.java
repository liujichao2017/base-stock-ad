package com.ruoyi.stock.experience.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.config.domain.DataConfig;
import com.ruoyi.stock.config.service.IDataConfigService;
import com.ruoyi.stock.experience.domain.Experience;
import com.ruoyi.stock.experience.domain.ExperienceRecord;
import com.ruoyi.stock.experience.mapper.ExperienceMapper;
import com.ruoyi.stock.experience.mapper.ExperienceRecordMapper;
import com.ruoyi.stock.experience.service.IExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 体验金Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@Service
@Slf4j
public class ExperienceServiceImpl implements IExperienceService {
    @Autowired
    private ExperienceMapper experienceMapper;
    @Autowired
    private ExperienceRecordMapper experienceRecordMapper;
    @Autowired
    private IDataConfigService dataConfigService;

    /**
     * 查询体验金
     *
     * @param id 体验金主键
     * @return 体验金
     */
    @Override
    public Experience selectExperienceById(Long id) {
        return experienceMapper.selectById(id);
    }

    /**
     * 查询体验金列表
     *
     * @param body 体验金
     * @return 体验金
     */
    @Override
    public List<Experience> selectExperienceList(Experience body) {
        QueryWrapper<Experience> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(body.getMemberId()), "b.member_id", body.getMemberId());
        ew.like(LogicUtils.isNotNull(body.getMemberPhone()), "m.phone", body.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(body.getUserId()), "u.user_id", body.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(body.getMarketId()), "b.market_id", body.getMarketId());
        ew.orderByDesc("b.create_time");

        return experienceMapper.selectByList(ew);
    }

    /**
     * 新增体验金
     *
     * @param experience 体验金
     * @return 结果
     */
    @Override
    public int insertExperience(Experience experience) {
        if (LogicUtils.isNull(experience.getNeedRechargeAmt())
                || LogicUtils.isEquals(experience.getNeedRechargeAmt(), BigDecimal.ZERO)) {
            experience.setLockStatus("0");
        }
        experience.setCreateTime(DateUtils.getNowDate());
        return experienceMapper.insert(experience);
    }

    /**
     * 修改体验金
     *
     * @param experience 体验金
     * @return 结果
     */
    @Override
    public int updateExperience(Experience experience) {
        Experience db = selectExperienceById(experience.getId());
        LogicUtils.assertNotNull(db, "记录不存在");
        LogicUtils.assertTrue(experience.getAmt().compareTo(db.getUseAmt()) >= 0, "体验金要大于等于已使用金额");
        LogicUtils.assertTrue(experience.getCount() >= db.getUseCount(), "使用次数要大于等于已使用次数");
        experience.setUpdateTime(DateUtils.getNowDate());
        return experienceMapper.updateById(experience);
    }

    /**
     * 批量删除体验金
     *
     * @param ids 需要删除的体验金主键
     * @return 结果
     */
    @Override
    public int deleteExperienceByIds(List<Long> ids) {
        QueryWrapper<Experience> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return experienceMapper.delete(ew);
    }

    /**
     * 删除体验金信息
     *
     * @param id 体验金主键
     * @return 结果
     */
    @Override
    public int deleteExperienceById(Long id) {
        return experienceMapper.deleteById(id);
    }


    /**
     * @return Map<MarketId, EnableAmt>
     */
    @Override
    public Map<Long, Map<Long, BigDecimal>> queryEnableAmt(List<Long> memberIds) {
        QueryWrapper<Experience> ew = new QueryWrapper<>();
        ew.in("member_id", memberIds);
        ew.gtSql("count", "use_count");
        ew.gtSql("amt", "use_amt");
        ew.gtSql("deadline", "now()");
        ew.eq("lock_status", "0");
        List<Experience> list = experienceMapper.selectList(ew);
        if (LogicUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<Long, Map<Long, BigDecimal>> map = new HashMap<>();
        for (Experience experience : list) {
            Map<Long, BigDecimal> marketMap = map.computeIfAbsent(experience.getMemberId(), k -> new HashMap<>());
            BigDecimal amt = marketMap.getOrDefault(experience.getMarketId(), BigDecimal.ZERO);
            BigDecimal experienceAmt = experience.getAmt().subtract(experience.getUseAmt());
            marketMap.put(experience.getMarketId(), amt.add(experienceAmt));
        }
        return map;
    }

    @Override
    public BigDecimal use(Long marketId, Long memberId, FundsSourceEnum fundsSourceEnum, Long sourceId,
            BigDecimal needAmt) {
        BigDecimal useAmt = null;
        try {
            Map<Long, Map<Long, BigDecimal>> memberMap = queryEnableAmt(Collections.singletonList(memberId));
            if (LogicUtils.isEmpty(memberMap)) {
                return BigDecimal.ZERO;
            }
            Map<Long, BigDecimal> map = memberMap.getOrDefault(memberId, Collections.emptyMap());
            BigDecimal enableAmt = map.getOrDefault(marketId, BigDecimal.ZERO);
            if (enableAmt.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            List<Experience> enableExperiences = experienceMapper.getEnableExperience(memberId, marketId);
            useAmt = needAmt;
            if (enableAmt.compareTo(useAmt) < 0) {
                useAmt = enableAmt;
            }

            ExperienceRecord entity = new ExperienceRecord();
            entity.setSource(fundsSourceEnum.getType());
            entity.setSourceId(sourceId);
            entity.setMemberId(memberId);
            entity.setMarketId(marketId);
            entity.setAmt(useAmt);
            LogicUtils.assertEquals(experienceRecordMapper.insert(entity), 1, "内部异常");

            BigDecimal lave = useAmt;
            for (Experience enableExperience : enableExperiences) {
                lave = useExperience(enableExperience, lave);
                if (lave.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("使用体验金失败: ", e);
            return BigDecimal.ZERO;
        }
        return useAmt;
    }

    @Override
    public BigDecimal queryUseAmt(FundsSourceEnum fundsSourceEnum, Long sourceId) {
        BigDecimal useAmt = experienceRecordMapper.queryUseAmt(fundsSourceEnum.getType(), sourceId);
        return useAmt != null ? useAmt : BigDecimal.ZERO;
    }

    @Override
    public void recharge(Long memberId, Long marketId, BigDecimal amt) {
        QueryWrapper<Experience> ew = new QueryWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("market_id", marketId);
        ew.eq("lock_status", "1");
        ew.orderByAsc("create_time");
        List<Experience> list = experienceMapper.selectList(ew);
        if (LogicUtils.isEmpty(list)) {
            return;
        }

        for (Experience experience : list) {
            if (amt.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            BigDecimal needAmt = experience.getNeedRechargeAmt().subtract(experience.getRechargeAmt());
            BigDecimal sub = amt;
            if (needAmt.compareTo(amt) > 0) {
                sub = needAmt.subtract(amt);
                amt = BigDecimal.ZERO;
            }
            if (needAmt.compareTo(amt) <= 0) {
                amt = amt.subtract(needAmt);
                sub = needAmt;
            }
            LogicUtils.assertEquals(experienceMapper.recharge(experience.getId(), sub), 1, "数据异常");
        }
    }

    @Override
    public void rollback(FundsSourceEnum fundsSourceEnum, Long sourceId) {
        QueryWrapper<ExperienceRecord> ew = new QueryWrapper<>();
        ew.eq("source", fundsSourceEnum.getType());
        ew.eq("source_id", sourceId);
        List<ExperienceRecord> list = experienceRecordMapper.selectList(ew);
        if (LogicUtils.isEmpty(list)) {
            return;
        }
        ExperienceRecord experienceRecord = list.get(0);

        List<Long> recordId = new ArrayList<>();
        BigDecimal amt = BigDecimal.ZERO;
        for (ExperienceRecord record : list) {
            recordId.add(record.getId());
            amt = amt.add(record.getAmt());
        }

        LogicUtils.assertEquals(experienceRecordMapper.deleteBatchIds(recordId), recordId.size(), "内部异常");

        DataConfig dataConfig = new DataConfig();
        dataConfig.setGroup("experience");
        dataConfig.setType("str");
        dataConfig.setKey("experience_rallback_day");
        dataConfig.setVal("3");
        dataConfig.setRemark("退回的体验金保留的天数");
        String day = dataConfigService.getByStr(dataConfig);

        Experience experience = new Experience();
        experience.setMemberId(experienceRecord.getMemberId());
        experience.setMarketId(experienceRecord.getMarketId());
        experience.setAmt(amt);
        experience.setUseAmt(new BigDecimal("0"));
        experience.setCount(1L);
        experience.setUseCount(0L);
        experience.setDeadline(DateUtils.getDate(Integer.parseInt(day)));

        LogicUtils.assertEquals(experienceMapper.insert(experience), 1, "内部异常");
    }

    private BigDecimal useExperience(Experience experience, BigDecimal needAmt) {
        BigDecimal enableAmt = experience.getAmt().subtract(experience.getUseAmt());
        BigDecimal useAmt = needAmt;
        BigDecimal lave = BigDecimal.ZERO;
        if (enableAmt.compareTo(useAmt) < 0) {
            useAmt = enableAmt;
            lave = needAmt.subtract(enableAmt);
        }
        LogicUtils.assertEquals(experienceMapper.addUseAmt(experience.getId(), useAmt), 1, "内部异常");
        return lave;
    }

}
