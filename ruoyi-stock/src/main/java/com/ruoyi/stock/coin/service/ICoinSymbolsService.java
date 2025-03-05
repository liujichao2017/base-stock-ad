package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinSymbols;

/**
 * 交易对Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinSymbolsService
{
    /**
     * 查询交易对
     *
     * @param id 交易对主键
     * @return 交易对
     */
    public CoinSymbols selectCoinSymbolsById(Long id);

    /**
     * 查询交易对列表
     *
     * @param coinSymbols 交易对
     * @return 交易对集合
     */
    public List<CoinSymbols> selectCoinSymbolsList(CoinSymbols coinSymbols);

    /**
     * 新增交易对
     *
     * @param coinSymbols 交易对
     * @return 结果
     */
    public int insertCoinSymbols(CoinSymbols coinSymbols);

    /**
     * 修改交易对
     *
     * @param coinSymbols 交易对
     * @return 结果
     */
    public int updateCoinSymbols(CoinSymbols coinSymbols);

    /**
     * 批量删除交易对
     *
     * @param ids 需要删除的交易对主键集合
     * @return 结果
     */
    public int deleteCoinSymbolsByIds(List<Long> ids);

    /**
     * 删除交易对信息
     *
     * @param id 交易对主键
     * @return 结果
     */
    public int deleteCoinSymbolsById(Long id);
}
