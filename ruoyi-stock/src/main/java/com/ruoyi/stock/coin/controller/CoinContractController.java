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
import com.ruoyi.stock.coin.domain.CoinContract;
import com.ruoyi.stock.coin.service.ICoinContractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币合约Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/contract")
public class CoinContractController extends BaseController
{
    @Autowired
    private ICoinContractService coinContractService;

    /**
     * 查询虚拟币合约列表
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinContract coinContract)
    {
        startPage();
        return getDataTable(coinContractService.selectCoinContractList(coinContract));
    }

    /**
     * 导出虚拟币合约列表
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:export')")
    @Log(title = "虚拟币合约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinContract coinContract)
    {
        List<CoinContract> list = coinContractService.selectCoinContractList(coinContract);
        ExcelUtil<CoinContract> util = new ExcelUtil<CoinContract>(CoinContract.class);
        util.exportExcel(response, list, "虚拟币合约数据");
    }

    /**
     * 获取虚拟币合约详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinContractService.selectCoinContractById(id));
    }

    /**
     * 新增虚拟币合约
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:add')")
    @Log(title = "虚拟币合约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinContract coinContract)
    {
        return toAjax(coinContractService.insertCoinContract(coinContract));
    }

    /**
     * 修改虚拟币合约
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:edit')")
    @Log(title = "虚拟币合约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinContract coinContract)
    {
        return toAjax(coinContractService.updateCoinContract(coinContract));
    }

    /**
     * 删除虚拟币合约
     */
    @PreAuthorize("@ss.hasPermi('coin:contract:remove')")
    @Log(title = "虚拟币合约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinContractService.deleteCoinContractByIds(ids));
    }
}
