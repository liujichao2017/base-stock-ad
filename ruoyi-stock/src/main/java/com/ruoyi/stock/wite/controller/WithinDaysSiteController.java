package com.ruoyi.stock.wite.controller;

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
import com.ruoyi.stock.wite.domain.WithinDaysSite;
import com.ruoyi.stock.wite.service.IWithinDaysSiteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日内交易配置Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/withinDaysSite")
public class WithinDaysSiteController extends BaseController
{
    @Autowired
    private IWithinDaysSiteService withinDaysSiteService;

    /**
     * 查询日内交易配置列表
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:list')")
    @GetMapping("/list")
    public TableDataInfo list(WithinDaysSite withinDaysSite)
    {
        startPage();
        return getDataTable(withinDaysSiteService.selectWithinDaysSiteList(withinDaysSite));
    }

    /**
     * 导出日内交易配置列表
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:export')")
    @Log(title = "日内交易配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WithinDaysSite withinDaysSite)
    {
        List<WithinDaysSite> list = withinDaysSiteService.selectWithinDaysSiteList(withinDaysSite);
        ExcelUtil<WithinDaysSite> util = new ExcelUtil<WithinDaysSite>(WithinDaysSite.class);
        util.exportExcel(response, list, "日内交易配置数据");
    }

    /**
     * 获取日内交易配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(withinDaysSiteService.selectWithinDaysSiteById(id));
    }

    /**
     * 新增日内交易配置
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:add')")
    @Log(title = "日内交易配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WithinDaysSite withinDaysSite)
    {
        return toAjax(withinDaysSiteService.insertWithinDaysSite(withinDaysSite));
    }

    /**
     * 修改日内交易配置
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:edit')")
    @Log(title = "日内交易配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WithinDaysSite withinDaysSite)
    {
        return toAjax(withinDaysSiteService.updateWithinDaysSite(withinDaysSite));
    }

    /**
     * 删除日内交易配置
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysSite:remove')")
    @Log(title = "日内交易配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(withinDaysSiteService.deleteWithinDaysSiteByIds(ids));
    }
}
