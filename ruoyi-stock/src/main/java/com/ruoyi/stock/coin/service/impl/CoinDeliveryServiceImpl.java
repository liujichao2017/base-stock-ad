package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinDeliveryMapper;
import com.ruoyi.stock.coin.domain.CoinDelivery;
import com.ruoyi.stock.coin.service.ICoinDeliveryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 虚拟币交割方案Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinDeliveryServiceImpl implements ICoinDeliveryService
{
    @Autowired
    private CoinDeliveryMapper coinDeliveryMapper;

    /**
     * 查询虚拟币交割方案
     *
     * @param id 虚拟币交割方案主键
     * @return 虚拟币交割方案
     */
    @Override
    public CoinDelivery selectCoinDeliveryById(Long id) {
        return coinDeliveryMapper.selectById(id);
    }

    /**
     * 查询虚拟币交割方案列表
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 虚拟币交割方案
     */
    @Override
    public List<CoinDelivery> selectCoinDeliveryList(CoinDelivery coinDelivery) {
        QueryWrapper<CoinDelivery> ew = new QueryWrapper<>();
        ew.setEntity(coinDelivery);
        ew.orderByDesc("create_time");
        return coinDeliveryMapper.selectList(ew);
    }

    /**
     * 新增虚拟币交割方案
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 结果
     */
    @Override
    public int insertCoinDelivery(CoinDelivery coinDelivery)
    {
        coinDelivery.setCreateTime(DateUtils.getNowDate());
        return coinDeliveryMapper.insert(coinDelivery);
    }

    /**
     * 修改虚拟币交割方案
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 结果
     */
    @Override
    public int updateCoinDelivery(CoinDelivery coinDelivery)
    {
        coinDelivery.setUpdateTime(DateUtils.getNowDate());
        return coinDeliveryMapper.updateById(coinDelivery);
    }

    /**
     * 批量删除虚拟币交割方案
     *
     * @param ids 需要删除的虚拟币交割方案主键
     * @return 结果
     */
    @Override
    public int deleteCoinDeliveryByIds(List<Long> ids)
    {
        QueryWrapper<CoinDelivery> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinDeliveryMapper.delete(ew);
    }

    /**
     * 删除虚拟币交割方案信息
     *
     * @param id 虚拟币交割方案主键
     * @return 结果
     */
    @Override
    public int deleteCoinDeliveryById(Long id)
    {
        return coinDeliveryMapper.deleteById(id);
    }
}
