package com.ruoyi.stock.experience.controller;

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
import com.ruoyi.stock.experience.domain.Experience;
import com.ruoyi.stock.experience.service.IExperienceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 体验金Controller
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/experience/experience")
public class ExperienceController extends BaseController
{
    @Autowired
    private IExperienceService experienceService;

    /**
     * 查询体验金列表
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:list')")
    @GetMapping("/list")
    public TableDataInfo list(Experience experience)
    {
        startPage();
        return getDataTable(experienceService.selectExperienceList(experience));
    }

    /**
     * 导出体验金列表
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:export')")
    @Log(title = "体验金", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Experience experience)
    {
        List<Experience> list = experienceService.selectExperienceList(experience);
        ExcelUtil<Experience> util = new ExcelUtil<Experience>(Experience.class);
        util.exportExcel(response, list, "体验金数据");
    }

    /**
     * 获取体验金详细信息
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(experienceService.selectExperienceById(id));
    }

    /**
     * 新增体验金
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:add')")
    @Log(title = "体验金", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Experience experience)
    {
        return toAjax(experienceService.insertExperience(experience));
    }

    /**
     * 修改体验金
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:edit')")
    @Log(title = "体验金", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Experience experience)
    {
        return toAjax(experienceService.updateExperience(experience));
    }

    /**
     * 删除体验金
     */
    @PreAuthorize("@ss.hasPermi('experience:experience:remove')")
    @Log(title = "体验金", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(experienceService.deleteExperienceByIds(ids));
    }
}
