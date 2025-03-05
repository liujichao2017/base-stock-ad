package com.ruoyi.stock.ai.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import org.apache.ibatis.annotations.Param;

/**
 * AI交易记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface AiTradeRecordMapper extends BaseMapper<AiTradeRecord> {

    List<AiTradeRecord> selectByList(@Param("ew") QueryWrapper<AiTradeRecord> ew);
}
