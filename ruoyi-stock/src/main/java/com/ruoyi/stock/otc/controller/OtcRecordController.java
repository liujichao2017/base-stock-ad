package com.ruoyi.stock.otc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.stock.otc.domain.vo.OtcRecordVo;
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
import com.ruoyi.stock.otc.domain.OtcRecord;
import com.ruoyi.stock.otc.service.IOtcRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * otc(大宗)记录Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/otcRecord")
public class OtcRecordController extends BaseController
{
    @Autowired
    private IOtcRecordService otcRecordService;

    /**
     * 查询otc(大宗)记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(OtcRecordVo otcRecord)
    {
        startPage();
        return getDataTable(otcRecordService.selectOtcRecordList(otcRecord));
    }

    /**
     * 导出otc(大宗)记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:export')")
    @Log(title = "otc(大宗)记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OtcRecordVo otcRecord)
    {
        List<OtcRecordVo> list = otcRecordService.selectOtcRecordList(otcRecord);
        ExcelUtil<OtcRecordVo> util = new ExcelUtil<OtcRecordVo>(OtcRecordVo.class);
        util.exportExcel(response, list, "otc(大宗)记录数据");
    }

    /**
     * 获取otc(大宗)记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(otcRecordService.selectOtcRecordById(id));
    }

    /**
     * 新增otc(大宗)记录
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:add')")
    @Log(title = "otc(大宗)记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OtcRecord otcRecord)
    {
        return toAjax(otcRecordService.insertOtcRecord(otcRecord));
    }

    /**
     * 修改otc(大宗)记录
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:edit')")
    @Log(title = "otc(大宗)记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OtcRecord otcRecord)
    {
        return toAjax(otcRecordService.updateOtcRecord(otcRecord));
    }

    /**
     * 删除otc(大宗)记录
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:remove')")
    @Log(title = "otc(大宗)记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(otcRecordService.deleteOtcRecordByIds(ids));
    }

    /**
     * 审批otc(大宗)记录
     */
    @PreAuthorize("@ss.hasPermi('stock:otcRecord:exam')")
    @Log(title = "otc(大宗)记录", businessType = BusinessType.UPDATE)
    @PostMapping("/exam")
    public AjaxResult exam(@RequestBody OtcRecord otcRecord)
    {
        return toAjax(otcRecordService.examOtcRecord(otcRecord));
    }
}
