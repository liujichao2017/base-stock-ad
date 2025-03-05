package com.ruoyi.stock.dailylimit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.dailylimit.domain.StockDailyLimit;
import com.ruoyi.stock.dailylimit.mapper.StockDailyLimitMapper;
import com.ruoyi.stock.dailylimit.service.IStockDailyLimitService;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 涨停列表Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Service
public class StockDailyLimitServiceImpl implements IStockDailyLimitService {
    @Autowired
    private StockDailyLimitMapper stockDailyLimitMapper;
    @Autowired
    private IStockService stockService;

    /**
     * 查询涨停列表
     *
     * @param id 涨停列表主键
     * @return 涨停列表
     */
    @Override
    public StockDailyLimit selectStockDailyLimitById(Long id) {
        return stockDailyLimitMapper.selectById(id);
    }

    /**
     * 查询涨停列表列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 涨停列表
     */
    @Override
    public List<StockDailyLimit> selectStockDailyLimitList(StockDailyLimit stockDailyLimit) {
        QueryWrapper<StockDailyLimit> ew = new QueryWrapper<>();
        ew.setEntity(stockDailyLimit);
        ew.orderByDesc("create_time");
        List<StockDailyLimit> stockDailyLimits = stockDailyLimitMapper.selectList(ew);
        if(LogicUtils.isNotEmpty(stockDailyLimits)){
            stockDailyLimits.forEach(dl->{
                Stock stock = stockService.selectStock(dl.getStockGid());
                dl.setStockName(stock.getName());
                dl.setStockCode(stock.getCode());
                dl.setLast(stock.getLast());
                dl.setChgPct(stock.getChgPct());
            });
        }
        return stockDailyLimits;
    }

    /**
     * 新增涨停列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 结果
     */
    @Override
    public int insertStockDailyLimit(StockDailyLimit stockDailyLimit) {
        Stock stock = stockService.selectStock(stockDailyLimit.getStockGid());
        LogicUtils.assertNotNull(stock, "股票不存在");
        stockDailyLimit.setMarketId(stock.getMarketId());
        stockDailyLimit.setCreateTime(DateUtils.getNowDate());
        return stockDailyLimitMapper.insert(stockDailyLimit);
    }

    /**
     * 修改涨停列表
     *
     * @param stockDailyLimit 涨停列表
     * @return 结果
     */
    @Override
    public int updateStockDailyLimit(StockDailyLimit stockDailyLimit) {
        stockDailyLimit.setUpdateTime(DateUtils.getNowDate());
        return stockDailyLimitMapper.updateById(stockDailyLimit);
    }

    /**
     * 批量删除涨停列表
     *
     * @param ids 需要删除的涨停列表主键
     * @return 结果
     */
    @Override
    public int deleteStockDailyLimitByIds(List<Long> ids) {
        QueryWrapper<StockDailyLimit> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return stockDailyLimitMapper.delete(ew);
    }

    /**
     * 删除涨停列表信息
     *
     * @param id 涨停列表主键
     * @return 结果
     */
    @Override
    public int deleteStockDailyLimitById(Long id) {
        return stockDailyLimitMapper.deleteById(id);
    }
}
