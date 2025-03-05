package com.ruoyi.stock.coin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.coin.domain.CoinContractDelegation;
import org.apache.ibatis.annotations.Param;

/**
 * 虚拟币合约委托Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface CoinContractDelegationMapper extends BaseMapper<CoinContractDelegation> {

    List<CoinContractDelegation> selectByList(@Param("ew") QueryWrapper<CoinContractDelegation> ew);
}
