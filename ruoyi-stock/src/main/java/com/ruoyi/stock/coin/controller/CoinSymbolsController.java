package com.ruoyi.stock.coin.controller;

import com.ruoyi.stock.coin.domain.CoinSymbols;
import com.ruoyi.stock.coin.service.ICoinSymbolsService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 交易对Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/symbols")
public class CoinSymbolsController extends BaseController {
    @Autowired
    private ICoinSymbolsService coinSymbolsService;

    /**
     * 查询交易对列表
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinSymbols coinSymbols) {
        startPage();
        return getDataTable(coinSymbolsService.selectCoinSymbolsList(coinSymbols));
    }

    /**
     * 导出交易对列表
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:export')")
    @Log(title = "交易对", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinSymbols coinSymbols) {
        List<CoinSymbols> list = coinSymbolsService.selectCoinSymbolsList(coinSymbols);
        ExcelUtil<CoinSymbols> util = new ExcelUtil<CoinSymbols>(CoinSymbols.class);
        util.exportExcel(response, list, "交易对数据");
    }

    /**
     * 获取交易对详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(coinSymbolsService.selectCoinSymbolsById(id));
    }

    /**
     * 新增交易对
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:add')")
    @Log(title = "交易对", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinSymbols coinSymbols) {
        return toAjax(coinSymbolsService.insertCoinSymbols(coinSymbols));
    }

    /**
     * 修改交易对
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:edit')")
    @Log(title = "交易对", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinSymbols coinSymbols) {
        return toAjax(coinSymbolsService.updateCoinSymbols(coinSymbols));
    }

    /**
     * 删除交易对
     */
    @PreAuthorize("@ss.hasPermi('coin:symbols:remove')")
    @Log(title = "交易对", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(coinSymbolsService.deleteCoinSymbolsByIds(ids));
    }
}
