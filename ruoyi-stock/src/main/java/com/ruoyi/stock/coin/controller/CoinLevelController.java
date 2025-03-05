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
import com.ruoyi.stock.coin.domain.CoinLevel;
import com.ruoyi.stock.coin.service.ICoinLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币杠杆Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/level")
public class CoinLevelController extends BaseController
{
    @Autowired
    private ICoinLevelService coinLevelService;

    /**
     * 查询虚拟币杠杆列表
     */
    @PreAuthorize("@ss.hasPermi('coin:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinLevel coinLevel)
    {
        startPage();
        return getDataTable(coinLevelService.selectCoinLevelList(coinLevel));
    }

    /**
     * 导出虚拟币杠杆列表
     */
    @PreAuthorize("@ss.hasPermi('coin:level:export')")
    @Log(title = "虚拟币杠杆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinLevel coinLevel)
    {
        List<CoinLevel> list = coinLevelService.selectCoinLevelList(coinLevel);
        ExcelUtil<CoinLevel> util = new ExcelUtil<CoinLevel>(CoinLevel.class);
        util.exportExcel(response, list, "虚拟币杠杆数据");
    }

    /**
     * 获取虚拟币杠杆详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinLevelService.selectCoinLevelById(id));
    }

    /**
     * 新增虚拟币杠杆
     */
    @PreAuthorize("@ss.hasPermi('coin:level:add')")
    @Log(title = "虚拟币杠杆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinLevel coinLevel)
    {
        return toAjax(coinLevelService.insertCoinLevel(coinLevel));
    }

    /**
     * 修改虚拟币杠杆
     */
    @PreAuthorize("@ss.hasPermi('coin:level:edit')")
    @Log(title = "虚拟币杠杆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinLevel coinLevel)
    {
        return toAjax(coinLevelService.updateCoinLevel(coinLevel));
    }

    /**
     * 删除虚拟币杠杆
     */
    @PreAuthorize("@ss.hasPermi('coin:level:remove')")
    @Log(title = "虚拟币杠杆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinLevelService.deleteCoinLevelByIds(ids));
    }
}
