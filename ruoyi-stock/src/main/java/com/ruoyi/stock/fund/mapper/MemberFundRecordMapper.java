package com.ruoyi.stock.fund.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.fund.domain.MemberFundRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 基金购买记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface MemberFundRecordMapper extends BaseMapper<MemberFundRecord> {
    List<MemberFundRecord> selectByList(@Param("ew") QueryWrapper<MemberFundRecord> ew);
}
