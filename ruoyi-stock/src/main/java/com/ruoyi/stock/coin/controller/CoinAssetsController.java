package com.ruoyi.stock.coin.controller;

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
import com.ruoyi.stock.coin.domain.CoinAssets;
import com.ruoyi.stock.coin.service.ICoinAssetsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币资金Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/assets")
public class CoinAssetsController extends BaseController
{
    @Autowired
    private ICoinAssetsService coinAssetsService;

    /**
     * 查询虚拟币资金列表
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinAssets coinAssets)
    {
        startPage();
        return getDataTable(coinAssetsService.selectCoinAssetsList(coinAssets));
    }

    /**
     * 导出虚拟币资金列表
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:export')")
    @Log(title = "虚拟币资金", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinAssets coinAssets)
    {
        List<CoinAssets> list = coinAssetsService.selectCoinAssetsList(coinAssets);
        ExcelUtil<CoinAssets> util = new ExcelUtil<CoinAssets>(CoinAssets.class);
        util.exportExcel(response, list, "虚拟币资金数据");
    }

    /**
     * 获取虚拟币资金详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinAssetsService.selectCoinAssetsById(id));
    }

    /**
     * 新增虚拟币资金
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:add')")
    @Log(title = "虚拟币资金", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinAssets coinAssets)
    {
        return toAjax(coinAssetsService.insertCoinAssets(coinAssets));
    }

    /**
     * 修改虚拟币资金
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:edit')")
    @Log(title = "虚拟币资金", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinAssets coinAssets)
    {
        return toAjax(coinAssetsService.updateCoinAssets(coinAssets));
    }

    /**
     * 删除虚拟币资金
     */
    @PreAuthorize("@ss.hasPermi('coin:assets:remove')")
    @Log(title = "虚拟币资金", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinAssetsService.deleteCoinAssetsByIds(ids));
    }
}
