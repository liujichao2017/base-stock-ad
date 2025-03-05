package com.ruoyi.stock.sitemessage.controller;

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
import com.ruoyi.stock.sitemessage.domain.SiteMessage;
import com.ruoyi.stock.sitemessage.service.ISiteMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公告Controller
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@RestController
@RequestMapping("/sitemessage/sitemessage")
public class SiteMessageController extends BaseController
{
    @Autowired
    private ISiteMessageService siteMessageService;

    /**
     * 查询公告列表
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteMessage siteMessage)
    {
        startPage();
        return getDataTable(siteMessageService.selectSiteMessageList(siteMessage));
    }

    /**
     * 导出公告列表
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:export')")
    @Log(title = "公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteMessage siteMessage)
    {
        List<SiteMessage> list = siteMessageService.selectSiteMessageList(siteMessage);
        ExcelUtil<SiteMessage> util = new ExcelUtil<SiteMessage>(SiteMessage.class);
        util.exportExcel(response, list, "公告数据");
    }

    /**
     * 获取公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteMessageService.selectSiteMessageById(id));
    }

    /**
     * 新增公告
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:add')")
    @Log(title = "公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteMessage siteMessage)
    {
        return toAjax(siteMessageService.insertSiteMessage(siteMessage));
    }

    /**
     * 修改公告
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:edit')")
    @Log(title = "公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteMessage siteMessage)
    {
        return toAjax(siteMessageService.updateSiteMessage(siteMessage));
    }

    /**
     * 删除公告
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessage:remove')")
    @Log(title = "公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteMessageService.deleteSiteMessageByIds(ids));
    }
}
