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
import com.ruoyi.stock.sms.domain.SiteSmsConfig;
import com.ruoyi.stock.sms.service.ISiteSmsConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信配置Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/sms/config")
public class SiteSmsConfigController extends BaseController
{
    @Autowired
    private ISiteSmsConfigService siteSmsConfigService;

    /**
     * 查询短信配置列表
     */
//    @PreAuthorize("@ss.hasPermi('sms:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteSmsConfig siteSmsConfig)
    {
        startPage();
        return getDataTable(siteSmsConfigService.selectSiteSmsConfigList(siteSmsConfig));
    }

    /**
     * 导出短信配置列表
     */
    @PreAuthorize("@ss.hasPermi('sms:config:export')")
    @Log(title = "短信配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteSmsConfig siteSmsConfig)
    {
        List<SiteSmsConfig> list = siteSmsConfigService.selectSiteSmsConfigList(siteSmsConfig);
        ExcelUtil<SiteSmsConfig> util = new ExcelUtil<SiteSmsConfig>(SiteSmsConfig.class);
        util.exportExcel(response, list, "短信配置数据");
    }

    /**
     * 获取短信配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteSmsConfigService.selectSiteSmsConfigById(id));
    }

    /**
     * 新增短信配置
     */
    @PreAuthorize("@ss.hasPermi('sms:config:add')")
    @Log(title = "短信配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteSmsConfig siteSmsConfig)
    {
        return toAjax(siteSmsConfigService.insertSiteSmsConfig(siteSmsConfig));
    }

    /**
     * 修改短信配置
     */
    @PreAuthorize("@ss.hasPermi('sms:config:edit')")
    @Log(title = "短信配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteSmsConfig siteSmsConfig)
    {
        return toAjax(siteSmsConfigService.updateSiteSmsConfig(siteSmsConfig));
    }

    /**
     * 删除短信配置
     */
    @PreAuthorize("@ss.hasPermi('sms:config:remove')")
    @Log(title = "短信配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteSmsConfigService.deleteSiteSmsConfigByIds(ids));
    }
}
