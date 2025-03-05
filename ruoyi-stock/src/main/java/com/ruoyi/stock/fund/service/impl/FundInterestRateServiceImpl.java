package com.ruoyi.stock.fund.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.fund.mapper.FundInterestRateMapper;
import com.ruoyi.stock.fund.domain.FundInterestRate;
import com.ruoyi.stock.fund.service.IFundInterestRateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 日收益Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-06
 */
@Service
public class FundInterestRateServiceImpl implements IFundInterestRateService
{
    @Autowired
    private FundInterestRateMapper fundInterestRateMapper;

    /**
     * 查询日收益
     *
     * @param id 日收益主键
     * @return 日收益
     */
    @Override
    public FundInterestRate selectMemberFundInterestRateById(Long id) {
        return fundInterestRateMapper.selectById(id);
    }

    /**
     * 查询日收益列表
     *
     * @param fundInterestRate 日收益
     * @return 日收益
     */
    @Override
    public List<FundInterestRate> selectMemberFundInterestRateList(FundInterestRate fundInterestRate) {
        QueryWrapper<FundInterestRate> ew = new QueryWrapper<>();
        ew.setEntity(fundInterestRate);
        ew.orderByAsc("cycle");
        return fundInterestRateMapper.selectList(ew);
    }

    /**
     * 新增日收益
     *
     * @param fundInterestRate 日收益
     * @return 结果
     */
    @Override
    public int insertMemberFundInterestRate(FundInterestRate fundInterestRate)
    {
        fundInterestRate.setCreateTime(DateUtils.getNowDate());
        return fundInterestRateMapper.insert(fundInterestRate);
    }

    /**
     * 修改日收益
     *
     * @param fundInterestRate 日收益
     * @return 结果
     */
    @Override
    public int updateMemberFundInterestRate(FundInterestRate fundInterestRate)
    {
        fundInterestRate.setUpdateTime(DateUtils.getNowDate());
        return fundInterestRateMapper.updateById(fundInterestRate);
    }

    /**
     * 批量删除日收益
     *
     * @param ids 需要删除的日收益主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundInterestRateByIds(List<Long> ids)
    {
        QueryWrapper<FundInterestRate> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return fundInterestRateMapper.delete(ew);
    }

    /**
     * 删除日收益信息
     *
     * @param id 日收益主键
     * @return 结果
     */
    @Override
    public int deleteMemberFundInterestRateById(Long id)
    {
        return fundInterestRateMapper.deleteById(id);
    }
}
