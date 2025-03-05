package com.ruoyi.stock.experience.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.experience.domain.Experience;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体验金Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-26
 */
public interface ExperienceMapper extends BaseMapper<Experience> {


    @Select("select * from experience " +
            "where member_id = #{memberId} and market_id = #{marketId} and deadline > now() " +
            "and count > use_count and amt > use_amt order by create_time asc")
    List<Experience> getEnableExperience(@Param("memberId") Long memberId, @Param("marketId") Long marketId);

    @Update("update experience set use_amt = use_amt + #{useAmt},use_count = use_count + 1 " +
            "where use_amt+#{useAmt} <= amt and use_count+1 <= count and deadline >= now() and id = #{id}")
    int addUseAmt(@Param("id") Long id, @Param("useAmt") BigDecimal useAmt);

    List<Experience> selectByList(@Param("ew") QueryWrapper<Experience> ew);

    @Update("update experience set recharge_amt = recharge_amt + #{amt},lock_status=if(need_recharge_amt <= recharge_amt + #{amt},'0','1') where id =#{id}")
    int recharge(@Param("id") Long id, @Param("amt") BigDecimal amt);
}
