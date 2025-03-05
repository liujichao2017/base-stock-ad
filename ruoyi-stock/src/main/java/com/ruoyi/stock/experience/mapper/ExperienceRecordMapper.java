package com.ruoyi.stock.experience.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import com.ruoyi.stock.experience.domain.ExperienceRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 体验金使用记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-26
 */
public interface ExperienceRecordMapper extends BaseMapper<ExperienceRecord> {

    @Select("select sum(amt) from experience_record where source = #{source} and source_id = #{sourceId}")
    BigDecimal queryUseAmt(@Param("source") String source, @Param("sourceId") Long sourceId);

    List<ExperienceRecord> selectByList(@Param("ew") QueryWrapper<ExperienceRecord> ew);
}
