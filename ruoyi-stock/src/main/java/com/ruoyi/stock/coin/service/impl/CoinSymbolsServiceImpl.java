package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.coin.domain.CoinSymbols;
import com.ruoyi.stock.coin.service.ICoinSymbolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinSymbolsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 交易对Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinSymbolsServiceImpl implements ICoinSymbolsService
{
    @Autowired
    private CoinSymbolsMapper coinSymbolsMapper;

    /**
     * 查询交易对
     *
     * @param id 交易对主键
     * @return 交易对
     */
    @Override
    public CoinSymbols selectCoinSymbolsById(Long id) {
        return coinSymbolsMapper.selectById(id);
    }

    /**
     * 查询交易对列表
     *
     * @param coinSymbols 交易对
     * @return 交易对
     */
    @Override
    public List<CoinSymbols> selectCoinSymbolsList(CoinSymbols coinSymbols) {
        QueryWrapper<CoinSymbols> ew = new QueryWrapper<>();
        coinSymbols.setQcdn("USDT");
        String symbol = coinSymbols.getSymbol();
        coinSymbols.setSymbol(null);
        ew.setEntity(coinSymbols);
        ew.like(LogicUtils.isNotBlank(symbol), "symbol", symbol);
        ew.orderByDesc("create_time");
        return coinSymbolsMapper.selectList(ew);
    }

    /**
     * 新增交易对
     *
     * @param coinSymbols 交易对
     * @return 结果
     */
    @Override
    public int insertCoinSymbols(CoinSymbols coinSymbols)
    {
        coinSymbols.setCreateTime(DateUtils.getNowDate());
        return coinSymbolsMapper.insert(coinSymbols);
    }

    /**
     * 修改交易对
     *
     * @param coinSymbols 交易对
     * @return 结果
     */
    @Override
    public int updateCoinSymbols(CoinSymbols coinSymbols)
    {
        coinSymbols.setUpdateTime(DateUtils.getNowDate());
        return coinSymbolsMapper.updateById(coinSymbols);
    }

    /**
     * 批量删除交易对
     *
     * @param ids 需要删除的交易对主键
     * @return 结果
     */
    @Override
    public int deleteCoinSymbolsByIds(List<Long> ids)
    {
        QueryWrapper<CoinSymbols> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinSymbolsMapper.delete(ew);
    }

    /**
     * 删除交易对信息
     *
     * @param id 交易对主键
     * @return 结果
     */
    @Override
    public int deleteCoinSymbolsById(Long id)
    {
        return coinSymbolsMapper.deleteById(id);
    }
}
