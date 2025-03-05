package com.ruoyi.stock.hkbearbull.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.hkbearbull.domain.StockHkBearbull;
import com.ruoyi.stock.hkbearbull.mapper.StockHkBearbullMapper;
import com.ruoyi.stock.hkbearbull.service.IStockHkBearbullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 牛熊市股Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-13
 */
@Service
public class StockHkBearbullServiceImpl implements IStockHkBearbullService {
    @Autowired
    private StockHkBearbullMapper stockHkBearbullMapper;

    /**
     * 查询牛熊市股
     *
     * @param sym 牛熊市股主键
     * @return 牛熊市股
     */
    @Override
    public StockHkBearbull selectStockHkBearbullBySym(String sym) {
        return stockHkBearbullMapper.selectById(sym);
    }

    /**
     * 查询牛熊市股列表
     *
     * @param stockHkBearbull 牛熊市股
     * @return 牛熊市股
     */
    @Override
    public List<StockHkBearbull> selectStockHkBearbullList(StockHkBearbull stockHkBearbull) {
        QueryWrapper<StockHkBearbull> ew = new QueryWrapper<>();
        String sym = stockHkBearbull.getSym();
        String desp = stockHkBearbull.getDesp();
        stockHkBearbull.setSym(null);
        stockHkBearbull.setDesp(null);
        ew.setEntity(stockHkBearbull);
        ew.like(LogicUtils.isNotBlank(sym), "sym", sym);
        ew.like(LogicUtils.isNotBlank(desp), "desp", desp);
        ew.orderByDesc("create_time");
        return stockHkBearbullMapper.selectList(ew);
    }

    /**
     * 新增牛熊市股
     *
     * @param stockHkBearbull 牛熊市股
     * @return 结果
     */
    @Override
    public int insertStockHkBearbull(StockHkBearbull stockHkBearbull) {
        stockHkBearbull.setCreateTime(DateUtils.getNowDate());
        return stockHkBearbullMapper.insert(stockHkBearbull);
    }

    /**
     * 修改牛熊市股
     *
     * @param stockHkBearbull 牛熊市股
     * @return 结果
     */
    @Override
    public int updateStockHkBearbull(StockHkBearbull stockHkBearbull) {
        stockHkBearbull.setUpdateTime(DateUtils.getNowDate());
        return stockHkBearbullMapper.updateById(stockHkBearbull);
    }

    /**
     * 批量删除牛熊市股
     *
     * @param ids 需要删除的牛熊市股主键
     * @return 结果
     */
    @Override
    public int deleteStockHkBearbullBySyms(List<String> ids) {
        QueryWrapper<StockHkBearbull> ew = new QueryWrapper<>();
        ew.in("sym", ids);
        return stockHkBearbullMapper.delete(ew);
    }

    /**
     * 删除牛熊市股信息
     *
     * @param sym 牛熊市股主键
     * @return 结果
     */
    @Override
    public int deleteStockHkBearbullBySym(String sym) {
        return stockHkBearbullMapper.deleteById(sym);
    }
}
