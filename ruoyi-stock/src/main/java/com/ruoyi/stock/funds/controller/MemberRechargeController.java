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
import com.ruoyi.stock.funds.domain.MemberRecharge;
import com.ruoyi.stock.funds.service.IMemberRechargeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户充值Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/funds/recharge")
public class MemberRechargeController extends BaseController
{
    @Autowired
    private IMemberRechargeService memberRechargeService;

    /**
     * 查询客户充值列表
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberRecharge memberRecharge)
    {
        startPage();
        return getDataTable(memberRechargeService.selectMemberRechargeList(memberRecharge));
    }

    /**
     * 导出客户充值列表
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:export')")
    @Log(title = "客户充值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberRecharge memberRecharge)
    {
        List<MemberRecharge> list = memberRechargeService.selectMemberRechargeList(memberRecharge);
        ExcelUtil<MemberRecharge> util = new ExcelUtil<MemberRecharge>(MemberRecharge.class);
        util.exportExcel(response, list, "客户充值数据");
    }

    /**
     * 获取客户充值详细信息
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberRechargeService.selectMemberRechargeById(id));
    }

    /**
     * 审核客户充值
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:check')")
    @Log(title = "审核客户充值", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult check(@RequestBody MemberRecharge memberRecharge)
    {
        return toAjax(memberRechargeService.checkMemberRecharge(memberRecharge));
    }

    /**
     * 新增客户充值
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:add')")
    @Log(title = "客户充值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberRecharge memberRecharge)
    {
        return toAjax(memberRechargeService.insertMemberRecharge(memberRecharge));
    }

    /**
     * 修改客户充值
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:edit')")
    @Log(title = "客户充值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberRecharge memberRecharge)
    {
        return toAjax(memberRechargeService.updateMemberRecharge(memberRecharge));
    }

    /**
     * 删除客户充值
     */
    @PreAuthorize("@ss.hasPermi('funds:recharge:remove')")
    @Log(title = "客户充值", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberRechargeService.deleteMemberRechargeByIds(ids));
    }
}
