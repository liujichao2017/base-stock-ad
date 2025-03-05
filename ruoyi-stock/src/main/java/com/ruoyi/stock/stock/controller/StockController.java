package com.ruoyi.stock.stock.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.reset.AppApi;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 股票产品Controller
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@RestController
@RequestMapping("/stock/stock")
public class StockController extends BaseController {
    @Autowired
    private IStockService stockService;

    /**
     * 查询股票产品列表
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(Stock stock) {
        startPage();
        return getDataTable(stockService.selectStockList(stock));
    }

    /**
     * 导出股票产品列表
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:export')")
    @Log(title = "股票产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Stock stock) {
        List<Stock> list = stockService.selectStockList(stock);
        ExcelUtil<Stock> util = new ExcelUtil<Stock>(Stock.class);
        util.exportExcel(response, list, "股票产品数据");
    }

    /**
     * 获取股票产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockService.selectStockById(id));
    }

    /**
     * 新增股票产品
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:add')")
    @Log(title = "股票产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Stock stock) {
        return toAjax(stockService.insertStock(stock));
    }

    /**
     * 修改股票产品
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:edit')")
    @Log(title = "股票产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Stock stock) {
        return toAjax(stockService.updateStock(stock));
    }

    /**
     * 删除股票产品
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:remove')")
    @Log(title = "股票产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(stockService.deleteStockByIds(ids));
    }

    /**
     * 获取股票最新行情
     */
    @GetMapping("/getLastStock/{gid}")
    public AjaxResult getLastStock(@PathVariable String gid) {
        Stock stock = stockService.selectStock(gid);
        if (LogicUtils.isNull(stock)) stock = stockService.selectStock(gid);
        if (LogicUtils.isNull(stock)) return AjaxResult.error("股票不存在");
        return AjaxResult.success(AppApi.getStockByLast(stock.getId()));
    }
}
