package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinContract;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币合约Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinContractService
{
    /**
     * 查询虚拟币合约
     *
     * @param id 虚拟币合约主键
     * @return 虚拟币合约
     */
    public CoinContract selectCoinContractById(Long id);

    /**
     * 查询虚拟币合约列表
     *
     * @param coinContract 虚拟币合约
     * @return 虚拟币合约集合
     */
    public List<CoinContract> selectCoinContractList(CoinContract coinContract);

    /**
     * 新增虚拟币合约
     *
     * @param coinContract 虚拟币合约
     * @return 结果
     */
    public int insertCoinContract(CoinContract coinContract);

    /**
     * 修改虚拟币合约
     *
     * @param coinContract 虚拟币合约
     * @return 结果
     */
    public int updateCoinContract(CoinContract coinContract);

    /**
     * 批量删除虚拟币合约
     *
     * @param ids 需要删除的虚拟币合约主键集合
     * @return 结果
     */
    public int deleteCoinContractByIds(List<Long> ids);

    /**
     * 删除虚拟币合约信息
     *
     * @param id 虚拟币合约主键
     * @return 结果
     */
    public int deleteCoinContractById(Long id);
}
