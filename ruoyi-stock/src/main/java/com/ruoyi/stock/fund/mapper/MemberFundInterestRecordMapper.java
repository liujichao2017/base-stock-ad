package com.ruoyi.stock.fund.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.fund.domain.MemberFundInterestRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 基金盈利Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface MemberFundInterestRecordMapper extends BaseMapper<MemberFundInterestRecord> {



    @Select("select sum(interest) from member_fund_interest_record where user_fund_record_id = #{userFundRecordId}")
    BigDecimal sumInterest(@Param("userFundRecordId") Long id);
}
