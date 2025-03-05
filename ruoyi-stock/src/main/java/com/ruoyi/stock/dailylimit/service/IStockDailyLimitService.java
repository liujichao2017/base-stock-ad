package com.ruoyi.stock.dailylimit.service;

import java.util.List;
import com.ruoyi.stock.dailylimit.domain.StockDailyLimit;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 涨停列表Service接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface IStockDailyLimitService
{
    /**
     * 查询涨停列表
     *
     * @param id 涨停列表主键
     * @return 涨停列表
     */
    public StockDailyLimit selectStockDailyLimitById(Long id);

    /**
     * 查询涨停列表列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 涨停列表集合
     */
    public List<StockDailyLimit> selectStockDailyLimitList(StockDailyLimit stockDailyLimit);

    /**
     * 新增涨停列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 结果
     */
    public int insertStockDailyLimit(StockDailyLimit stockDailyLimit);

    /**
     * 修改涨停列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 结果
     */
    public int updateStockDailyLimit(StockDailyLimit stockDailyLimit);

    /**
     * 批量删除涨停列表
     *
     * @param ids 需要删除的涨停列表主键集合
     * @return 结果
     */
    public int deleteStockDailyLimitByIds(List<Long> ids);

    /**
     * 删除涨停列表信息
     *
     * @param id 涨停列表主键
     * @return 结果
     */
    public int deleteStockDailyLimitById(Long id);
}
