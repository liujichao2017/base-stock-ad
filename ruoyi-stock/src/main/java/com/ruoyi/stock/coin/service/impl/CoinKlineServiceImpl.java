package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinKlineMapper;
import com.ruoyi.stock.coin.domain.CoinKline;
import com.ruoyi.stock.coin.service.ICoinKlineService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 虚拟币K线Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinKlineServiceImpl implements ICoinKlineService
{
    @Autowired
    private CoinKlineMapper coinKlineMapper;

    /**
     * 查询虚拟币K线
     *
     * @param id 虚拟币K线主键
     * @return 虚拟币K线
     */
    @Override
    public CoinKline selectCoinKlineById(Long id) {
        return coinKlineMapper.selectById(id);
    }

    /**
     * 查询虚拟币K线列表
     *
     * @param coinKline 虚拟币K线
     * @return 虚拟币K线
     */
    @Override
    public List<CoinKline> selectCoinKlineList(CoinKline coinKline) {
        QueryWrapper<CoinKline> ew = new QueryWrapper<>();
        ew.setEntity(coinKline);
        ew.orderByDesc("create_time");
        return coinKlineMapper.selectList(ew);
    }

    /**
     * 新增虚拟币K线
     *
     * @param coinKline 虚拟币K线
     * @return 结果
     */
    @Override
    public int insertCoinKline(CoinKline coinKline)
    {
        coinKline.setCreateTime(DateUtils.getNowDate());
        return coinKlineMapper.insert(coinKline);
    }

    /**
     * 修改虚拟币K线
     *
     * @param coinKline 虚拟币K线
     * @return 结果
     */
    @Override
    public int updateCoinKline(CoinKline coinKline)
    {
        coinKline.setUpdateTime(DateUtils.getNowDate());
        return coinKlineMapper.updateById(coinKline);
    }

    /**
     * 批量删除虚拟币K线
     *
     * @param ids 需要删除的虚拟币K线主键
     * @return 结果
     */
    @Override
    public int deleteCoinKlineByIds(List<Long> ids)
    {
        QueryWrapper<CoinKline> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinKlineMapper.delete(ew);
    }

    /**
     * 删除虚拟币K线信息
     *
     * @param id 虚拟币K线主键
     * @return 结果
     */
    @Override
    public int deleteCoinKlineById(Long id)
    {
        return coinKlineMapper.deleteById(id);
    }
}
