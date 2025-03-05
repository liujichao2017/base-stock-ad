package com.ruoyi.stock.fund.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.fund.domain.FundStockRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 基金股票子项Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-16
 */
public interface FundStockRecordMapper extends BaseMapper<FundStockRecord> {

    List<FundStockRecord> selectByList(@Param("ew") QueryWrapper<FundStockRecord> ew);
}
