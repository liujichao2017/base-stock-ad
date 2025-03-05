package com.ruoyi.stock.funds.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.funds.domain.MemberFundsLogs;
import com.ruoyi.stock.funds.service.IMemberFundsLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资金动态Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/funds/fundslog")
public class MemberFundsLogsController extends BaseController {
    @Autowired
    private IMemberFundsLogsService memberFundsLogsService;

    /**
     * 查询资金动态列表
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberFundsLogs memberFundsLogs) {
        startPage();
        return getDataTable(memberFundsLogsService.selectMemberFundsLogsList(memberFundsLogs));
    }

    /**
     * 导出资金动态列表
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:export')")
    @Log(title = "资金动态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberFundsLogs memberFundsLogs) {
        List<MemberFundsLogs> list = memberFundsLogsService.selectMemberFundsLogsList(memberFundsLogs);
        ExcelUtil<MemberFundsLogs> util = new ExcelUtil<MemberFundsLogs>(MemberFundsLogs.class);
        util.exportExcel(response, list, "资金动态数据");
    }

    /**
     * 获取资金动态详细信息
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberFundsLogsService.selectMemberFundsLogsById(id));
    }

    /**
     * 新增资金动态
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:add')")
    @Log(title = "资金动态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberFundsLogs memberFundsLogs) {
        return toAjax(memberFundsLogsService.insertMemberFundsLogs(memberFundsLogs));
    }

    /**
     * 修改资金动态
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:edit')")
    @Log(title = "资金动态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberFundsLogs memberFundsLogs) {
        return toAjax(memberFundsLogsService.updateMemberFundsLogs(memberFundsLogs));
    }

    /**
     * 删除资金动态
     */
    @PreAuthorize("@ss.hasPermi('funds:fundslog:remove')")
    @Log(title = "资金动态", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(memberFundsLogsService.deleteMemberFundsLogsByIds(ids));
    }
}
