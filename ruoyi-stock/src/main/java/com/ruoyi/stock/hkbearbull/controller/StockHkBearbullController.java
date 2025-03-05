package com.ruoyi.stock.hkbearbull.controller;

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
import com.ruoyi.stock.hkbearbull.domain.StockHkBearbull;
import com.ruoyi.stock.hkbearbull.service.IStockHkBearbullService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 牛熊市股Controller
 *
 * @author ruoyi
 * @date 2024-12-13
 */
@RestController
@RequestMapping("/hkbearbull/hkbearbull")
public class StockHkBearbullController extends BaseController
{
    @Autowired
    private IStockHkBearbullService stockHkBearbullService;

    /**
     * 查询牛熊市股列表
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockHkBearbull stockHkBearbull)
    {
        startPage();
        return getDataTable(stockHkBearbullService.selectStockHkBearbullList(stockHkBearbull));
    }

    /**
     * 导出牛熊市股列表
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:export')")
    @Log(title = "牛熊市股", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockHkBearbull stockHkBearbull)
    {
        List<StockHkBearbull> list = stockHkBearbullService.selectStockHkBearbullList(stockHkBearbull);
        ExcelUtil<StockHkBearbull> util = new ExcelUtil<StockHkBearbull>(StockHkBearbull.class);
        util.exportExcel(response, list, "牛熊市股数据");
    }

    /**
     * 获取牛熊市股详细信息
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:query')")
    @GetMapping(value = "/{sym}")
    public AjaxResult getInfo(@PathVariable("sym") String sym)
    {
        return success(stockHkBearbullService.selectStockHkBearbullBySym(sym));
    }

    /**
     * 新增牛熊市股
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:add')")
    @Log(title = "牛熊市股", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockHkBearbull stockHkBearbull)
    {
        return toAjax(stockHkBearbullService.insertStockHkBearbull(stockHkBearbull));
    }

    /**
     * 修改牛熊市股
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:edit')")
    @Log(title = "牛熊市股", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockHkBearbull stockHkBearbull)
    {
        return toAjax(stockHkBearbullService.updateStockHkBearbull(stockHkBearbull));
    }

    /**
     * 删除牛熊市股
     */
    @PreAuthorize("@ss.hasPermi('hkbearbull:hkbearbull:remove')")
    @Log(title = "牛熊市股", businessType = BusinessType.DELETE)
	@DeleteMapping("/{syms}")
    public AjaxResult remove(@PathVariable List<String> syms)
    {
        return toAjax(stockHkBearbullService.deleteStockHkBearbullBySyms(syms));
    }
}
