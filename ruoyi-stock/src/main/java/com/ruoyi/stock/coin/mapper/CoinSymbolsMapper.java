package com.ruoyi.stock.coin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.coin.domain.CoinSymbols;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 交易对Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface CoinSymbolsMapper extends BaseMapper<CoinSymbols> {

    @Select("select * from coin_symbols where symbol = #{symbol}")
    CoinSymbols selectBySymbol(@Param("symbol") String symbol);

    @Delete("delete from coin_symbols where symbol = #{symbol}")
    int deleteBySymbol(@Param("symbol") String symbol);

    @Delete("delete from coin_trade_details where symbol = #{symbol}")
    void deleteByTradeDetails(@Param("symbol") String symbol);
}
