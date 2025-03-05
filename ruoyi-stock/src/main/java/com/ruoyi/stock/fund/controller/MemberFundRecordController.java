package com.ruoyi.stock.fund.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.fund.domain.MemberFundRecord;
import com.ruoyi.stock.fund.service.IMemberFundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基金购买记录Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/fund/fundRecord")
public class MemberFundRecordController extends BaseController {
    @Autowired
    private IMemberFundRecordService memberFundRecordService;

    /**
     * 查询基金购买记录列表
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberFundRecord memberFundRecord) {
        startPage();
        return getDataTable(memberFundRecordService.selectMemberFundRecordList(memberFundRecord));
    }

    /**
     * 导出基金购买记录列表
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:export')")
    @Log(title = "基金购买记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberFundRecord memberFundRecord) {
        List<MemberFundRecord> list = memberFundRecordService.selectMemberFundRecordList(memberFundRecord);
        ExcelUtil<MemberFundRecord> util = new ExcelUtil<MemberFundRecord>(MemberFundRecord.class);
        util.exportExcel(response, list, "基金购买记录数据");
    }

    /**
     * 获取基金购买记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberFundRecordService.selectMemberFundRecordById(id));
    }

    /**
     * 新增基金购买记录
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:add')")
    @Log(title = "基金购买记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberFundRecord memberFundRecord) {
        return toAjax(memberFundRecordService.insertMemberFundRecord(memberFundRecord));
    }

    /**
     * 修改基金购买记录
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:edit')")
    @Log(title = "基金购买记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberFundRecord memberFundRecord) {
        return success(memberFundRecordService.updateMemberFundRecord(memberFundRecord));
    }

    /**
     * 删除基金购买记录
     */
    @PreAuthorize("@ss.hasPermi('fund:fundRecord:remove')")
    @Log(title = "基金购买记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(memberFundRecordService.deleteMemberFundRecordByIds(ids));
    }
}
