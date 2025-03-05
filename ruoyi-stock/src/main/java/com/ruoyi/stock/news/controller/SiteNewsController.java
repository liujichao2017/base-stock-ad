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
import com.ruoyi.stock.news.domain.SiteNews;
import com.ruoyi.stock.news.service.ISiteNewsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻资讯Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/news/news")
public class SiteNewsController extends BaseController
{
    @Autowired
    private ISiteNewsService siteNewsService;

    /**
     * 查询新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('news:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(SiteNews siteNews)
    {
        startPage();
        return getDataTable(siteNewsService.selectSiteNewsList(siteNews));
    }

    /**
     * 导出新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('news:news:export')")
    @Log(title = "新闻资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SiteNews siteNews)
    {
        List<SiteNews> list = siteNewsService.selectSiteNewsList(siteNews);
        ExcelUtil<SiteNews> util = new ExcelUtil<SiteNews>(SiteNews.class);
        util.exportExcel(response, list, "新闻资讯数据");
    }

    /**
     * 获取新闻资讯详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:news:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(siteNewsService.selectSiteNewsById(id));
    }

    /**
     * 新增新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news:add')")
    @Log(title = "新闻资讯", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SiteNews siteNews)
    {
        return toAjax(siteNewsService.insertSiteNews(siteNews));
    }

    /**
     * 修改新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news:edit')")
    @Log(title = "新闻资讯", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SiteNews siteNews)
    {
        return toAjax(siteNewsService.updateSiteNews(siteNews));
    }

    /**
     * 删除新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news:remove')")
    @Log(title = "新闻资讯", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(siteNewsService.deleteSiteNewsByIds(ids));
    }
}
