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
import com.ruoyi.stock.experience.domain.ExperienceRecord;
import com.ruoyi.stock.experience.service.IExperienceRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 体验金使用记录Controller
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/experience/record")
public class ExperienceRecordController extends BaseController
{
    @Autowired
    private IExperienceRecordService experienceRecordService;

    /**
     * 查询体验金使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('experience:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExperienceRecord experienceRecord)
    {
        startPage();
        return getDataTable(experienceRecordService.selectExperienceRecordList(experienceRecord));
    }

    /**
     * 导出体验金使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('experience:record:export')")
    @Log(title = "体验金使用记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExperienceRecord experienceRecord)
    {
        List<ExperienceRecord> list = experienceRecordService.selectExperienceRecordList(experienceRecord);
        ExcelUtil<ExperienceRecord> util = new ExcelUtil<ExperienceRecord>(ExperienceRecord.class);
        util.exportExcel(response, list, "体验金使用记录数据");
    }

    /**
     * 获取体验金使用记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('experience:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(experienceRecordService.selectExperienceRecordById(id));
    }

    /**
     * 新增体验金使用记录
     */
    @PreAuthorize("@ss.hasPermi('experience:record:add')")
    @Log(title = "体验金使用记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExperienceRecord experienceRecord)
    {
        return toAjax(experienceRecordService.insertExperienceRecord(experienceRecord));
    }

    /**
     * 修改体验金使用记录
     */
    @PreAuthorize("@ss.hasPermi('experience:record:edit')")
    @Log(title = "体验金使用记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExperienceRecord experienceRecord)
    {
        return toAjax(experienceRecordService.updateExperienceRecord(experienceRecord));
    }

    /**
     * 删除体验金使用记录
     */
    @PreAuthorize("@ss.hasPermi('experience:record:remove')")
    @Log(title = "体验金使用记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(experienceRecordService.deleteExperienceRecordByIds(ids));
    }
}
