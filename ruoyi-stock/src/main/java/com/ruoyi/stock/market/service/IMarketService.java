package com.ruoyi.stock.market.service;

import java.util.List;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 市场Service接口
 *
 * @author ruoyi
 * @date 2024-11-18
 */
public interface IMarketService
{
    /**
     * 查询市场
     *
     * @param id 市场主键
     * @return 市场
     */
    public Market selectMarketById(Long id);

    /**
     * 查询市场列表
     *
     * @param market 市场
     * @return 市场集合
     */
    public List<Market> selectMarketList(Market market);

    /**
     * 新增市场
     *
     * @param market 市场
     * @return 结果
     */
    public int insertMarket(Market market);

    /**
     * 修改市场
     *
     * @param market 市场
     * @return 结果
     */
    public int updateMarket(Market market);

    /**
     * 批量删除市场
     *
     * @param ids 需要删除的市场主键集合
     * @return 结果
     */
    public int deleteMarketByIds(List<Long> ids);

    /**
     * 删除市场信息
     *
     * @param id 市场主键
     * @return 结果
     */
    public int deleteMarketById(Long id);
}
