package com.ruoyi.stock.loan.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.loan.mapper.LoanConfigurationMapper;
import com.ruoyi.stock.loan.domain.LoanConfiguration;
import com.ruoyi.stock.loan.service.ILoanConfigurationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 贷款配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class LoanConfigurationServiceImpl implements ILoanConfigurationService
{
    @Autowired
    private LoanConfigurationMapper loanConfigurationMapper;

    /**
     * 查询贷款配置
     *
     * @param id 贷款配置主键
     * @return 贷款配置
     */
    @Override
    public LoanConfiguration selectLoanConfigurationById(Long id) {
        return loanConfigurationMapper.selectById(id);
    }

    /**
     * 查询贷款配置列表
     *
     * @param loanConfiguration 贷款配置
     * @return 贷款配置
     */
    @Override
    public List<LoanConfiguration> selectLoanConfigurationList(LoanConfiguration loanConfiguration) {
        QueryWrapper<LoanConfiguration> ew = new QueryWrapper<>();
        ew.setEntity(loanConfiguration);
        ew.orderByDesc("create_time");
        return loanConfigurationMapper.selectList(ew);
    }

    /**
     * 新增贷款配置
     *
     * @param loanConfiguration 贷款配置
     * @return 结果
     */
    @Override
    public int insertLoanConfiguration(LoanConfiguration loanConfiguration)
    {
        loanConfiguration.setCreateTime(DateUtils.getNowDate());
        return loanConfigurationMapper.insert(loanConfiguration);
    }

    /**
     * 修改贷款配置
     *
     * @param loanConfiguration 贷款配置
     * @return 结果
     */
    @Override
    public int updateLoanConfiguration(LoanConfiguration loanConfiguration)
    {
        loanConfiguration.setUpdateTime(DateUtils.getNowDate());
        return loanConfigurationMapper.updateById(loanConfiguration);
    }

    /**
     * 批量删除贷款配置
     *
     * @param ids 需要删除的贷款配置主键
     * @return 结果
     */
    @Override
    public int deleteLoanConfigurationByIds(List<Long> ids)
    {
        QueryWrapper<LoanConfiguration> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return loanConfigurationMapper.delete(ew);
    }

    /**
     * 删除贷款配置信息
     *
     * @param id 贷款配置主键
     * @return 结果
     */
    @Override
    public int deleteLoanConfigurationById(Long id)
    {
        return loanConfigurationMapper.deleteById(id);
    }
}
