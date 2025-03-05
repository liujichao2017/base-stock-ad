package com.ruoyi.stock.stock.service;

import java.util.List;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股票产品Service接口
 *
 * @author ruoyi
 * @date 2024-11-20
 */
public interface IStockService
{
    /**
     * 查询股票产品
     *
     * @param id 股票产品主键
     * @return 股票产品
     */
    public Stock selectStockById(Long id);

    Stock selectStock(String code);

    /**
     * 查询股票产品
     *
     * @param code 股票ID
     * @return 股票产品
     */
    public Stock selectStockByCode(String code);

    /**
     * 查询股票产品
     *
     * @param gid 股票GID
     * @return 股票产品
     */
    public Stock selectStockByGid(String gid);

    /**
     * 查询股票产品
     *
     * @param code 股票ID
     * @return 股票产品
     */
    public List<Stock> selectStockByCode(List<String> code);

    /**
     * 查询股票产品
     *
     * @param gid 股票GID
     * @return 股票产品
     */
    public List<Stock> selectStockByGid(List<String> gid);


    /**
     * 查询股票产品列表
     *
     * @param stock 股票产品
     * @return 股票产品集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 新增股票产品
     *
     * @param stock 股票产品
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改股票产品
     *
     * @param stock 股票产品
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 批量删除股票产品
     *
     * @param ids 需要删除的股票产品主键集合
     * @return 结果
     */
    public int deleteStockByIds(List<Long> ids);

    /**
     * 删除股票产品信息
     *
     * @param id 股票产品主键
     * @return 结果
     */
    public int deleteStockById(Long id);
}
