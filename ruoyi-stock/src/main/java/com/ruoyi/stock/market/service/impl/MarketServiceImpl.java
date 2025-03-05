package com.ruoyi.stock.market.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.market.mapper.MarketMapper;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.service.IMarketService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 市场Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-18
 */
@Service
public class MarketServiceImpl implements IMarketService
{
    @Autowired
    private MarketMapper marketMapper;

    /**
     * 查询市场
     *
     * @param id 市场主键
     * @return 市场
     */
    @Override
    public Market selectMarketById(Long id) {
        return marketMapper.selectById(id);
    }

    /**
     * 查询市场列表
     *
     * @param market 市场
     * @return 市场
     */
    @Override
    public List<Market> selectMarketList(Market market) {
        QueryWrapper<Market> ew = new QueryWrapper<>();
        ew.setEntity(market);
        ew.orderByAsc("sort");
        return marketMapper.selectList(ew);
    }

    /**
     * 新增市场
     *
     * @param market 市场
     * @return 结果
     */
    @Override
    public int insertMarket(Market market)
    {
        if (YNEnum.yes(market.getIsMaster())) {
            Market entity = new Market();
            entity.setIsMaster("0");
            marketMapper.update(entity, null);
        }
        market.setCreateTime(DateUtils.getNowDate());
        return marketMapper.insert(market);
    }

    /**
     * 修改市场
     *
     * @param market 市场
     * @return 结果
     */
    @Override
    public int updateMarket(Market market)
    {
        if (YNEnum.yes(market.getIsMaster())) {
            Market entity = new Market();
            entity.setIsMaster("0");
            marketMapper.update(entity, null);
        }
        if (LogicUtils.isNotNull(market.getMainExchangeRate())) {
            market.setSlaveExchangeRate(computeSlaveExchangeRate(market.getMainExchangeRate()));
        }
        market.setUpdateTime(DateUtils.getNowDate());
        return marketMapper.updateById(market);
    }

    public static BigDecimal computeSlaveExchangeRate(BigDecimal rate) {
        BigDecimal masterAmt = new BigDecimal("1000000000000");
        BigDecimal slaveAmt = masterAmt.divide(rate, 100, 4);
        return slaveAmt.divide(masterAmt, 10, 4);
    }
    /**
     * 批量删除市场
     *
     * @param ids 需要删除的市场主键
     * @return 结果
     */
    @Override
    public int deleteMarketByIds(List<Long> ids)
    {
        QueryWrapper<Market> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return marketMapper.delete(ew);
    }

    /**
     * 删除市场信息
     *
     * @param id 市场主键
     * @return 结果
     */
    @Override
    public int deleteMarketById(Long id)
    {
        return marketMapper.deleteById(id);
    }
}
