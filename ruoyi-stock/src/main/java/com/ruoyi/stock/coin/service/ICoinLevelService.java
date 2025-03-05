package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinLevel;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币杠杆Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinLevelService
{
    /**
     * 查询虚拟币杠杆
     *
     * @param id 虚拟币杠杆主键
     * @return 虚拟币杠杆
     */
    public CoinLevel selectCoinLevelById(Long id);

    /**
     * 查询虚拟币杠杆列表
     *
     * @param coinLevel 虚拟币杠杆
     * @return 虚拟币杠杆集合
     */
    public List<CoinLevel> selectCoinLevelList(CoinLevel coinLevel);

    /**
     * 新增虚拟币杠杆
     *
     * @param coinLevel 虚拟币杠杆
     * @return 结果
     */
    public int insertCoinLevel(CoinLevel coinLevel);

    /**
     * 修改虚拟币杠杆
     *
     * @param coinLevel 虚拟币杠杆
     * @return 结果
     */
    public int updateCoinLevel(CoinLevel coinLevel);

    /**
     * 批量删除虚拟币杠杆
     *
     * @param ids 需要删除的虚拟币杠杆主键集合
     * @return 结果
     */
    public int deleteCoinLevelByIds(List<Long> ids);

    /**
     * 删除虚拟币杠杆信息
     *
     * @param id 虚拟币杠杆主键
     * @return 结果
     */
    public int deleteCoinLevelById(Long id);
}
