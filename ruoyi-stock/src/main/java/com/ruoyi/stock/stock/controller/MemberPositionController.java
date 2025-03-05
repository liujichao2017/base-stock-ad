package com.ruoyi.stock.stock.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.service.IMemberPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户持仓Controller
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@RestController
@RequestMapping("/stock/position")
public class MemberPositionController extends BaseController {
    @Autowired
    private IMemberPositionService memberPositionService;

    /**
     * 查询用户持仓列表
     */
    @PreAuthorize("@ss.hasPermi('stock:position:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberPosition memberPosition) {
        startPage();
        return getDataTable(memberPositionService.selectMemberPositionList(memberPosition));
    }

    /**
     * 导出用户持仓列表
     */
    @PreAuthorize("@ss.hasPermi('stock:position:export')")
    @Log(title = "用户持仓", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberPosition memberPosition) {
        List<MemberPosition> list = memberPositionService.selectMemberPositionList(memberPosition);
        ExcelUtil<MemberPosition> util = new ExcelUtil<MemberPosition>(MemberPosition.class);
        util.exportExcel(response, list, "用户持仓数据");
    }

    /**
     * 获取用户持仓详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:position:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberPositionService.selectMemberPositionById(id));
    }

    /**
     * 新增用户持仓
     */
    @PreAuthorize("@ss.hasPermi('stock:position:add')")
    @Log(title = "用户持仓", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberPosition memberPosition) {
        return toAjax(memberPositionService.insertMemberPosition(memberPosition));
    }

    @PreAuthorize("@ss.hasPermi('stock:position:sell')")
    @Log(title = "强制平仓", businessType = BusinessType.UPDATE)
    @PostMapping("/sell")
    public AjaxResult sell(@RequestBody MemberPosition memberPosition) {
        return success(memberPositionService.sell(memberPosition));
    }


    /**
     * 修改用户持仓
     */
    @PreAuthorize("@ss.hasPermi('stock:position:edit')")
    @Log(title = "用户持仓", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberPosition memberPosition) {
        return toAjax(memberPositionService.updateMemberPosition(memberPosition));
    }

    /**
     * 删除用户持仓
     */
    @PreAuthorize("@ss.hasPermi('stock:position:remove')")
    @Log(title = "用户持仓", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(memberPositionService.deleteMemberPositionByIds(ids));
    }
}
