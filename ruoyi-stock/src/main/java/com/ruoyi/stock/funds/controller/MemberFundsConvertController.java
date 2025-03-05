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
import com.ruoyi.stock.funds.domain.MemberFundsConvert;
import com.ruoyi.stock.funds.service.IMemberFundsConvertService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 兑换记录Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/funds/convert")
public class MemberFundsConvertController extends BaseController
{
    @Autowired
    private IMemberFundsConvertService memberFundsConvertService;

    /**
     * 查询兑换记录列表
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberFundsConvert memberFundsConvert)
    {
        startPage();
        return getDataTable(memberFundsConvertService.selectMemberFundsConvertList(memberFundsConvert));
    }

    /**
     * 导出兑换记录列表
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:export')")
    @Log(title = "兑换记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberFundsConvert memberFundsConvert)
    {
        List<MemberFundsConvert> list = memberFundsConvertService.selectMemberFundsConvertList(memberFundsConvert);
        ExcelUtil<MemberFundsConvert> util = new ExcelUtil<MemberFundsConvert>(MemberFundsConvert.class);
        util.exportExcel(response, list, "兑换记录数据");
    }

    /**
     * 获取兑换记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberFundsConvertService.selectMemberFundsConvertById(id));
    }

    /**
     * 新增兑换记录
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:add')")
    @Log(title = "兑换记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberFundsConvert memberFundsConvert)
    {
        return toAjax(memberFundsConvertService.insertMemberFundsConvert(memberFundsConvert));
    }

    /**
     * 修改兑换记录
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:edit')")
    @Log(title = "兑换记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberFundsConvert memberFundsConvert)
    {
        return toAjax(memberFundsConvertService.updateMemberFundsConvert(memberFundsConvert));
    }

    /**
     * 删除兑换记录
     */
    @PreAuthorize("@ss.hasPermi('funds:convert:remove')")
    @Log(title = "兑换记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberFundsConvertService.deleteMemberFundsConvertByIds(ids));
    }
}
