package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinLevelMapper;
import com.ruoyi.stock.coin.domain.CoinLevel;
import com.ruoyi.stock.coin.service.ICoinLevelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 虚拟币杠杆Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinLevelServiceImpl implements ICoinLevelService
{
    @Autowired
    private CoinLevelMapper coinLevelMapper;

    /**
     * 查询虚拟币杠杆
     *
     * @param id 虚拟币杠杆主键
     * @return 虚拟币杠杆
     */
    @Override
    public CoinLevel selectCoinLevelById(Long id) {
        return coinLevelMapper.selectById(id);
    }

    /**
     * 查询虚拟币杠杆列表
     *
     * @param coinLevel 虚拟币杠杆
     * @return 虚拟币杠杆
     */
    @Override
    public List<CoinLevel> selectCoinLevelList(CoinLevel coinLevel) {
        QueryWrapper<CoinLevel> ew = new QueryWrapper<>();
        ew.setEntity(coinLevel);
        ew.orderByDesc("create_time");
        return coinLevelMapper.selectList(ew);
    }

    /**
     * 新增虚拟币杠杆
     *
     * @param coinLevel 虚拟币杠杆
     * @return 结果
     */
    @Override
    public int insertCoinLevel(CoinLevel coinLevel)
    {
        coinLevel.setCreateTime(DateUtils.getNowDate());
        return coinLevelMapper.insert(coinLevel);
    }

    /**
     * 修改虚拟币杠杆
     *
     * @param coinLevel 虚拟币杠杆
     * @return 结果
     */
    @Override
    public int updateCoinLevel(CoinLevel coinLevel)
    {
        coinLevel.setUpdateTime(DateUtils.getNowDate());
        return coinLevelMapper.updateById(coinLevel);
    }

    /**
     * 批量删除虚拟币杠杆
     *
     * @param ids 需要删除的虚拟币杠杆主键
     * @return 结果
     */
    @Override
    public int deleteCoinLevelByIds(List<Long> ids)
    {
        QueryWrapper<CoinLevel> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinLevelMapper.delete(ew);
    }

    /**
     * 删除虚拟币杠杆信息
     *
     * @param id 虚拟币杠杆主键
     * @return 结果
     */
    @Override
    public int deleteCoinLevelById(Long id)
    {
        return coinLevelMapper.deleteById(id);
    }
}
