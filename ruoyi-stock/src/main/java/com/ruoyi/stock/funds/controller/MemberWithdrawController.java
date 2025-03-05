package com.ruoyi.stock.funds.controller;

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
import com.ruoyi.stock.funds.domain.MemberWithdraw;
import com.ruoyi.stock.funds.service.IMemberWithdrawService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户提现Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/funds/withdraw")
public class MemberWithdrawController extends BaseController
{
    @Autowired
    private IMemberWithdrawService memberWithdrawService;

    /**
     * 查询用户提现列表
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberWithdraw memberWithdraw)
    {
        startPage();
        return getDataTable(memberWithdrawService.selectMemberWithdrawList(memberWithdraw));
    }

    /**
     * 导出用户提现列表
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:export')")
    @Log(title = "用户提现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberWithdraw memberWithdraw)
    {
        List<MemberWithdraw> list = memberWithdrawService.selectMemberWithdrawList(memberWithdraw);
        ExcelUtil<MemberWithdraw> util = new ExcelUtil<MemberWithdraw>(MemberWithdraw.class);
        util.exportExcel(response, list, "用户提现数据");
    }

    /**
     * 获取用户提现详细信息
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberWithdrawService.selectMemberWithdrawById(id));
    }

    /**
     * 新增用户提现
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:add')")
    @Log(title = "用户提现", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberWithdraw memberWithdraw)
    {
        return toAjax(memberWithdrawService.insertMemberWithdraw(memberWithdraw));
    }

    /**
     * 修改用户提现
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:edit')")
    @Log(title = "用户提现", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberWithdraw memberWithdraw)
    {
        return toAjax(memberWithdrawService.updateMemberWithdraw(memberWithdraw));
    }

    /**
     * 删除用户提现
     */
    @PreAuthorize("@ss.hasPermi('funds:withdraw:remove')")
    @Log(title = "用户提现", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberWithdrawService.deleteMemberWithdrawByIds(ids));
    }
}
