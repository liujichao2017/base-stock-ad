package com.ruoyi.stock.loan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.loan.domain.MemberLoanRecord;
import com.ruoyi.stock.loan.service.IMemberLoanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户贷款申请Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/loan/loanRecord")
public class MemberLoanRecordController extends BaseController {
    @Autowired
    private IMemberLoanRecordService memberLoanRecordService;

    /**
     * 查询用户贷款申请列表
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberLoanRecord memberLoanRecord) {
        startPage();
        return getDataTable(memberLoanRecordService.selectMemberLoanRecordList(memberLoanRecord));
    }

    /**
     * 导出用户贷款申请列表
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:export')")
    @Log(title = "用户贷款申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberLoanRecord memberLoanRecord) {
        List<MemberLoanRecord> list = memberLoanRecordService.selectMemberLoanRecordList(memberLoanRecord);
        ExcelUtil<MemberLoanRecord> util = new ExcelUtil<MemberLoanRecord>(MemberLoanRecord.class);
        util.exportExcel(response, list, "用户贷款申请数据");
    }

    /**
     * 获取用户贷款申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberLoanRecordService.selectMemberLoanRecordById(id));
    }

    /**
     * 新增用户贷款申请
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:add')")
    @Log(title = "用户贷款申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberLoanRecord memberLoanRecord) {
        return toAjax(memberLoanRecordService.insertMemberLoanRecord(memberLoanRecord));
    }

    /**
     * 修改用户贷款申请
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:edit')")
    @Log(title = "用户贷款申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberLoanRecord memberLoanRecord) {
        return toAjax(memberLoanRecordService.updateMemberLoanRecord(memberLoanRecord));
    }

    /**
     * 删除用户贷款申请
     */
    @PreAuthorize("@ss.hasPermi('loan:loanRecord:remove')")
    @Log(title = "用户贷款申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(memberLoanRecordService.deleteMemberLoanRecordByIds(ids));
    }
}
