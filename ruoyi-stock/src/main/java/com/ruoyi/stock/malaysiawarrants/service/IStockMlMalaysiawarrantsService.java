package com.ruoyi.stock.malaysiawarrants.service;

import java.util.List;
import com.ruoyi.stock.malaysiawarrants.domain.StockMlMalaysiawarrants;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * malaysiawarrantsService接口
 *
 * @author ruoyi
 * @date 2025-01-02
 */
public interface IStockMlMalaysiawarrantsService
{
    /**
     * 查询malaysiawarrants
     *
     * @param dwSymbol malaysiawarrants主键
     * @return malaysiawarrants
     */
    public StockMlMalaysiawarrants selectStockMlMalaysiawarrantsByDwSymbol(String dwSymbol);

    /**
     * 查询malaysiawarrants列表
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return malaysiawarrants集合
     */
    public List<StockMlMalaysiawarrants> selectStockMlMalaysiawarrantsList(StockMlMalaysiawarrants stockMlMalaysiawarrants);

    /**
     * 新增malaysiawarrants
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return 结果
     */
    public int insertStockMlMalaysiawarrants(StockMlMalaysiawarrants stockMlMalaysiawarrants);

    /**
     * 修改malaysiawarrants
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return 结果
     */
    public int updateStockMlMalaysiawarrants(StockMlMalaysiawarrants stockMlMalaysiawarrants);

    /**
     * 批量删除malaysiawarrants
     *
     * @param dwSymbols 需要删除的malaysiawarrants主键集合
     * @return 结果
     */
    public int deleteStockMlMalaysiawarrantsByDwSymbols(List<String> dwSymbols);

    /**
     * 删除malaysiawarrants信息
     *
     * @param dwSymbol malaysiawarrants主键
     * @return 结果
     */
    public int deleteStockMlMalaysiawarrantsByDwSymbol(String dwSymbol);
}
