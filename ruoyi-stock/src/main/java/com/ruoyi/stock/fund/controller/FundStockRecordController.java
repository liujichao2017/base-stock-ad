package com.ruoyi.stock.fund.controller;

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
import com.ruoyi.stock.fund.domain.FundStockRecord;
import com.ruoyi.stock.fund.service.IFundStockRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金股票子项Controller
 *
 * @author ruoyi
 * @date 2024-12-16
 */
@RestController
@RequestMapping("/fundstockrecord/fundstockrecord")
public class FundStockRecordController extends BaseController
{
    @Autowired
    private IFundStockRecordService fundStockRecordService;

    /**
     * 查询基金股票子项列表
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(FundStockRecord fundStockRecord)
    {
        startPage();
        return getDataTable(fundStockRecordService.selectFundStockRecordList(fundStockRecord));
    }

    /**
     * 导出基金股票子项列表
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:export')")
    @Log(title = "基金股票子项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FundStockRecord fundStockRecord)
    {
        List<FundStockRecord> list = fundStockRecordService.selectFundStockRecordList(fundStockRecord);
        ExcelUtil<FundStockRecord> util = new ExcelUtil<FundStockRecord>(FundStockRecord.class);
        util.exportExcel(response, list, "基金股票子项数据");
    }

    /**
     * 获取基金股票子项详细信息
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(fundStockRecordService.selectFundStockRecordById(id));
    }

    /**
     * 新增基金股票子项
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:add')")
    @Log(title = "基金股票子项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FundStockRecord fundStockRecord)
    {
        return toAjax(fundStockRecordService.insertFundStockRecord(fundStockRecord));
    }

    /**
     * 修改基金股票子项
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:edit')")
    @Log(title = "基金股票子项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FundStockRecord fundStockRecord)
    {
        return toAjax(fundStockRecordService.updateFundStockRecord(fundStockRecord));
    }

    /**
     * 删除基金股票子项
     */
    @PreAuthorize("@ss.hasPermi('fundstockrecord:fundstockrecord:remove')")
    @Log(title = "基金股票子项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(fundStockRecordService.deleteFundStockRecordByIds(ids));
    }
}
