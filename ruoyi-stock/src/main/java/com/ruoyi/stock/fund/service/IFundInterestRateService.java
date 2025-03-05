package com.ruoyi.stock.fund.service;

import java.util.List;
import com.ruoyi.stock.fund.domain.FundInterestRate;

/**
 * 日收益Service接口
 *
 * @author ruoyi
 * @date 2025-01-06
 */
public interface IFundInterestRateService
{
    /**
     * 查询日收益
     *
     * @param id 日收益主键
     * @return 日收益
     */
    public FundInterestRate selectMemberFundInterestRateById(Long id);

    /**
     * 查询日收益列表
     *
     * @param fundInterestRate 日收益
     * @return 日收益集合
     */
    public List<FundInterestRate> selectMemberFundInterestRateList(FundInterestRate fundInterestRate);

    /**
     * 新增日收益
     *
     * @param fundInterestRate 日收益
     * @return 结果
     */
    public int insertMemberFundInterestRate(FundInterestRate fundInterestRate);

    /**
     * 修改日收益
     *
     * @param fundInterestRate 日收益
     * @return 结果
     */
    public int updateMemberFundInterestRate(FundInterestRate fundInterestRate);

    /**
     * 批量删除日收益
     *
     * @param ids 需要删除的日收益主键集合
     * @return 结果
     */
    public int deleteMemberFundInterestRateByIds(List<Long> ids);

    /**
     * 删除日收益信息
     *
     * @param id 日收益主键
     * @return 结果
     */
    public int deleteMemberFundInterestRateById(Long id);
}
