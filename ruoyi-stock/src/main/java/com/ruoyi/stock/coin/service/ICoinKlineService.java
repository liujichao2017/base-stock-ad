package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinKline;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币K线Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinKlineService
{
    /**
     * 查询虚拟币K线
     *
     * @param id 虚拟币K线主键
     * @return 虚拟币K线
     */
    public CoinKline selectCoinKlineById(Long id);

    /**
     * 查询虚拟币K线列表
     *
     * @param coinKline 虚拟币K线
     * @return 虚拟币K线集合
     */
    public List<CoinKline> selectCoinKlineList(CoinKline coinKline);

    /**
     * 新增虚拟币K线
     *
     * @param coinKline 虚拟币K线
     * @return 结果
     */
    public int insertCoinKline(CoinKline coinKline);

    /**
     * 修改虚拟币K线
     *
     * @param coinKline 虚拟币K线
     * @return 结果
     */
    public int updateCoinKline(CoinKline coinKline);

    /**
     * 批量删除虚拟币K线
     *
     * @param ids 需要删除的虚拟币K线主键集合
     * @return 结果
     */
    public int deleteCoinKlineByIds(List<Long> ids);

    /**
     * 删除虚拟币K线信息
     *
     * @param id 虚拟币K线主键
     * @return 结果
     */
    public int deleteCoinKlineById(Long id);
}
