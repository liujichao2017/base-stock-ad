package com.ruoyi.stock.coin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.stock.coin.domain.CoinContract;
import com.ruoyi.stock.coin.mapper.CoinContractMapper;
import com.ruoyi.stock.coin.service.ICoinContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 虚拟币合约Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinContractServiceImpl implements ICoinContractService {
    @Autowired
    private CoinContractMapper coinContractMapper;

    /**
     * 查询虚拟币合约
     *
     * @param id 虚拟币合约主键
     * @return 虚拟币合约
     */
    @Override
    public CoinContract selectCoinContractById(Long id) {
        return coinContractMapper.selectById(id);
    }

    /**
     * 查询虚拟币合约列表
     *
     * @param body 虚拟币合约
     * @return 虚拟币合约
     */
    @Override
    public List<CoinContract> selectCoinContractList(CoinContract body) {
        QueryWrapper<CoinContract> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(body.getMemberId()), "b.member_id", body.getMemberId());
        ew.like(LogicUtils.isNotNull(body.getMemberPhone()), "m.phone", body.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(body.getUserId()), "u.user_id", body.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.orderByDesc("b.create_time");

        ew.like(LogicUtils.isNotNull(body.getSymbol()), "b.symbol", body.getSymbol());
        ew.eq(LogicUtils.isNotNull(body.getDirection()), "b.direction", body.getDirection());
        ew.like(LogicUtils.isNotNull(body.getBcdn()), "b.bcdn", body.getBcdn());
        ew.eq(LogicUtils.isNotNull(body.getStatus()), "b.status", body.getStatus());
        return coinContractMapper.selectByList(ew);
    }

    /**
     * 新增虚拟币合约
     *
     * @param coinContract 虚拟币合约
     * @return 结果
     */
    @Override
    public int insertCoinContract(CoinContract coinContract) {
        coinContract.setCreateTime(DateUtils.getNowDate());
        return coinContractMapper.insert(coinContract);
    }

    /**
     * 修改虚拟币合约
     *
     * @param coinContract 虚拟币合约
     * @return 结果
     */
    @Override
    public int updateCoinContract(CoinContract coinContract) {
        coinContract.setUpdateTime(DateUtils.getNowDate());
        return coinContractMapper.updateById(coinContract);
    }

    /**
     * 批量删除虚拟币合约
     *
     * @param ids 需要删除的虚拟币合约主键
     * @return 结果
     */
    @Override
    public int deleteCoinContractByIds(List<Long> ids) {
        QueryWrapper<CoinContract> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinContractMapper.delete(ew);
    }

    /**
     * 删除虚拟币合约信息
     *
     * @param id 虚拟币合约主键
     * @return 结果
     */
    @Override
    public int deleteCoinContractById(Long id) {
        return coinContractMapper.deleteById(id);
    }
}
