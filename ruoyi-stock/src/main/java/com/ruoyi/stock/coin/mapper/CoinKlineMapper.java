package com.ruoyi.stock.coin.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.coin.domain.CoinKline;
import org.apache.ibatis.annotations.Delete;

/**
 * 虚拟币K线Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface CoinKlineMapper extends BaseMapper<CoinKline> {
    @Delete("delete from coin_kline where symbol = #{symbol}")
    int deleteBySymbol(String symbol);
}
