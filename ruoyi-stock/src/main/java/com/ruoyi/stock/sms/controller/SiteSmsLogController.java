package com.ruoyi.stock.sms.controller;

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
import com.ruoyi.stock.sms.domain.SiteSmsLog;
import com.ruoyi.stock.sms.service.ISiteSmsLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信记录Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/sms/log")
public class SiteSmsLogController extends BaseController
{
    @Autowired
    private ISiteSmsLogService siteSmsLogService;

    /**
     * 查询短信记录列表
     */
    @PreAuthorize("@ss.hasPermi('sms:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteSmsLog siteSmsLog)
    {
        startPage();
        return getDataTable(siteSmsLogService.selectSiteSmsLogList(siteSmsLog));
    }

    /**
     * 导出短信记录列表
     */
    @PreAuthorize("@ss.hasPermi('sms:log:export')")
    @Log(title = "短信记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteSmsLog siteSmsLog)
    {
        List<SiteSmsLog> list = siteSmsLogService.selectSiteSmsLogList(siteSmsLog);
        ExcelUtil<SiteSmsLog> util = new ExcelUtil<SiteSmsLog>(SiteSmsLog.class);
        util.exportExcel(response, list, "短信记录数据");
    }

    /**
     * 获取短信记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteSmsLogService.selectSiteSmsLogById(id));
    }

    /**
     * 新增短信记录
     */
    @PreAuthorize("@ss.hasPermi('sms:log:add')")
    @Log(title = "短信记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteSmsLog siteSmsLog)
    {
        return toAjax(siteSmsLogService.insertSiteSmsLog(siteSmsLog));
    }

    /**
     * 修改短信记录
     */
    @PreAuthorize("@ss.hasPermi('sms:log:edit')")
    @Log(title = "短信记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteSmsLog siteSmsLog)
    {
        return toAjax(siteSmsLogService.updateSiteSmsLog(siteSmsLog));
    }

    /**
     * 删除短信记录
     */
    @PreAuthorize("@ss.hasPermi('sms:log:remove')")
    @Log(title = "短信记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteSmsLogService.deleteSiteSmsLogByIds(ids));
    }
}
