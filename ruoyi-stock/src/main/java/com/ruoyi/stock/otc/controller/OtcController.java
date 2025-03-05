package com.ruoyi.stock.otc.controller;

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
import com.ruoyi.stock.otc.domain.Otc;
import com.ruoyi.stock.otc.service.IOtcService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * otc(大宗)Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/otc")
public class OtcController extends BaseController
{
    @Autowired
    private IOtcService otcService;

    /**
     * 查询otc(大宗)列表
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:list')")
    @GetMapping("/list")
    public TableDataInfo list(Otc otc)
    {
        startPage();
        return getDataTable(otcService.selectOtcList(otc));
    }

    /**
     * 导出otc(大宗)列表
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:export')")
    @Log(title = "otc(大宗)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Otc otc)
    {
        List<Otc> list = otcService.selectOtcList(otc);
        ExcelUtil<Otc> util = new ExcelUtil<Otc>(Otc.class);
        util.exportExcel(response, list, "otc(大宗)数据");
    }

    /**
     * 获取otc(大宗)详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(otcService.selectOtcById(id));
    }

    /**
     * 新增otc(大宗)
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:add')")
    @Log(title = "otc(大宗)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Otc otc)
    {
        return otcService.insertOtc(otc);
    }

    /**
     * 修改otc(大宗)
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:edit')")
    @Log(title = "otc(大宗)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Otc otc)
    {
        return otcService.updateOtc(otc);
    }

    /**
     * 删除otc(大宗)
     */
    @PreAuthorize("@ss.hasPermi('stock:otc:remove')")
    @Log(title = "otc(大宗)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(otcService.deleteOtcByIds(ids));
    }
}
