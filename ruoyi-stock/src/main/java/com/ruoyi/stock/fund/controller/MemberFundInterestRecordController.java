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
import com.ruoyi.stock.fund.domain.MemberFundInterestRecord;
import com.ruoyi.stock.fund.service.IMemberFundInterestRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金盈利Controller
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/fund/interest")
public class MemberFundInterestRecordController extends BaseController
{
    @Autowired
    private IMemberFundInterestRecordService memberFundInterestRecordService;

    /**
     * 查询基金盈利列表
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberFundInterestRecord memberFundInterestRecord)
    {
        startPage();
        return getDataTable(memberFundInterestRecordService.selectMemberFundInterestRecordList(memberFundInterestRecord));
    }

    /**
     * 导出基金盈利列表
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:export')")
    @Log(title = "基金盈利", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberFundInterestRecord memberFundInterestRecord)
    {
        List<MemberFundInterestRecord> list = memberFundInterestRecordService.selectMemberFundInterestRecordList(memberFundInterestRecord);
        ExcelUtil<MemberFundInterestRecord> util = new ExcelUtil<MemberFundInterestRecord>(MemberFundInterestRecord.class);
        util.exportExcel(response, list, "基金盈利数据");
    }

    /**
     * 获取基金盈利详细信息
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberFundInterestRecordService.selectMemberFundInterestRecordById(id));
    }

    /**
     * 新增基金盈利
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:add')")
    @Log(title = "基金盈利", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberFundInterestRecord memberFundInterestRecord)
    {
        return toAjax(memberFundInterestRecordService.insertMemberFundInterestRecord(memberFundInterestRecord));
    }

    /**
     * 修改基金盈利
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:edit')")
    @Log(title = "基金盈利", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberFundInterestRecord memberFundInterestRecord)
    {
        return toAjax(memberFundInterestRecordService.updateMemberFundInterestRecord(memberFundInterestRecord));
    }

    /**
     * 删除基金盈利
     */
    @PreAuthorize("@ss.hasPermi('fund:interest:remove')")
    @Log(title = "基金盈利", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberFundInterestRecordService.deleteMemberFundInterestRecordByIds(ids));
    }
}
