package com.ruoyi.stock.ipo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.ipo.domain.IpoRecord;
import com.ruoyi.stock.ipo.domain.IpoRecordDTO;
import com.ruoyi.stock.ipo.domain.vo.IpoRecordVo;
import com.ruoyi.stock.ipo.service.IIpoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * IPO(新股)申购记录Controller
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/stock/ipoRecord")
public class IpoRecordController extends BaseController
{
    @Autowired
    private IIpoRecordService ipoRecordService;

    /**
     * 查询IPO(新股)申购记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(IpoRecordVo ipoRecord)
    {
        startPage();
        return getDataTable(ipoRecordService.selectIpoRecordList(ipoRecord));
    }

    /**
     * 导出IPO(新股)申购记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:export')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IpoRecordVo ipoRecord)
    {
        List<IpoRecordVo> list = ipoRecordService.selectIpoRecordList(ipoRecord);
        ExcelUtil<IpoRecordVo> util = new ExcelUtil<IpoRecordVo>(IpoRecordVo.class);
        util.exportExcel(response, list, "IPO(新股)申购记录数据");
    }

    /**
     * 获取IPO(新股)申购记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ipoRecordService.selectIpoRecordById(id));
    }

    /**
     * 新增IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:add')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IpoRecord ipoRecord)
    {
        return toAjax(ipoRecordService.insertIpoRecord(ipoRecord));
    }

    /**
     * 修改IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:edit')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IpoRecord ipoRecord)
    {
        return toAjax(ipoRecordService.updateIpoRecord(ipoRecord));
    }

    /**
     * 删除IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:remove')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(ipoRecordService.deleteIpoRecordByIds(ids));
    }

    /**
     * 审批IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:exam')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.UPDATE)
    @PostMapping("/exam")
    public AjaxResult exam(@RequestBody IpoRecordDTO ipoRecord){
        return toAjax(ipoRecordService.examIpoRecord(ipoRecord));
    }
    /**
     * 划转IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:transfer')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.UPDATE)
    @PostMapping("/transfer/{id}")
    public AjaxResult transfer(@PathVariable Long id){
        return ipoRecordService.transfer(id);
    }
    /**
     * 转持仓IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:traPos')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.UPDATE)
    @PostMapping("/traPos/{id}")
    public AjaxResult traPos(@PathVariable Long id){
        return ipoRecordService.traPos(id);
    }
    /**
     * 退款IPO(新股)申购记录
     */
    @PreAuthorize("@ss.hasPermi('stock:ipoRecord:refund')")
    @Log(title = "IPO(新股)申购记录", businessType = BusinessType.UPDATE)
    @PostMapping("/refund/{id}")
    public AjaxResult refund(@PathVariable Long id){
        return ipoRecordService.refund(id);
    }}
