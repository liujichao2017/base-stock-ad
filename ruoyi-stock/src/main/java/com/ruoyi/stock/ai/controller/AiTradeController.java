package com.ruoyi.stock.ai.controller;

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
import com.ruoyi.stock.ai.domain.AiTrade;
import com.ruoyi.stock.ai.service.IAiTradeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * AI交易Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/ai/trade")
public class AiTradeController extends BaseController
{
    @Autowired
    private IAiTradeService aiTradeService;

    /**
     * 查询AI交易列表
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiTrade aiTrade)
    {
        startPage();
        return getDataTable(aiTradeService.selectAiTradeList(aiTrade));
    }

    /**
     * 导出AI交易列表
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:export')")
    @Log(title = "AI交易", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiTrade aiTrade)
    {
        List<AiTrade> list = aiTradeService.selectAiTradeList(aiTrade);
        ExcelUtil<AiTrade> util = new ExcelUtil<AiTrade>(AiTrade.class);
        util.exportExcel(response, list, "AI交易数据");
    }

    /**
     * 获取AI交易详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aiTradeService.selectAiTradeById(id));
    }

    /**
     * 新增AI交易
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:add')")
    @Log(title = "AI交易", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiTrade aiTrade)
    {
        return toAjax(aiTradeService.insertAiTrade(aiTrade));
    }

    /**
     * 修改AI交易
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:edit')")
    @Log(title = "AI交易", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiTrade aiTrade)
    {
        return toAjax(aiTradeService.updateAiTrade(aiTrade));
    }

    /**
     * 删除AI交易
     */
    @PreAuthorize("@ss.hasPermi('ai:trade:remove')")
    @Log(title = "AI交易", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(aiTradeService.deleteAiTradeByIds(ids));
    }
}
