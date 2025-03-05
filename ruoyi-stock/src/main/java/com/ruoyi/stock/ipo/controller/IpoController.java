package com.ruoyi.stock.ipo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.ipo.domain.Ipo;
import com.ruoyi.stock.ipo.service.IIpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * IPO(新股)Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/ipo")
public class IpoController extends BaseController {
    @Autowired
    private IIpoService ipoService;

    /**
     * 查询IPO(新股)列表
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Ipo ipo) {
        startPage();
        return getDataTable(ipoService.selectIpoList(ipo));
    }

    /**
     * 导出IPO(新股)列表
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:export')")
    @Log(title = "IPO(新股)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ipo ipo) {
        List<Ipo> list = ipoService.selectIpoList(ipo);
        ExcelUtil<Ipo> util = new ExcelUtil<Ipo>(Ipo.class);
        util.exportExcel(response, list, "IPO(新股)数据");
    }

    /**
     * 获取IPO(新股)详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(ipoService.selectIpoById(id));
    }

    /**
     * 新增IPO(新股)
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:add')")
    @Log(title = "IPO(新股)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ipo ipo) {
        return toAjax(ipoService.insertIpo(ipo));
    }

    /**
     * 修改IPO(新股)
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:edit')")
    @Log(title = "IPO(新股)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ipo ipo) {
        return toAjax(ipoService.updateIpo(ipo));
    }

    /**
     * 删除IPO(新股)
     */
    @PreAuthorize("@ss.hasPermi('stock:ipo:remove')")
    @Log(title = "IPO(新股)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(ipoService.deleteIpoByIds(ids));
    }
}
