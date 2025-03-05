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
import com.ruoyi.stock.coin.domain.CoinContractDelegation;
import com.ruoyi.stock.coin.service.ICoinContractDelegationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 虚拟币合约委托Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/delegation")
public class CoinContractDelegationController extends BaseController
{
    @Autowired
    private ICoinContractDelegationService coinContractDelegationService;

    /**
     * 查询虚拟币合约委托列表
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinContractDelegation coinContractDelegation)
    {
        startPage();
        return getDataTable(coinContractDelegationService.selectCoinContractDelegationList(coinContractDelegation));
    }

    /**
     * 导出虚拟币合约委托列表
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:export')")
    @Log(title = "虚拟币合约委托", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinContractDelegation coinContractDelegation)
    {
        List<CoinContractDelegation> list = coinContractDelegationService.selectCoinContractDelegationList(coinContractDelegation);
        ExcelUtil<CoinContractDelegation> util = new ExcelUtil<CoinContractDelegation>(CoinContractDelegation.class);
        util.exportExcel(response, list, "虚拟币合约委托数据");
    }

    /**
     * 获取虚拟币合约委托详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinContractDelegationService.selectCoinContractDelegationById(id));
    }

    /**
     * 新增虚拟币合约委托
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:add')")
    @Log(title = "虚拟币合约委托", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinContractDelegation coinContractDelegation)
    {
        return toAjax(coinContractDelegationService.insertCoinContractDelegation(coinContractDelegation));
    }

    /**
     * 修改虚拟币合约委托
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:edit')")
    @Log(title = "虚拟币合约委托", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinContractDelegation coinContractDelegation)
    {
        return toAjax(coinContractDelegationService.updateCoinContractDelegation(coinContractDelegation));
    }

    /**
     * 删除虚拟币合约委托
     */
    @PreAuthorize("@ss.hasPermi('coin:delegation:remove')")
    @Log(title = "虚拟币合约委托", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinContractDelegationService.deleteCoinContractDelegationByIds(ids));
    }
}
