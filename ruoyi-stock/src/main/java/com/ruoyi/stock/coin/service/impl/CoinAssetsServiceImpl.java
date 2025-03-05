package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinAssetsMapper;
import com.ruoyi.stock.coin.domain.CoinAssets;
import com.ruoyi.stock.coin.service.ICoinAssetsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 虚拟币资金Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinAssetsServiceImpl implements ICoinAssetsService
{
    @Autowired
    private CoinAssetsMapper coinAssetsMapper;

    /**
     * 查询虚拟币资金
     *
     * @param id 虚拟币资金主键
     * @return 虚拟币资金
     */
    @Override
    public CoinAssets selectCoinAssetsById(Long id) {
        return coinAssetsMapper.selectById(id);
    }

    /**
     * 查询虚拟币资金列表
     *
     * @param coinAssets 虚拟币资金
     * @return 虚拟币资金
     */
    @Override
    public List<CoinAssets> selectCoinAssetsList(CoinAssets coinAssets) {
        QueryWrapper<CoinAssets> ew = new QueryWrapper<>();
        ew.setEntity(coinAssets);
        ew.orderByDesc("create_time");
        return coinAssetsMapper.selectList(ew);
    }

    /**
     * 新增虚拟币资金
     *
     * @param coinAssets 虚拟币资金
     * @return 结果
     */
    @Override
    public int insertCoinAssets(CoinAssets coinAssets)
    {
        coinAssets.setCreateTime(DateUtils.getNowDate());
        return coinAssetsMapper.insert(coinAssets);
    }

    /**
     * 修改虚拟币资金
     *
     * @param coinAssets 虚拟币资金
     * @return 结果
     */
    @Override
    public int updateCoinAssets(CoinAssets coinAssets)
    {
        coinAssets.setUpdateTime(DateUtils.getNowDate());
        return coinAssetsMapper.updateById(coinAssets);
    }

    /**
     * 批量删除虚拟币资金
     *
     * @param ids 需要删除的虚拟币资金主键
     * @return 结果
     */
    @Override
    public int deleteCoinAssetsByIds(List<Long> ids)
    {
        QueryWrapper<CoinAssets> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinAssetsMapper.delete(ew);
    }

    /**
     * 删除虚拟币资金信息
     *
     * @param id 虚拟币资金主键
     * @return 结果
     */
    @Override
    public int deleteCoinAssetsById(Long id)
    {
        return coinAssetsMapper.deleteById(id);
    }
}
