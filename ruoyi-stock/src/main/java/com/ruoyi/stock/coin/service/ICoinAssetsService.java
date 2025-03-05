package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinAssets;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币资金Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinAssetsService
{
    /**
     * 查询虚拟币资金
     *
     * @param id 虚拟币资金主键
     * @return 虚拟币资金
     */
    public CoinAssets selectCoinAssetsById(Long id);

    /**
     * 查询虚拟币资金列表
     *
     * @param coinAssets 虚拟币资金
     * @return 虚拟币资金集合
     */
    public List<CoinAssets> selectCoinAssetsList(CoinAssets coinAssets);

    /**
     * 新增虚拟币资金
     *
     * @param coinAssets 虚拟币资金
     * @return 结果
     */
    public int insertCoinAssets(CoinAssets coinAssets);

    /**
     * 修改虚拟币资金
     *
     * @param coinAssets 虚拟币资金
     * @return 结果
     */
    public int updateCoinAssets(CoinAssets coinAssets);

    /**
     * 批量删除虚拟币资金
     *
     * @param ids 需要删除的虚拟币资金主键集合
     * @return 结果
     */
    public int deleteCoinAssetsByIds(List<Long> ids);

    /**
     * 删除虚拟币资金信息
     *
     * @param id 虚拟币资金主键
     * @return 结果
     */
    public int deleteCoinAssetsById(Long id);
}
