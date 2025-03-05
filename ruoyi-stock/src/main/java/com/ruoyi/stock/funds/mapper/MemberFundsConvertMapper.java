package com.ruoyi.stock.funds.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.funds.domain.MemberFundsConvert;
import org.apache.ibatis.annotations.Param;

/**
 * 兑换记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberFundsConvertMapper extends BaseMapper<MemberFundsConvert> {

    List<MemberFundsConvert> selectByList(@Param("ew") QueryWrapper<MemberFundsConvert> ew);
}
