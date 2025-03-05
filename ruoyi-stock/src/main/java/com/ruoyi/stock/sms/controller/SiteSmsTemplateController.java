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
import com.ruoyi.stock.sms.domain.SiteSmsTemplate;
import com.ruoyi.stock.sms.service.ISiteSmsTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信模版Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/sms/template")
public class SiteSmsTemplateController extends BaseController
{
    @Autowired
    private ISiteSmsTemplateService siteSmsTemplateService;

    /**
     * 查询短信模版列表
     */
//    @PreAuthorize("@ss.hasPermi('sms:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteSmsTemplate siteSmsTemplate)
    {
        startPage();
        return getDataTable(siteSmsTemplateService.selectSiteSmsTemplateList(siteSmsTemplate));
    }

    /**
     * 导出短信模版列表
     */
    @PreAuthorize("@ss.hasPermi('sms:template:export')")
    @Log(title = "短信模版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteSmsTemplate siteSmsTemplate)
    {
        List<SiteSmsTemplate> list = siteSmsTemplateService.selectSiteSmsTemplateList(siteSmsTemplate);
        ExcelUtil<SiteSmsTemplate> util = new ExcelUtil<SiteSmsTemplate>(SiteSmsTemplate.class);
        util.exportExcel(response, list, "短信模版数据");
    }

    /**
     * 获取短信模版详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteSmsTemplateService.selectSiteSmsTemplateById(id));
    }

    /**
     * 新增短信模版
     */
    @PreAuthorize("@ss.hasPermi('sms:template:add')")
    @Log(title = "短信模版", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteSmsTemplate siteSmsTemplate)
    {
        return toAjax(siteSmsTemplateService.insertSiteSmsTemplate(siteSmsTemplate));
    }

    /**
     * 修改短信模版
     */
    @PreAuthorize("@ss.hasPermi('sms:template:edit')")
    @Log(title = "短信模版", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteSmsTemplate siteSmsTemplate)
    {
        return toAjax(siteSmsTemplateService.updateSiteSmsTemplate(siteSmsTemplate));
    }

    /**
     * 删除短信模版
     */
    @PreAuthorize("@ss.hasPermi('sms:template:remove')")
    @Log(title = "短信模版", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteSmsTemplateService.deleteSiteSmsTemplateByIds(ids));
    }
}
