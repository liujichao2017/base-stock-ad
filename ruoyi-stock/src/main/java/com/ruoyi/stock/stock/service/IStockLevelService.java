package com.ruoyi.stock.stock.service;

import java.util.List;
import com.ruoyi.stock.stock.domain.StockLevel;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 杠杆配置Service接口
 *
 * @author ruoyi
 * @date 2024-12-16
 */
public interface IStockLevelService
{
    /**
     * 查询杠杆配置
     *
     * @param id 杠杆配置主键
     * @return 杠杆配置
     */
    public StockLevel selectStockLevelById(Long id);

    /**
     * 查询杠杆配置列表
     *
     * @param stockLevel 杠杆配置
     * @return 杠杆配置集合
     */
    public List<StockLevel> selectStockLevelList(StockLevel stockLevel);

    /**
     * 新增杠杆配置
     *
     * @param stockLevel 杠杆配置
     * @return 结果
     */
    public int insertStockLevel(StockLevel stockLevel);

    /**
     * 修改杠杆配置
     *
     * @param stockLevel 杠杆配置
     * @return 结果
     */
    public int updateStockLevel(StockLevel stockLevel);

    /**
     * 批量删除杠杆配置
     *
     * @param ids 需要删除的杠杆配置主键集合
     * @return 结果
     */
    public int deleteStockLevelByIds(List<Long> ids);

    /**
     * 删除杠杆配置信息
     *
     * @param id 杠杆配置主键
     * @return 结果
     */
    public int deleteStockLevelById(Long id);
}
