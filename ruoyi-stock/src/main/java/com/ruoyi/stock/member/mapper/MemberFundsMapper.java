package com.ruoyi.stock.member.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.member.domain.MemberFunds;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 会员资金Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberFundsMapper extends BaseMapper<MemberFunds> {
    // 可用资金
    @Update("update member_funds set enable_amt = enable_amt + #{amt} where id = #{id}")
    int addEnableAmtByNegative(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set enable_amt = enable_amt - #{amt} where id = #{id}")
    int subEnableAmtByNegative(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set enable_amt = enable_amt + #{amt} where id = #{id}")
    int addEnableAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set enable_amt = enable_amt - #{amt} where id = #{id} and enable_amt - #{amt} >= 0")
    int subEnableAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    // 占用资金
    @Update("update member_funds set occupancy_amt = occupancy_amt + #{amt} where id = #{id}")
    int addOccupancyAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set occupancy_amt = occupancy_amt - #{amt} where id = #{id} and occupancy_amt - #{amt} >= 0")
    int subOccupancyAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    // 冻结资金
    @Update("update member_funds set freeze_amt = freeze_amt + #{amt} where id = #{id}")
    int addFreezeAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set freeze_amt = freeze_amt - #{amt} where id = #{id} and freeze_amt - #{amt} >= 0")
    int subFreezeAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);


    // 收益
    @Update("update member_funds set profit_amt = profit_amt + #{amt} where id = #{id}")
    int addProfitAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);

    @Update("update member_funds set profit_amt = profit_amt - #{amt} where id = #{id} and profit_amt - #{amt} >= 0")
    int subProfitAmt(@Param("amt") BigDecimal amt, @Param("id") Long id);
}
