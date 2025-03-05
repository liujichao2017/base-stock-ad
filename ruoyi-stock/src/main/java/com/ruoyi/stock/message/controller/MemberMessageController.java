package com.ruoyi.stock.message.controller;

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
import com.ruoyi.stock.message.domain.MemberMessage;
import com.ruoyi.stock.message.service.IMemberMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户消息Controller
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@RestController
@RequestMapping("/message/message")
public class MemberMessageController extends BaseController
{
    @Autowired
    private IMemberMessageService memberMessageService;

    /**
     * 查询客户消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberMessage memberMessage)
    {
        startPage();
        return getDataTable(memberMessageService.selectMemberMessageList(memberMessage));
    }

    /**
     * 导出客户消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:message:export')")
    @Log(title = "客户消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberMessage memberMessage)
    {
        List<MemberMessage> list = memberMessageService.selectMemberMessageList(memberMessage);
        ExcelUtil<MemberMessage> util = new ExcelUtil<MemberMessage>(MemberMessage.class);
        util.exportExcel(response, list, "客户消息数据");
    }

    /**
     * 获取客户消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberMessageService.selectMemberMessageById(id));
    }

    /**
     * 新增客户消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:add')")
    @Log(title = "客户消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MemberMessage memberMessage)
    {
        return toAjax(memberMessageService.insertMemberMessage(memberMessage));
    }

    /**
     * 修改客户消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:edit')")
    @Log(title = "客户消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MemberMessage memberMessage)
    {
        return toAjax(memberMessageService.updateMemberMessage(memberMessage));
    }

    /**
     * 删除客户消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:remove')")
    @Log(title = "客户消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(memberMessageService.deleteMemberMessageByIds(ids));
    }
}
