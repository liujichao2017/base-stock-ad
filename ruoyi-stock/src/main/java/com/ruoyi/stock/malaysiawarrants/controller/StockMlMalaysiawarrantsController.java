package com.ruoyi.stock.malaysiawarrants.controller;

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
import com.ruoyi.stock.malaysiawarrants.domain.StockMlMalaysiawarrants;
import com.ruoyi.stock.malaysiawarrants.service.IStockMlMalaysiawarrantsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * malaysiawarrantsController
 *
 * @author ruoyi
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/malaysiawarrants/malaysiawarrants")
public class StockMlMalaysiawarrantsController extends BaseController
{
    @Autowired
    private IStockMlMalaysiawarrantsService stockMlMalaysiawarrantsService;

    /**
     * 查询malaysiawarrants列表
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockMlMalaysiawarrants stockMlMalaysiawarrants)
    {
        startPage();
        return getDataTable(stockMlMalaysiawarrantsService.selectStockMlMalaysiawarrantsList(stockMlMalaysiawarrants));
    }

    /**
     * 导出malaysiawarrants列表
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:export')")
    @Log(title = "malaysiawarrants", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockMlMalaysiawarrants stockMlMalaysiawarrants)
    {
        List<StockMlMalaysiawarrants> list = stockMlMalaysiawarrantsService.selectStockMlMalaysiawarrantsList(stockMlMalaysiawarrants);
        ExcelUtil<StockMlMalaysiawarrants> util = new ExcelUtil<StockMlMalaysiawarrants>(StockMlMalaysiawarrants.class);
        util.exportExcel(response, list, "malaysiawarrants数据");
    }

    /**
     * 获取malaysiawarrants详细信息
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:query')")
    @GetMapping(value = "/{dwSymbol}")
    public AjaxResult getInfo(@PathVariable("dwSymbol") String dwSymbol)
    {
        return success(stockMlMalaysiawarrantsService.selectStockMlMalaysiawarrantsByDwSymbol(dwSymbol));
    }

    /**
     * 新增malaysiawarrants
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:add')")
    @Log(title = "malaysiawarrants", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockMlMalaysiawarrants stockMlMalaysiawarrants)
    {
        return toAjax(stockMlMalaysiawarrantsService.insertStockMlMalaysiawarrants(stockMlMalaysiawarrants));
    }

    /**
     * 修改malaysiawarrants
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:edit')")
    @Log(title = "malaysiawarrants", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockMlMalaysiawarrants stockMlMalaysiawarrants)
    {
        return toAjax(stockMlMalaysiawarrantsService.updateStockMlMalaysiawarrants(stockMlMalaysiawarrants));
    }

    /**
     * 删除malaysiawarrants
     */
    @PreAuthorize("@ss.hasPermi('malaysiawarrants:malaysiawarrants:remove')")
    @Log(title = "malaysiawarrants", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dwSymbols}")
    public AjaxResult remove(@PathVariable List<String> dwSymbols)
    {
        return toAjax(stockMlMalaysiawarrantsService.deleteStockMlMalaysiawarrantsByDwSymbols(dwSymbols));
    }
}
