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
import com.ruoyi.stock.sitemessage.domain.SiteMessageMember;
import com.ruoyi.stock.sitemessage.service.ISiteMessageMemberService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户通知Controller
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@RestController
@RequestMapping("/sitemessage/sitemessagemember")
public class SiteMessageMemberController extends BaseController
{
    @Autowired
    private ISiteMessageMemberService siteMessageMemberService;

    /**
     * 查询用户通知列表
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteMessageMember siteMessageMember)
    {
        startPage();
        return getDataTable(siteMessageMemberService.selectSiteMessageMemberList(siteMessageMember));
    }

    /**
     * 导出用户通知列表
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:export')")
    @Log(title = "用户通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteMessageMember siteMessageMember)
    {
        List<SiteMessageMember> list = siteMessageMemberService.selectSiteMessageMemberList(siteMessageMember);
        ExcelUtil<SiteMessageMember> util = new ExcelUtil<SiteMessageMember>(SiteMessageMember.class);
        util.exportExcel(response, list, "用户通知数据");
    }

    /**
     * 获取用户通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteMessageMemberService.selectSiteMessageMemberById(id));
    }

    /**
     * 新增用户通知
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:add')")
    @Log(title = "用户通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteMessageMember siteMessageMember)
    {
        return toAjax(siteMessageMemberService.insertSiteMessageMember(siteMessageMember));
    }

    /**
     * 修改用户通知
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:edit')")
    @Log(title = "用户通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteMessageMember siteMessageMember)
    {
        return toAjax(siteMessageMemberService.updateSiteMessageMember(siteMessageMember));
    }

    /**
     * 删除用户通知
     */
    @PreAuthorize("@ss.hasPermi('sitemessage:sitemessagemember:remove')")
    @Log(title = "用户通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteMessageMemberService.deleteSiteMessageMemberByIds(ids));
    }
}
