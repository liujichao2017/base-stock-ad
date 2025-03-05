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
import com.ruoyi.stock.coin.domain.CoinDelivery;
import com.ruoyi.stock.coin.service.ICoinDeliveryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币交割方案Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/delivery")
public class CoinDeliveryController extends BaseController
{
    @Autowired
    private ICoinDeliveryService coinDeliveryService;

    /**
     * 查询虚拟币交割方案列表
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinDelivery coinDelivery)
    {
        startPage();
        return getDataTable(coinDeliveryService.selectCoinDeliveryList(coinDelivery));
    }

    /**
     * 导出虚拟币交割方案列表
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:export')")
    @Log(title = "虚拟币交割方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinDelivery coinDelivery)
    {
        List<CoinDelivery> list = coinDeliveryService.selectCoinDeliveryList(coinDelivery);
        ExcelUtil<CoinDelivery> util = new ExcelUtil<CoinDelivery>(CoinDelivery.class);
        util.exportExcel(response, list, "虚拟币交割方案数据");
    }

    /**
     * 获取虚拟币交割方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinDeliveryService.selectCoinDeliveryById(id));
    }

    /**
     * 新增虚拟币交割方案
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:add')")
    @Log(title = "虚拟币交割方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinDelivery coinDelivery)
    {
        return toAjax(coinDeliveryService.insertCoinDelivery(coinDelivery));
    }

    /**
     * 修改虚拟币交割方案
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:edit')")
    @Log(title = "虚拟币交割方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinDelivery coinDelivery)
    {
        return toAjax(coinDeliveryService.updateCoinDelivery(coinDelivery));
    }

    /**
     * 删除虚拟币交割方案
     */
    @PreAuthorize("@ss.hasPermi('coin:delivery:remove')")
    @Log(title = "虚拟币交割方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinDeliveryService.deleteCoinDeliveryByIds(ids));
    }
}
