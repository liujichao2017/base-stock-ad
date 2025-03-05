package com.ruoyi.stock.borrow.controller;

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
import com.ruoyi.stock.borrow.domain.MemberBorrowRecord;
import com.ruoyi.stock.borrow.service.IMemberBorrowRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 借劵记录Controller
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@RestController
@RequestMapping("/borrow/record")
public class MemberBorrowRecordController extends BaseController
{
    @Autowired
    private IMemberBorrowRecordService memberBorrowRecordService;

    /**
     * 查询借劵记录列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberBorrowRecord memberBorrowRecord)
    {
        startPage();
        return getDataTable(memberBorrowRecordService.selectMemberBorrowRecordList(memberBorrowRecord));
    }

    /**
     * 导出借劵记录列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:export')")
    @Log(title = "借劵记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberBorrowRecord memberBorrowRecord)
    {
        List<MemberBorrowRecord> list = memberBorrowRecordService.selectMemberBorrowRecordList(memberBorrowRecord);
        ExcelUtil<MemberBorrowRecord> util = new ExcelUtil<MemberBorrowRecord>(MemberBorrowRecord.class);
        util.exportExcel(response, list, "借劵记录数据");
    }

    /**
     * 获取借劵记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberBorrowRecordService.selectMemberBorrowRecordById(id));
    }

    /**
     * 新增借劵记录
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:add')")
    @Log(title = "借劵记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberBorrowRecord memberBorrowRecord)
    {
        return toAjax(memberBorrowRecordService.insertMemberBorrowRecord(memberBorrowRecord));
    }

    /**
     * 修改借劵记录
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:edit')")
    @Log(title = "借劵记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberBorrowRecord memberBorrowRecord)
    {
        return toAjax(memberBorrowRecordService.updateMemberBorrowRecord(memberBorrowRecord));
    }

    /**
     * 删除借劵记录
     */
    @PreAuthorize("@ss.hasPermi('borrow:record:remove')")
    @Log(title = "借劵记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberBorrowRecordService.deleteMemberBorrowRecordByIds(ids));
    }
}
