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
import com.ruoyi.stock.borrow.domain.BorrowCoupons;
import com.ruoyi.stock.borrow.service.IBorrowCouponsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 存股借卷配置Controller
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@RestController
@RequestMapping("/borrow/product")
public class BorrowCouponsController extends BaseController
{
    @Autowired
    private IBorrowCouponsService borrowCouponsService;

    /**
     * 查询存股借卷配置列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(BorrowCoupons borrowCoupons)
    {
        startPage();
        return getDataTable(borrowCouponsService.selectBorrowCouponsList(borrowCoupons));
    }

    /**
     * 导出存股借卷配置列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:export')")
    @Log(title = "存股借卷配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BorrowCoupons borrowCoupons)
    {
        List<BorrowCoupons> list = borrowCouponsService.selectBorrowCouponsList(borrowCoupons);
        ExcelUtil<BorrowCoupons> util = new ExcelUtil<BorrowCoupons>(BorrowCoupons.class);
        util.exportExcel(response, list, "存股借卷配置数据");
    }

    /**
     * 获取存股借卷配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(borrowCouponsService.selectBorrowCouponsById(id));
    }

    /**
     * 新增存股借卷配置
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:add')")
    @Log(title = "存股借卷配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BorrowCoupons borrowCoupons)
    {
        return toAjax(borrowCouponsService.insertBorrowCoupons(borrowCoupons));
    }

    /**
     * 修改存股借卷配置
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:edit')")
    @Log(title = "存股借卷配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BorrowCoupons borrowCoupons)
    {
        return toAjax(borrowCouponsService.updateBorrowCoupons(borrowCoupons));
    }

    /**
     * 删除存股借卷配置
     */
    @PreAuthorize("@ss.hasPermi('borrow:product:remove')")
    @Log(title = "存股借卷配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(borrowCouponsService.deleteBorrowCouponsByIds(ids));
    }
}
