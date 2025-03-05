package com.ruoyi.stock.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.StockDataSourceEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.mapper.MarketMapper;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.domain.StockHis;
import com.ruoyi.stock.stock.mapper.StockHisMapper;
import com.ruoyi.stock.stock.mapper.StockMapper;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 股票产品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private StockHisMapper stockHisMapper;

    /**
     * 查询股票产品
     *
     * @param id 股票产品主键
     * @return 股票产品
     */
    @Override
    public Stock selectStockById(Long id) {
        Stock stock = stockMapper.selectById(id);
        if (LogicUtils.isNull(stock)) {
            return stock;
        }
        return converts(Collections.singletonList(stock)).get(0);
    }

    @Override
    public Stock selectStock(String code) {
        QueryWrapper<Stock> ew = new QueryWrapper<>();
        ew.eq("gid", code);
        List<Stock> stocks = stockMapper.selectList(ew);
        if (LogicUtils.isNotEmpty(stocks)) {
            return converts(stocks).get(0);
        }
        ew = new QueryWrapper<>();
        ew.eq("code", code);
        stocks = stockMapper.selectList(ew);
        LogicUtils.assertNotEmpty(stocks,"找不到股票,请使用股票代码或股票GID");
        LogicUtils.assertEquals(stocks.size(), 1, "使用股票代码查询到多支股票, 请使用股票GID查询");
        return converts(stocks).get(0);
    }

    @Override
    public Stock selectStockByCode(String code) {
        List<Stock> stocks = selectStockByCode(Collections.singletonList(code));
        if (LogicUtils.isEmpty(stocks)) {
            return null;
        }
        return stocks.get(0);
    }

    @Override
    public Stock selectStockByGid(String gid) {
        List<Stock> stocks = selectStockByGid(Collections.singletonList(gid));
        if (LogicUtils.isEmpty(stocks)) {
            return null;
        }
        return stocks.get(0);
    }

    @Override
    public List<Stock> selectStockByCode(List<String> code) {
        QueryWrapper<Stock> ew = new QueryWrapper<>();
        ew.in("code", code);
        return converts(stockMapper.selectList(ew));
    }

    @Override
    public List<Stock> selectStockByGid(List<String> gid) {
        QueryWrapper<Stock> ew = new QueryWrapper<>();
        ew.in("gid", gid);
        return converts(stockMapper.selectList(ew));
    }

    /**
     * 查询股票产品列表
     *
     * @param stock 股票产品
     * @return 股票产品
     */
    @Override
    public List<Stock> selectStockList(Stock stock) {
        QueryWrapper<Stock> ew = new QueryWrapper<>();
        ew.setEntity(stock);
        ew.orderByDesc("create_time");
        List<Stock> stocks = stockMapper.selectList(ew);

        return converts(stocks);
    }

    /**
     * 新增股票产品
     *
     * @param stock 股票产品
     * @return 结果
     */
    @Override
    public int insertStock(Stock stock) {
        stock.setCreateTime(DateUtils.getNowDate());
        return stockMapper.insert(stock);
    }

    /**
     * 修改股票产品
     *
     * @param stock 股票产品
     * @return 结果
     */
    @Override
    public int updateStock(Stock stock) {
        stock.setUpdateTime(DateUtils.getNowDate());
        return stockMapper.updateById(stock);
    }

    /**
     * 批量删除股票产品
     *
     * @param ids 需要删除的股票产品主键
     * @return 结果
     */
    @Override
    public int deleteStockByIds(List<Long> ids) {
        QueryWrapper<Stock> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return stockMapper.delete(ew);
    }

    /**
     * 删除股票产品信息
     *
     * @param id 股票产品主键
     * @return 结果
     */
    @Override
    public int deleteStockById(Long id) {
        return stockMapper.deleteById(id);
    }

    /**
     * 兼容A股
     *
     * @param records
     * @return
     */
    private List<Stock> converts(List<Stock> records) {
        return records;


        // A股附加逻辑
//        Map<Long, Market> map = new HashMap<>();
//
//        List<String> gids = new ArrayList<>();
//        for (Stock record : records) {
//            Market market = map.computeIfAbsent(record.getMarketId(), marketMapper::selectById);
//            if (StockDataSourceEnum.getEnum(market.getDataSourceMark()).isStockHis()) {
//                gids.add(record.getGid());
//            }
//        }
//        if (LogicUtils.isEmpty(gids)) {
//            return records;
//        }
//        QueryWrapper<StockHis> ew = new QueryWrapper<>();
//        ew.in("stock_full_code", gids);
//        List<StockHis> stockHisList = stockHisMapper.selectList(ew);
//        if (LogicUtils.isEmpty(stockHisList)) {
//            return records;
//        }
//        StockHis empty = createEmpty();
//        Map<String, StockHis> stockHisMap = stockHisList.stream()
//                .collect(Collectors.toMap(StockHis::getStockFullCode, s -> s));
//        for (Stock record : records) {
//            Market market = map.computeIfAbsent(record.getMarketId(), marketMapper::selectById);
//            if ( StockDataSourceEnum.getEnum(market.getDataSourceMark()).isStockHis()) {
//                StockHis stockHis = stockHisMap.computeIfAbsent(record.getGid(), key -> empty);
//                record.setLast(stockHis.getZuix());
//                record.setChg(stockHis.getZdf());
//                record.setChgPct(stockHis.getZzd());
//                record.setOpen(stockHis.getJk());
//                record.setClose(stockHis.getZuos());
//                record.setHigh(stockHis.getZg());
//                record.setLow(stockHis.getZd());
//                record.setAmounts(stockHis.getCje());
//                record.setVolume(stockHis.getCjl());
//            }
//        }
//        return records;
    }

    private static StockHis createEmpty() {
        StockHis stockHis = new StockHis();
        stockHis.setZuix(new BigDecimal("0"));
        stockHis.setZuos(new BigDecimal("0"));
        stockHis.setZdf(new BigDecimal("0"));
        stockHis.setZzd(new BigDecimal("0"));
        stockHis.setJk(new BigDecimal("0"));
        stockHis.setZg(new BigDecimal("0"));
        stockHis.setZd(new BigDecimal("0"));
        stockHis.setZt(new BigDecimal("0"));
        stockHis.setCjl(new BigDecimal("0"));
        stockHis.setCje(new BigDecimal("0"));
        return stockHis;
    }
}
