package com.ruoyi.stock.loan.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.stock.loan.domain.LoanConfiguration;
import com.ruoyi.stock.loan.service.ILoanConfigurationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 贷款配置Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/loan/loanConfig")
public class LoanConfigurationController extends BaseController
{
    @Autowired
    private ILoanConfigurationService loanConfigurationService;

    /**
     * 查询贷款配置列表
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoanConfiguration loanConfiguration)
    {
        startPage();
        return getDataTable(loanConfigurationService.selectLoanConfigurationList(loanConfiguration));
    }

    /**
     * 导出贷款配置列表
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:export')")
    @Log(title = "贷款配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoanConfiguration loanConfiguration)
    {
        List<LoanConfiguration> list = loanConfigurationService.selectLoanConfigurationList(loanConfiguration);
        ExcelUtil<LoanConfiguration> util = new ExcelUtil<LoanConfiguration>(LoanConfiguration.class);
        util.exportExcel(response, list, "贷款配置数据");
    }

    /**
     * 获取贷款配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(loanConfigurationService.selectLoanConfigurationById(id));
    }

    /**
     * 新增贷款配置
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:add')")
    @Log(title = "贷款配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LoanConfiguration loanConfiguration)
    {
        return toAjax(loanConfigurationService.insertLoanConfiguration(loanConfiguration));
    }

    /**
     * 修改贷款配置
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:edit')")
    @Log(title = "贷款配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LoanConfiguration loanConfiguration)
    {
        return toAjax(loanConfigurationService.updateLoanConfiguration(loanConfiguration));
    }

    /**
     * 删除贷款配置
     */
    @PreAuthorize("@ss.hasPermi('loan:loanConfig:remove')")
    @Log(title = "贷款配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(loanConfigurationService.deleteLoanConfigurationByIds(ids));
    }
}
