package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinDelivery;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币交割方案Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinDeliveryService
{
    /**
     * 查询虚拟币交割方案
     *
     * @param id 虚拟币交割方案主键
     * @return 虚拟币交割方案
     */
    public CoinDelivery selectCoinDeliveryById(Long id);

    /**
     * 查询虚拟币交割方案列表
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 虚拟币交割方案集合
     */
    public List<CoinDelivery> selectCoinDeliveryList(CoinDelivery coinDelivery);

    /**
     * 新增虚拟币交割方案
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 结果
     */
    public int insertCoinDelivery(CoinDelivery coinDelivery);

    /**
     * 修改虚拟币交割方案
     *
     * @param coinDelivery 虚拟币交割方案
     * @return 结果
     */
    public int updateCoinDelivery(CoinDelivery coinDelivery);

    /**
     * 批量删除虚拟币交割方案
     *
     * @param ids 需要删除的虚拟币交割方案主键集合
     * @return 结果
     */
    public int deleteCoinDeliveryByIds(List<Long> ids);

    /**
     * 删除虚拟币交割方案信息
     *
     * @param id 虚拟币交割方案主键
     * @return 结果
     */
    public int deleteCoinDeliveryById(Long id);
}
