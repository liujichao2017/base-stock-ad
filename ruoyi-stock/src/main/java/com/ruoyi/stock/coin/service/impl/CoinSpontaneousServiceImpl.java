package com.ruoyi.stock.coin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.coin.domain.CoinSpontaneous;
import com.ruoyi.stock.coin.domain.CoinSymbols;
import com.ruoyi.stock.coin.mapper.CoinKlineMapper;
import com.ruoyi.stock.coin.mapper.CoinSpontaneousMapper;
import com.ruoyi.stock.coin.mapper.CoinSpontaneousRobotMapper;
import com.ruoyi.stock.coin.mapper.CoinSymbolsMapper;
import com.ruoyi.stock.coin.service.ICoinSpontaneousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/**
 * 自发币Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinSpontaneousServiceImpl implements ICoinSpontaneousService {
    @Autowired
    private CoinSpontaneousMapper coinSpontaneousMapper;
    @Autowired
    private CoinSymbolsMapper coinSymbolsMapper;
    @Autowired
    private CoinKlineMapper coinKlineMapper;
    @Autowired
    private CoinSpontaneousRobotMapper coinSpontaneousRobotMapper;

    /**
     * 查询自发币
     *
     * @param id 自发币主键
     * @return 自发币
     */
    @Override
    public CoinSpontaneous selectCoinSpontaneousById(Long id) {
        return coinSpontaneousMapper.selectById(id);
    }

    /**
     * 查询自发币列表
     *
     * @param coinSpontaneous 自发币
     * @return 自发币
     */
    @Override
    public List<CoinSpontaneous> selectCoinSpontaneousList(CoinSpontaneous coinSpontaneous) {
        QueryWrapper<CoinSpontaneous> ew = new QueryWrapper<>();
        ew.setEntity(coinSpontaneous);
        ew.orderByDesc("create_time");
        return coinSpontaneousMapper.selectList(ew);
    }

    /**
     * 新增自发币
     *
     * @param coinSpontaneous 自发币
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCoinSpontaneous(CoinSpontaneous coinSpontaneous) {
        coinSpontaneous.setBcdn(coinSpontaneous.getBcdn().toUpperCase(Locale.ENGLISH));
        coinSpontaneous.setSymbol((coinSpontaneous.getBcdn() + coinSpontaneous.getQcdn()).toLowerCase(Locale.ENGLISH));
        coinSpontaneous.setCreateTime(DateUtils.getNowDate());

        int insert = coinSpontaneousMapper.insert(coinSpontaneous);
        createSpontaneousCoin(coinSpontaneous);

        return insert;
    }


    /**
     * 修改自发币
     *
     * @param coinSpontaneous 自发币
     * @return 结果
     */
    @Override
    public int updateCoinSpontaneous(CoinSpontaneous coinSpontaneous) {
        coinSpontaneous.setUpdateTime(DateUtils.getNowDate());
        return coinSpontaneousMapper.updateById(coinSpontaneous);
    }

    /**
     * 批量删除自发币
     *
     * @param ids 需要删除的自发币主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCoinSpontaneousByIds(List<Long> ids) {
        for (Long id : ids) {
            deleteCoinSpontaneousById(id);
        }
        return ids.size();
    }

    /**
     * 删除自发币信息
     *
     * @param id 自发币主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCoinSpontaneousById(Long id) {
        CoinSpontaneous coinSpontaneous = selectCoinSpontaneousById(id);
        LogicUtils.assertNotNull(coinSpontaneous, "已删除");
        deleteSpontaneousCoin(coinSpontaneous);
        return coinSpontaneousMapper.deleteById(id);
    }

    private void deleteSpontaneousCoin(CoinSpontaneous coinSpontaneous) {
        coinSymbolsMapper.deleteBySymbol(coinSpontaneous.getSymbol());
        coinSymbolsMapper.deleteByTradeDetails(coinSpontaneous.getSymbol());
        coinKlineMapper.deleteBySymbol(coinSpontaneous.getSymbol());
        coinSpontaneousRobotMapper.deleteByCsId(coinSpontaneous.getId());
    }


    private void createSpontaneousCoin(CoinSpontaneous coinSpontaneous) {
        LogicUtils.assertNull(coinSymbolsMapper.selectBySymbol(coinSpontaneous.getSymbol()),
                              "币总已存在: " + coinSpontaneous.getSymbol());
        CoinSymbols coinSymbols = new CoinSymbols();

        coinSymbols.setSymbol(coinSpontaneous.getSymbol());
        coinSymbols.setState("online");
        coinSymbols.setHot(0);

        coinSymbols.setType("2");
        coinSymbols.setBcdn(coinSpontaneous.getBcdn());
        coinSymbols.setQcdn(coinSpontaneous.getQcdn());

        coinSymbols.setTradeState(0);
        coinSymbols.setSn(coinSpontaneous.getBcdn() + "/" + coinSpontaneous.getQcdn());
        coinSymbols.setPrice(new BigDecimal("0"));
        coinSymbols.setOpen(new BigDecimal("0"));
        coinSymbols.setClose(new BigDecimal("0"));
        coinSymbols.setAmount(new BigDecimal("0"));
        coinSymbols.setCounts(0L);
        coinSymbols.setLow(new BigDecimal("0"));
        coinSymbols.setHigh(new BigDecimal("0"));
        coinSymbols.setVol(new BigDecimal("0"));
        coinSymbols.setBid(new BigDecimal("0"));
        coinSymbols.setBidSize(new BigDecimal("0"));
        coinSymbols.setAsk(new BigDecimal("0"));
        coinSymbols.setAskSize(new BigDecimal("0"));
        coinSymbols.setLastSize(new BigDecimal("0"));
        LogicUtils.assertEquals(coinSymbolsMapper.insert(coinSymbols), 1, "创建自发币失败");
    }
}
