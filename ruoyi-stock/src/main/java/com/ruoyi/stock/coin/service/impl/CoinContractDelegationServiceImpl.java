package com.ruoyi.stock.coin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.coin.domain.CoinContractDelegation;
import com.ruoyi.stock.coin.mapper.CoinContractDelegationMapper;
import com.ruoyi.stock.coin.service.ICoinContractDelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 虚拟币合约委托Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinContractDelegationServiceImpl implements ICoinContractDelegationService {
    @Autowired
    private CoinContractDelegationMapper coinContractDelegationMapper;

    /**
     * 查询虚拟币合约委托
     *
     * @param id 虚拟币合约委托主键
     * @return 虚拟币合约委托
     */
    @Override
    public CoinContractDelegation selectCoinContractDelegationById(Long id) {
        return coinContractDelegationMapper.selectById(id);
    }

    /**
     * 查询虚拟币合约委托列表
     *
     * @param body 虚拟币合约委托
     * @return 虚拟币合约委托
     */
    @Override
    public List<CoinContractDelegation> selectCoinContractDelegationList(CoinContractDelegation body) {
        QueryWrapper<CoinContractDelegation> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(body.getMemberId()), "b.member_id", body.getMemberId());
        ew.like(LogicUtils.isNotNull(body.getMemberPhone()), "m.phone", body.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(body.getUserId()), "u.user_id", body.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.orderByDesc("b.create_time");

        ew.like(LogicUtils.isNotNull(body.getSymbol()), "b.symbol", body.getSymbol());
        ew.eq(LogicUtils.isNotNull(body.getDirection()), "b.direction", body.getDirection());

        ew.eq(LogicUtils.isNotNull(body.getPriceType()), "b.price_type", body.getPriceType());
        ew.eq(LogicUtils.isNotNull(body.getDeliveryType()), "b.delivery_type", body.getDeliveryType());
        ew.eq(LogicUtils.isNotNull(body.getStatus()), "b.status", body.getStatus());
        return coinContractDelegationMapper.selectByList(ew);
    }

    /**
     * 新增虚拟币合约委托
     *
     * @param coinContractDelegation 虚拟币合约委托
     * @return 结果
     */
    @Override
    public int insertCoinContractDelegation(CoinContractDelegation coinContractDelegation) {
        coinContractDelegation.setCreateTime(DateUtils.getNowDate());
        return coinContractDelegationMapper.insert(coinContractDelegation);
    }

    /**
     * 修改虚拟币合约委托
     *
     * @param coinContractDelegation 虚拟币合约委托
     * @return 结果
     */
    @Override
    public int updateCoinContractDelegation(CoinContractDelegation coinContractDelegation) {
        coinContractDelegation.setUpdateTime(DateUtils.getNowDate());
        return coinContractDelegationMapper.updateById(coinContractDelegation);
    }

    /**
     * 批量删除虚拟币合约委托
     *
     * @param ids 需要删除的虚拟币合约委托主键
     * @return 结果
     */
    @Override
    public int deleteCoinContractDelegationByIds(List<Long> ids) {
        QueryWrapper<CoinContractDelegation> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinContractDelegationMapper.delete(ew);
    }

    /**
     * 删除虚拟币合约委托信息
     *
     * @param id 虚拟币合约委托主键
     * @return 结果
     */
    @Override
    public int deleteCoinContractDelegationById(Long id) {
        return coinContractDelegationMapper.deleteById(id);
    }
}
