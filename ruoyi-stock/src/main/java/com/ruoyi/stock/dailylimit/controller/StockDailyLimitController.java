package com.ruoyi.stock.dailylimit.controller;

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
import com.ruoyi.stock.dailylimit.domain.StockDailyLimit;
import com.ruoyi.stock.dailylimit.service.IStockDailyLimitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 涨停列表Controller
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@RestController
@RequestMapping("/dailylimit/list")
public class StockDailyLimitController extends BaseController
{
    @Autowired
    private IStockDailyLimitService stockDailyLimitService;

    /**
     * 查询涨停列表列表
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockDailyLimit stockDailyLimit)
    {
        startPage();
        return getDataTable(stockDailyLimitService.selectStockDailyLimitList(stockDailyLimit));
    }

    /**
     * 导出涨停列表列表
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:export')")
    @Log(title = "涨停列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockDailyLimit stockDailyLimit)
    {
        List<StockDailyLimit> list = stockDailyLimitService.selectStockDailyLimitList(stockDailyLimit);
        ExcelUtil<StockDailyLimit> util = new ExcelUtil<StockDailyLimit>(StockDailyLimit.class);
        util.exportExcel(response, list, "涨停列表数据");
    }

    /**
     * 获取涨停列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stockDailyLimitService.selectStockDailyLimitById(id));
    }

    /**
     * 新增涨停列表
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:add')")
    @Log(title = "涨停列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockDailyLimit stockDailyLimit)
    {
        return toAjax(stockDailyLimitService.insertStockDailyLimit(stockDailyLimit));
    }

    /**
     * 修改涨停列表
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:edit')")
    @Log(title = "涨停列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockDailyLimit stockDailyLimit)
    {
        return toAjax(stockDailyLimitService.updateStockDailyLimit(stockDailyLimit));
    }

    /**
     * 删除涨停列表
     */
    @PreAuthorize("@ss.hasPermi('dailylimit:list:remove')")
    @Log(title = "涨停列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(stockDailyLimitService.deleteStockDailyLimitByIds(ids));
    }
}
