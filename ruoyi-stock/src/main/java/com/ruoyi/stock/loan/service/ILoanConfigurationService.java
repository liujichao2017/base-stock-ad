package com.ruoyi.stock.loan.service;

import java.util.List;
import com.ruoyi.stock.loan.domain.LoanConfiguration;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 贷款配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface ILoanConfigurationService
{
    /**
     * 查询贷款配置
     *
     * @param id 贷款配置主键
     * @return 贷款配置
     */
    public LoanConfiguration selectLoanConfigurationById(Long id);

    /**
     * 查询贷款配置列表
     *
     * @param loanConfiguration 贷款配置
     * @return 贷款配置集合
     */
    public List<LoanConfiguration> selectLoanConfigurationList(LoanConfiguration loanConfiguration);

    /**
     * 新增贷款配置
     *
     * @param loanConfiguration 贷款配置
     * @return 结果
     */
    public int insertLoanConfiguration(LoanConfiguration loanConfiguration);

    /**
     * 修改贷款配置
     *
     * @param loanConfiguration 贷款配置
     * @return 结果
     */
    public int updateLoanConfiguration(LoanConfiguration loanConfiguration);

    /**
     * 批量删除贷款配置
     *
     * @param ids 需要删除的贷款配置主键集合
     * @return 结果
     */
    public int deleteLoanConfigurationByIds(List<Long> ids);

    /**
     * 删除贷款配置信息
     *
     * @param id 贷款配置主键
     * @return 结果
     */
    public int deleteLoanConfigurationById(Long id);
}
