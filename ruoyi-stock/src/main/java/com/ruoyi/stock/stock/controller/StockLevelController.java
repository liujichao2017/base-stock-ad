package com.ruoyi.stock.stock.controller;

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
import com.ruoyi.stock.stock.domain.StockLevel;
import com.ruoyi.stock.stock.service.IStockLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 杠杆配置Controller
 *
 * @author ruoyi
 * @date 2024-12-16
 */
@RestController
@RequestMapping("/stocklevel/stocklevel")
public class StockLevelController extends BaseController
{
    @Autowired
    private IStockLevelService stockLevelService;

    /**
     * 查询杠杆配置列表
     */
//    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockLevel stockLevel)
    {
        startPage();
        return getDataTable(stockLevelService.selectStockLevelList(stockLevel));
    }

    /**
     * 导出杠杆配置列表
     */
    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:export')")
    @Log(title = "杠杆配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockLevel stockLevel)
    {
        List<StockLevel> list = stockLevelService.selectStockLevelList(stockLevel);
        ExcelUtil<StockLevel> util = new ExcelUtil<StockLevel>(StockLevel.class);
        util.exportExcel(response, list, "杠杆配置数据");
    }

    /**
     * 获取杠杆配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stockLevelService.selectStockLevelById(id));
    }

    /**
     * 新增杠杆配置
     */
    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:add')")
    @Log(title = "杠杆配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockLevel stockLevel)
    {
        return toAjax(stockLevelService.insertStockLevel(stockLevel));
    }

    /**
     * 修改杠杆配置
     */
    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:edit')")
    @Log(title = "杠杆配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockLevel stockLevel)
    {
        return toAjax(stockLevelService.updateStockLevel(stockLevel));
    }

    /**
     * 删除杠杆配置
     */
    @PreAuthorize("@ss.hasPermi('stocklevel:stocklevel:remove')")
    @Log(title = "杠杆配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(stockLevelService.deleteStockLevelByIds(ids));
    }
}
