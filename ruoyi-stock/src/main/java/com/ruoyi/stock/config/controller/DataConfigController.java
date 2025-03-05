package com.ruoyi.stock.config.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.config.domain.DataConfig;
import com.ruoyi.stock.config.service.IDataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通用配置Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/config/common")
public class DataConfigController extends BaseController {
    @Autowired
    private IDataConfigService dataConfigService;

    /**
     * 查询通用配置列表
     */
    @PreAuthorize("@ss.hasPermi('config:common:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataConfig dataConfig) {
        startPage();
        return getDataTable(dataConfigService.selectDataConfigList(dataConfig));
    }

    /**
     * 查询配置组
     */
    @PreAuthorize("@ss.hasPermi('config:common:group')")
    @GetMapping("/group")
    public AjaxResult getGroup() {
        return success(dataConfigService.getGroup());
    }

    /**
     * 导出通用配置列表
     */
    @PreAuthorize("@ss.hasPermi('config:common:export')")
    @Log(title = "通用配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataConfig dataConfig) {
        List<DataConfig> list = dataConfigService.selectDataConfigList(dataConfig);
        ExcelUtil<DataConfig> util = new ExcelUtil<DataConfig>(DataConfig.class);
        util.exportExcel(response, list, "通用配置数据");
    }

    /**
     * 获取通用配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('config:common:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(dataConfigService.selectDataConfigById(id));
    }

    /**
     * 新增通用配置
     */
    @PreAuthorize("@ss.hasPermi('config:common:add')")
    @Log(title = "通用配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataConfig dataConfig) {
        return toAjax(dataConfigService.insertDataConfig(dataConfig));
    }

    /**
     * 修改通用配置
     */
    @PreAuthorize("@ss.hasPermi('config:common:edit')")
    @Log(title = "通用配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataConfig dataConfig) {
        return toAjax(dataConfigService.updateDataConfig(dataConfig));
    }

    /**
     * 删除通用配置
     */
    @PreAuthorize("@ss.hasPermi('config:common:remove')")
    @Log(title = "通用配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(dataConfigService.deleteDataConfigByIds(ids));
    }
}
