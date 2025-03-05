package com.ruoyi.stock.news.controller;

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
import com.ruoyi.stock.news.domain.SiteNewsConfig;
import com.ruoyi.stock.news.service.ISiteNewsConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻配置Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/news/config")
public class SiteNewsConfigController extends BaseController
{
    @Autowired
    private ISiteNewsConfigService siteNewsConfigService;

    /**
     * 查询新闻配置列表
     */
    @PreAuthorize("@ss.hasPermi('news:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteNewsConfig siteNewsConfig)
    {
        startPage();
        return getDataTable(siteNewsConfigService.selectSiteNewsConfigList(siteNewsConfig));
    }

    /**
     * 导出新闻配置列表
     */
    @PreAuthorize("@ss.hasPermi('news:config:export')")
    @Log(title = "新闻配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteNewsConfig siteNewsConfig)
    {
        List<SiteNewsConfig> list = siteNewsConfigService.selectSiteNewsConfigList(siteNewsConfig);
        ExcelUtil<SiteNewsConfig> util = new ExcelUtil<SiteNewsConfig>(SiteNewsConfig.class);
        util.exportExcel(response, list, "新闻配置数据");
    }

    /**
     * 获取新闻配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteNewsConfigService.selectSiteNewsConfigById(id));
    }

    /**
     * 新增新闻配置
     */
    @PreAuthorize("@ss.hasPermi('news:config:add')")
    @Log(title = "新闻配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteNewsConfig siteNewsConfig)
    {
        return toAjax(siteNewsConfigService.insertSiteNewsConfig(siteNewsConfig));
    }

    /**
     * 修改新闻配置
     */
    @PreAuthorize("@ss.hasPermi('news:config:edit')")
    @Log(title = "新闻配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteNewsConfig siteNewsConfig)
    {
        return toAjax(siteNewsConfigService.updateSiteNewsConfig(siteNewsConfig));
    }

    /**
     * 删除新闻配置
     */
    @PreAuthorize("@ss.hasPermi('news:config:remove')")
    @Log(title = "新闻配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteNewsConfigService.deleteSiteNewsConfigByIds(ids));
    }
}
