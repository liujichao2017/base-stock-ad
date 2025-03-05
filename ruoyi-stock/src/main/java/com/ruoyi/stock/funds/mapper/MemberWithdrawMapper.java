package com.ruoyi.stock.funds.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.funds.domain.MemberWithdraw;
import org.apache.ibatis.annotations.Param;

/**
 * 用户提现Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberWithdrawMapper extends BaseMapper<MemberWithdraw> {

    List<MemberWithdraw> selectByList(@Param("ew") QueryWrapper<MemberWithdraw> ew);
}
