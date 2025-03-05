package com.ruoyi.stock.coin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.coin.domain.CoinContract;
import org.apache.ibatis.annotations.Param;

/**
 * 虚拟币合约Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface CoinContractMapper extends BaseMapper<CoinContract> {

    List<CoinContract> selectByList(@Param("ew") QueryWrapper<CoinContract> ew);

}
