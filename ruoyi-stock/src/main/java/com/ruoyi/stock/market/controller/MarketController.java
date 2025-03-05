package com.ruoyi.stock.market.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.market.service.IMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 市场Controller
 *
 * @author ruoyi
 * @date 2024-11-18
 */
@RestController
@RequestMapping("/market/market")
public class MarketController extends BaseController {
    @Autowired
    private IMarketService marketService;

    /**
     * 查询市场列表
     */
//    @PreAuthorize("@ss.hasPermi('market:market:list')")
    @GetMapping("/list")
    public TableDataInfo list(Market market) {
        startPage();
        return getDataTable(marketService.selectMarketList(market));
    }

    /**
     * 导出市场列表
     */
    @PreAuthorize("@ss.hasPermi('market:market:export')")
    @Log(title = "市场", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Market market) {
        List<Market> list = marketService.selectMarketList(market);
        ExcelUtil<Market> util = new ExcelUtil<Market>(Market.class);
        util.exportExcel(response, list, "市场数据");
    }

    /**
     * 获取市场详细信息
     */
//    @PreAuthorize("@ss.hasPermi('market:market:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(marketService.selectMarketById(id));
    }

    /**
     * 新增市场
     */
    @PreAuthorize("@ss.hasPermi('market:market:add')")
    @Log(title = "市场", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Market market) {
        return toAjax(marketService.insertMarket(market));
    }

    /**
     * 修改市场
     */
//    @PreAuthorize("@ss.hasPermi('market:market:edit')")
    @Log(title = "市场", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Market market) {
        return toAjax(marketService.updateMarket(market));
    }

    /**
     * 删除市场
     */
    @PreAuthorize("@ss.hasPermi('market:market:remove')")
    @Log(title = "市场", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(marketService.deleteMarketByIds(ids));
    }
}
