package com.ruoyi.stock.malaysiawarrants.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.malaysiawarrants.domain.StockMlMalaysiawarrants;
import com.ruoyi.stock.malaysiawarrants.mapper.StockMlMalaysiawarrantsMapper;
import com.ruoyi.stock.malaysiawarrants.service.IStockMlMalaysiawarrantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * malaysiawarrantsService业务层处理
 *
 * @author ruoyi
 * @date 2025-01-02
 */
@Service
public class StockMlMalaysiawarrantsServiceImpl implements IStockMlMalaysiawarrantsService {
    @Autowired
    private StockMlMalaysiawarrantsMapper stockMlMalaysiawarrantsMapper;

    /**
     * 查询malaysiawarrants
     *
     * @param dwSymbol malaysiawarrants主键
     * @return malaysiawarrants
     */
    @Override
    public StockMlMalaysiawarrants selectStockMlMalaysiawarrantsByDwSymbol(String dwSymbol) {
        return stockMlMalaysiawarrantsMapper.selectById(dwSymbol);
    }

    /**
     * 查询malaysiawarrants列表
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return malaysiawarrants
     */
    @Override
    public List<StockMlMalaysiawarrants> selectStockMlMalaysiawarrantsList(
            StockMlMalaysiawarrants stockMlMalaysiawarrants) {
        QueryWrapper<StockMlMalaysiawarrants> ew = new QueryWrapper<>();
        String dwSymbol = stockMlMalaysiawarrants.getDwSymbol();
        stockMlMalaysiawarrants.setDwSymbol(null);
        ew.setEntity(stockMlMalaysiawarrants);
        ew.like(LogicUtils.isNotBlank(dwSymbol), "dw_symbol", dwSymbol);
        ew.orderByDesc("trade_volume");
        return stockMlMalaysiawarrantsMapper.selectList(ew);
    }

    /**
     * 新增malaysiawarrants
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return 结果
     */
    @Override
    public int insertStockMlMalaysiawarrants(StockMlMalaysiawarrants stockMlMalaysiawarrants) {
        return stockMlMalaysiawarrantsMapper.insert(stockMlMalaysiawarrants);
    }

    /**
     * 修改malaysiawarrants
     *
     * @param stockMlMalaysiawarrants malaysiawarrants
     * @return 结果
     */
    @Override
    public int updateStockMlMalaysiawarrants(StockMlMalaysiawarrants stockMlMalaysiawarrants) {
        return stockMlMalaysiawarrantsMapper.updateById(stockMlMalaysiawarrants);
    }

    /**
     * 批量删除malaysiawarrants
     *
     * @param dwSymbols 需要删除的malaysiawarrants主键
     * @return 结果
     */
    @Override
    public int deleteStockMlMalaysiawarrantsByDwSymbols(List<String> dwSymbols) {
        QueryWrapper<StockMlMalaysiawarrants> ew = new QueryWrapper<>();
        ew.in("id", dwSymbols);
        return stockMlMalaysiawarrantsMapper.delete(ew);
    }

    /**
     * 删除malaysiawarrants信息
     *
     * @param dwSymbol malaysiawarrants主键
     * @return 结果
     */
    @Override
    public int deleteStockMlMalaysiawarrantsByDwSymbol(String dwSymbol) {
        return stockMlMalaysiawarrantsMapper.deleteById(dwSymbol);
    }
}
