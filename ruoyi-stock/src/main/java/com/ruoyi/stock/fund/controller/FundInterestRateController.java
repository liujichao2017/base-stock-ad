package com.ruoyi.stock.fund.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.fund.domain.FundInterestRate;
import com.ruoyi.stock.fund.service.IFundInterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 日收益Controller
 *
 * @author ruoyi
 * @date 2025-01-06
 */
@RestController
@RequestMapping("/interestrate/interestrate")
public class FundInterestRateController extends BaseController {
    @Autowired
    private IFundInterestRateService memberFundInterestRateService;

    /**
     * 查询日收益列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FundInterestRate memberFundInterestRate) {
//        startPage();
        return getDataTable(memberFundInterestRateService.selectMemberFundInterestRateList(memberFundInterestRate));
    }

    /**
     * 获取日收益详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberFundInterestRateService.selectMemberFundInterestRateById(id));
    }

    /**
     * 修改日收益
     */
    @PreAuthorize("@ss.hasPermi('fund:fundLists:edit')")
    @Log(title = "日收益", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FundInterestRate memberFundInterestRate) {
        return toAjax(memberFundInterestRateService.updateMemberFundInterestRate(memberFundInterestRate));
    }
}
