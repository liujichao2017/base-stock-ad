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
import com.ruoyi.stock.coin.domain.CoinSpontaneous;
import com.ruoyi.stock.coin.service.ICoinSpontaneousService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自发币Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/spontaneous")
public class CoinSpontaneousController extends BaseController
{
    @Autowired
    private ICoinSpontaneousService coinSpontaneousService;

    /**
     * 查询自发币列表
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinSpontaneous coinSpontaneous)
    {
        startPage();
        return getDataTable(coinSpontaneousService.selectCoinSpontaneousList(coinSpontaneous));
    }

    /**
     * 导出自发币列表
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:export')")
    @Log(title = "自发币", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinSpontaneous coinSpontaneous)
    {
        List<CoinSpontaneous> list = coinSpontaneousService.selectCoinSpontaneousList(coinSpontaneous);
        ExcelUtil<CoinSpontaneous> util = new ExcelUtil<CoinSpontaneous>(CoinSpontaneous.class);
        util.exportExcel(response, list, "自发币数据");
    }

    /**
     * 获取自发币详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinSpontaneousService.selectCoinSpontaneousById(id));
    }

    /**
     * 新增自发币
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:add')")
    @Log(title = "自发币", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinSpontaneous coinSpontaneous)
    {
        return toAjax(coinSpontaneousService.insertCoinSpontaneous(coinSpontaneous));
    }

    /**
     * 修改自发币
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:edit')")
    @Log(title = "自发币", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinSpontaneous coinSpontaneous)
    {
        return toAjax(coinSpontaneousService.updateCoinSpontaneous(coinSpontaneous));
    }

    /**
     * 删除自发币
     */
    @PreAuthorize("@ss.hasPermi('coin:spontaneous:remove')")
    @Log(title = "自发币", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinSpontaneousService.deleteCoinSpontaneousByIds(ids));
    }
}
