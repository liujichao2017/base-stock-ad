package com.ruoyi.stock.audit.controller;

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
import com.ruoyi.stock.audit.domain.SiteAuditLog;
import com.ruoyi.stock.audit.service.ISiteAuditLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户操作Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/audit/log")
public class SiteAuditLogController extends BaseController
{
    @Autowired
    private ISiteAuditLogService siteAuditLogService;

    /**
     * 查询客户操作列表
     */
    @PreAuthorize("@ss.hasPermi('audit:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteAuditLog siteAuditLog)
    {
        startPage();
        return getDataTable(siteAuditLogService.selectSiteAuditLogList(siteAuditLog));
    }

    /**
     * 导出客户操作列表
     */
    @PreAuthorize("@ss.hasPermi('audit:log:export')")
    @Log(title = "客户操作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteAuditLog siteAuditLog)
    {
        List<SiteAuditLog> list = siteAuditLogService.selectSiteAuditLogList(siteAuditLog);
        ExcelUtil<SiteAuditLog> util = new ExcelUtil<SiteAuditLog>(SiteAuditLog.class);
        util.exportExcel(response, list, "客户操作数据");
    }

    /**
     * 获取客户操作详细信息
     */
    @PreAuthorize("@ss.hasPermi('audit:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteAuditLogService.selectSiteAuditLogById(id));
    }

    /**
     * 新增客户操作
     */
    @PreAuthorize("@ss.hasPermi('audit:log:add')")
    @Log(title = "客户操作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteAuditLog siteAuditLog)
    {
        return toAjax(siteAuditLogService.insertSiteAuditLog(siteAuditLog));
    }

    /**
     * 修改客户操作
     */
    @PreAuthorize("@ss.hasPermi('audit:log:edit')")
    @Log(title = "客户操作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteAuditLog siteAuditLog)
    {
        return toAjax(siteAuditLogService.updateSiteAuditLog(siteAuditLog));
    }

    /**
     * 删除客户操作
     */
    @PreAuthorize("@ss.hasPermi('audit:log:remove')")
    @Log(title = "客户操作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteAuditLogService.deleteSiteAuditLogByIds(ids));
    }
}
