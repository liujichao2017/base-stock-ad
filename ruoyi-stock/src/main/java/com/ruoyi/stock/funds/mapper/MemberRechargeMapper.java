package com.ruoyi.stock.funds.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.funds.domain.MemberRecharge;
import org.apache.ibatis.annotations.Param;

/**
 * 客户充值Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberRechargeMapper extends BaseMapper<MemberRecharge> {

    List<MemberRecharge> selectByList(@Param("ew") QueryWrapper<MemberRecharge> ew);
}
