package com.ruoyi.stock.ai.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import com.ruoyi.stock.ai.service.IAiTradeRecordService;
import com.ruoyi.stock.ai.vo.AiTradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * AI交易记录Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/ai/record")
public class AiTradeRecordController extends BaseController {
    @Autowired
    private IAiTradeRecordService aiTradeRecordService;

    /**
     * 查询AI交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('ai:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiTradeRecord aiTradeRecord) {
        startPage();
        return getDataTable(aiTradeRecordService.selectAiTradeRecordList(aiTradeRecord));
    }

    /**
     * 导出AI交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('ai:record:export')")
    @Log(title = "AI交易记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiTradeRecord aiTradeRecord) {
        List<AiTradeRecord> list = aiTradeRecordService.selectAiTradeRecordList(aiTradeRecord);
        ExcelUtil<AiTradeRecord> util = new ExcelUtil<AiTradeRecord>(AiTradeRecord.class);
        util.exportExcel(response, list, "AI交易记录数据");
    }

    /**
     * 获取AI交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(aiTradeRecordService.selectAiTradeRecordById(id));
    }

    /**
     * 新增AI交易记录
     */
    @PreAuthorize("@ss.hasPermi('ai:record:add')")
    @Log(title = "AI交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiTradeRecord aiTradeRecord) {
        return toAjax(aiTradeRecordService.insertAiTradeRecord(aiTradeRecord));
    }

    /**
     * 修改AI交易记录
     */
    @PreAuthorize("@ss.hasPermi('ai:record:edit')")
    @Log(title = "AI交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiTradeRecord aiTradeRecord) {
        return toAjax(aiTradeRecordService.updateAiTradeRecord(aiTradeRecord));
    }

    /**
     * AI建仓
     */
    @PreAuthorize("@ss.hasPermi('ai:record:position')")
    @Log(title = "AI建仓", businessType = BusinessType.UPDATE)
    @PutMapping("/position")
    public AjaxResult position(@RequestBody @Valid AiTradeVo body) {
        return success(aiTradeRecordService.position(body));
    }

    /**
     * 删除AI交易记录
     */
    @PreAuthorize("@ss.hasPermi('ai:record:remove')")
    @Log(title = "AI交易记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(aiTradeRecordService.deleteAiTradeRecordByIds(ids));
    }
}
