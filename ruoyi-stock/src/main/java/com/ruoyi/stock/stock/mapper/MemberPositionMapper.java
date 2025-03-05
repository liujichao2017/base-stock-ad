package com.ruoyi.stock.stock.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.stock.domain.MemberPosition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 用户持仓Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-20
 */
public interface MemberPositionMapper extends BaseMapper<MemberPosition> {

    List<MemberPosition> selectByList(@Param("ew") QueryWrapper<MemberPosition> ew);

    @Update("update member_position set sell_time_limit = null where id = #{id}")
    int deleteSellTimeLimit(@Param("id")  Long id);
}
