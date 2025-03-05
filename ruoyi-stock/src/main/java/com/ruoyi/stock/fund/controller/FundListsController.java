package com.ruoyi.stock.fund.controller;

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
import com.ruoyi.stock.fund.domain.FundLists;
import com.ruoyi.stock.fund.service.IFundListsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金产品Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/fund/fundLists")
public class FundListsController extends BaseController
{
    @Autowired
    private IFundListsService fundListsService;

    /**
     * 查询基金产品列表
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:list')")
    @GetMapping("/list")
    public TableDataInfo list(FundLists fundLists)
    {
        startPage();
        return getDataTable(fundListsService.selectFundListsList(fundLists));
    }

    /**
     * 导出基金产品列表
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:export')")
    @Log(title = "基金产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FundLists fundLists)
    {
        List<FundLists> list = fundListsService.selectFundListsList(fundLists);
        ExcelUtil<FundLists> util = new ExcelUtil<FundLists>(FundLists.class);
        util.exportExcel(response, list, "基金产品数据");
    }

    /**
     * 获取基金产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(fundListsService.selectFundListsById(id));
    }

    /**
     * 新增基金产品
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:add')")
    @Log(title = "基金产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FundLists fundLists)
    {
        return toAjax(fundListsService.insertFundLists(fundLists));
    }

    /**
     * 修改基金产品
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:edit')")
    @Log(title = "基金产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FundLists fundLists)
    {
        return toAjax(fundListsService.updateFundLists(fundLists));
    }

    /**
     * 删除基金产品
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:remove')")
    @Log(title = "基金产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(fundListsService.deleteFundListsByIds(ids));
    }
}
