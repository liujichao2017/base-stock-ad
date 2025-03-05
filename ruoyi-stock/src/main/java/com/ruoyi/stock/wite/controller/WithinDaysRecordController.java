package com.ruoyi.stock.wite.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.stock.wite.domain.dto.CreateWareHouseDto;
import com.ruoyi.stock.wite.domain.vo.WithinDaysRecordVo;
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
import com.ruoyi.stock.wite.domain.WithinDaysRecord;
import com.ruoyi.stock.wite.service.IWithinDaysRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户日内交易记录Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/withinDaysRecord")
public class WithinDaysRecordController extends BaseController
{
    @Autowired
    private IWithinDaysRecordService withinDaysRecordService;

    /**
     * 查询用户日内交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WithinDaysRecordVo withinDaysRecord)
    {
        startPage();
        return getDataTable(withinDaysRecordService.selectWithinDaysRecordList(withinDaysRecord));
    }

    /**
     * 导出用户日内交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:export')")
    @Log(title = "用户日内交易记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WithinDaysRecordVo withinDaysRecord)
    {
        List<WithinDaysRecordVo> list = withinDaysRecordService.selectWithinDaysRecordList(withinDaysRecord);
        ExcelUtil<WithinDaysRecordVo> util = new ExcelUtil<WithinDaysRecordVo>(WithinDaysRecordVo.class);
        util.exportExcel(response, list, "用户日内交易记录数据");
    }

    /**
     * 获取用户日内交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(withinDaysRecordService.selectWithinDaysRecordById(id));
    }

    /**
     * 新增用户日内交易记录
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:add')")
    @Log(title = "用户日内交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WithinDaysRecord withinDaysRecord)
    {
        return toAjax(withinDaysRecordService.insertWithinDaysRecord(withinDaysRecord));
    }

    /**
     * 修改用户日内交易记录
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:edit')")
    @Log(title = "用户日内交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WithinDaysRecord withinDaysRecord)
    {
        return toAjax(withinDaysRecordService.updateWithinDaysRecord(withinDaysRecord));
    }

    /**
     * 删除用户日内交易记录
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:remove')")
    @Log(title = "用户日内交易记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(withinDaysRecordService.deleteWithinDaysRecordByIds(ids));
    }

    /**
     * 日内建仓
     */
    @PreAuthorize("@ss.hasPermi('stock:withinDaysRecord:create')")
    @Log(title = "用户日内交易记录", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult create(@RequestBody CreateWareHouseDto createWareHouseDto)
    {
        return toAjax(withinDaysRecordService.createHouse(createWareHouseDto));
    }
}
