package com.ruoyi.stock.stock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.stock.mapper.StockLevelMapper;
import com.ruoyi.stock.stock.domain.StockLevel;
import com.ruoyi.stock.stock.service.IStockLevelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 杠杆配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-16
 */
@Service
public class StockLevelServiceImpl implements IStockLevelService
{
    @Autowired
    private StockLevelMapper stockLevelMapper;

    /**
     * 查询杠杆配置
     *
     * @param id 杠杆配置主键
     * @return 杠杆配置
     */
    @Override
    public StockLevel selectStockLevelById(Long id) {
        return stockLevelMapper.selectById(id);
    }

    /**
     * 查询杠杆配置列表
     *
     * @param stockLevel 杠杆配置
     * @return 杠杆配置
     */
    @Override
    public List<StockLevel> selectStockLevelList(StockLevel stockLevel) {
        QueryWrapper<StockLevel> ew = new QueryWrapper<>();
        ew.setEntity(stockLevel);
        ew.orderByAsc("level");
        return stockLevelMapper.selectList(ew);
    }

    /**
     * 新增杠杆配置
     *
     * @param stockLevel 杠杆配置
     * @return 结果
     */
    @Override
    public int insertStockLevel(StockLevel stockLevel)
    {
        stockLevel.setCreateTime(DateUtils.getNowDate());
        return stockLevelMapper.insert(stockLevel);
    }

    /**
     * 修改杠杆配置
     *
     * @param stockLevel 杠杆配置
     * @return 结果
     */
    @Override
    public int updateStockLevel(StockLevel stockLevel)
    {
        stockLevel.setUpdateTime(DateUtils.getNowDate());
        return stockLevelMapper.updateById(stockLevel);
    }

    /**
     * 批量删除杠杆配置
     *
     * @param ids 需要删除的杠杆配置主键
     * @return 结果
     */
    @Override
    public int deleteStockLevelByIds(List<Long> ids)
    {
        QueryWrapper<StockLevel> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return stockLevelMapper.delete(ew);
    }

    /**
     * 删除杠杆配置信息
     *
     * @param id 杠杆配置主键
     * @return 结果
     */
    @Override
    public int deleteStockLevelById(Long id)
    {
        return stockLevelMapper.deleteById(id);
    }
}
