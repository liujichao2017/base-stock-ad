package com.ruoyi.stock.stock.controller;

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
import com.ruoyi.stock.stock.domain.ClosingDateConfig;
import com.ruoyi.stock.stock.service.IClosingDateConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 节假日Controller
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@RestController
@RequestMapping("/stock/closing")
public class ClosingDateConfigController extends BaseController
{
    @Autowired
    private IClosingDateConfigService closingDateConfigService;

    /**
     * 查询节假日列表
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClosingDateConfig closingDateConfig)
    {
        startPage();
        return getDataTable(closingDateConfigService.selectClosingDateConfigList(closingDateConfig));
    }

    /**
     * 导出节假日列表
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:export')")
    @Log(title = "节假日", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClosingDateConfig closingDateConfig)
    {
        List<ClosingDateConfig> list = closingDateConfigService.selectClosingDateConfigList(closingDateConfig);
        ExcelUtil<ClosingDateConfig> util = new ExcelUtil<ClosingDateConfig>(ClosingDateConfig.class);
        util.exportExcel(response, list, "节假日数据");
    }

    /**
     * 获取节假日详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(closingDateConfigService.selectClosingDateConfigById(id));
    }

    /**
     * 新增节假日
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:add')")
    @Log(title = "节假日", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClosingDateConfig closingDateConfig)
    {
        return toAjax(closingDateConfigService.insertClosingDateConfig(closingDateConfig));
    }

    /**
     * 修改节假日
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:edit')")
    @Log(title = "节假日", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClosingDateConfig closingDateConfig)
    {
        return toAjax(closingDateConfigService.updateClosingDateConfig(closingDateConfig));
    }

    /**
     * 删除节假日
     */
    @PreAuthorize("@ss.hasPermi('stock:closing:remove')")
    @Log(title = "节假日", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(closingDateConfigService.deleteClosingDateConfigByIds(ids));
    }
}
