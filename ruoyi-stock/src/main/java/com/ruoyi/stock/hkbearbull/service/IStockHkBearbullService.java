package com.ruoyi.stock.hkbearbull.service;

import java.util.List;
import com.ruoyi.stock.hkbearbull.domain.StockHkBearbull;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 牛熊市股Service接口
 *
 * @author ruoyi
 * @date 2024-12-13
 */
public interface IStockHkBearbullService
{
    /**
     * 查询牛熊市股
     *
     * @param sym 牛熊市股主键
     * @return 牛熊市股
     */
    public StockHkBearbull selectStockHkBearbullBySym(String sym);

    /**
     * 查询牛熊市股列表
     *
     * @param stockHkBearbull 牛熊市股
     * @return 牛熊市股集合
     */
    public List<StockHkBearbull> selectStockHkBearbullList(StockHkBearbull stockHkBearbull);

    /**
     * 新增牛熊市股
     *
     * @param stockHkBearbull 牛熊市股
     * @return 结果
     */
    public int insertStockHkBearbull(StockHkBearbull stockHkBearbull);

    /**
     * 修改牛熊市股
     *
     * @param stockHkBearbull 牛熊市股
     * @return 结果
     */
    public int updateStockHkBearbull(StockHkBearbull stockHkBearbull);

    /**
     * 批量删除牛熊市股
     *
     * @param syms 需要删除的牛熊市股主键集合
     * @return 结果
     */
    public int deleteStockHkBearbullBySyms(List<String> syms);

    /**
     * 删除牛熊市股信息
     *
     * @param sym 牛熊市股主键
     * @return 结果
     */
    public int deleteStockHkBearbullBySym(String sym);
}
