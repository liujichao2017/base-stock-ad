package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinContractDelegation;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币合约委托Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinContractDelegationService
{
    /**
     * 查询虚拟币合约委托
     *
     * @param id 虚拟币合约委托主键
     * @return 虚拟币合约委托
     */
    public CoinContractDelegation selectCoinContractDelegationById(Long id);

    /**
     * 查询虚拟币合约委托列表
     *
     * @param coinContractDelegation 虚拟币合约委托
     * @return 虚拟币合约委托集合
     */
    public List<CoinContractDelegation> selectCoinContractDelegationList(CoinContractDelegation coinContractDelegation);

    /**
     * 新增虚拟币合约委托
     *
     * @param coinContractDelegation 虚拟币合约委托
     * @return 结果
     */
    public int insertCoinContractDelegation(CoinContractDelegation coinContractDelegation);

    /**
     * 修改虚拟币合约委托
     *
     * @param coinContractDelegation 虚拟币合约委托
     * @return 结果
     */
    public int updateCoinContractDelegation(CoinContractDelegation coinContractDelegation);

    /**
     * 批量删除虚拟币合约委托
     *
     * @param ids 需要删除的虚拟币合约委托主键集合
     * @return 结果
     */
    public int deleteCoinContractDelegationByIds(List<Long> ids);

    /**
     * 删除虚拟币合约委托信息
     *
     * @param id 虚拟币合约委托主键
     * @return 结果
     */
    public int deleteCoinContractDelegationById(Long id);
}
