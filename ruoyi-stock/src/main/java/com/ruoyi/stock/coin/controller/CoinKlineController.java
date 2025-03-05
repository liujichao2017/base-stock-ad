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
import com.ruoyi.stock.coin.domain.CoinKline;
import com.ruoyi.stock.coin.service.ICoinKlineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币K线Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/kline")
public class CoinKlineController extends BaseController
{
    @Autowired
    private ICoinKlineService coinKlineService;

    /**
     * 查询虚拟币K线列表
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinKline coinKline)
    {
        startPage();
        return getDataTable(coinKlineService.selectCoinKlineList(coinKline));
    }

    /**
     * 导出虚拟币K线列表
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:export')")
    @Log(title = "虚拟币K线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinKline coinKline)
    {
        List<CoinKline> list = coinKlineService.selectCoinKlineList(coinKline);
        ExcelUtil<CoinKline> util = new ExcelUtil<CoinKline>(CoinKline.class);
        util.exportExcel(response, list, "虚拟币K线数据");
    }

    /**
     * 获取虚拟币K线详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinKlineService.selectCoinKlineById(id));
    }

    /**
     * 新增虚拟币K线
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:add')")
    @Log(title = "虚拟币K线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinKline coinKline)
    {
        return toAjax(coinKlineService.insertCoinKline(coinKline));
    }

    /**
     * 修改虚拟币K线
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:edit')")
    @Log(title = "虚拟币K线", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinKline coinKline)
    {
        return toAjax(coinKlineService.updateCoinKline(coinKline));
    }

    /**
     * 删除虚拟币K线
     */
    @PreAuthorize("@ss.hasPermi('coin:kline:remove')")
    @Log(title = "虚拟币K线", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinKlineService.deleteCoinKlineByIds(ids));
    }
}
