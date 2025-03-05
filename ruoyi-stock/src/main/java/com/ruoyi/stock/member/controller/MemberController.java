package com.ruoyi.stock.member.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.stock.member.service.IMemberService;
import com.ruoyi.stock.member.vo.SendSmsVo;
import com.ruoyi.stock.member.vo.UpdateAmtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/member/member")
public class MemberController extends BaseController {
    @Autowired
    private IMemberService memberService;

    /**
     * 查询会员列表
     */
    @PreAuthorize("@ss.hasPermi('member:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(Member member) {
        startPage();
        return getDataTable(memberService.selectMemberList(member));
    }

    /**
     * 导出会员列表
     */
    @PreAuthorize("@ss.hasPermi('member:member:export')")
    @Log(title = "会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Member member) {
        List<Member> list = memberService.selectMemberList(member);
        ExcelUtil<Member> util = new ExcelUtil<Member>(Member.class);
        util.exportExcel(response, list, "会员数据");
    }

    /**
     * 获取会员详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:member:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberService.selectMemberById(id));
    }

    /**
     * 新增会员
     */
    @PreAuthorize("@ss.hasPermi('member:member:add')")
    @Log(title = "会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Member member) {
        return toAjax(memberService.insertMember(member));
    }

    /**
     * 修改会员
     */
    @PreAuthorize("@ss.hasPermi('member:member:edit')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Member member) {
        return toAjax(memberService.updateMember(member));
    }


    /**
     * 发送短信验证码
     */
    @PreAuthorize("@ss.hasPermi('member:member:edit')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping("/sendSms")
    public AjaxResult sendSms(@RequestBody SendSmsVo body) {
        return success("成功", memberService.sendSms(body));
    }


    /**
     * 删除会员
     */
    @PreAuthorize("@ss.hasPermi('member:member:remove')")
    @Log(title = "会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(memberService.deleteMemberByIds(ids));
    }

    /**
     * 实名审批
     */
    @PreAuthorize("@ss.hasPermi('member:member:realName')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping("/realName")
    public AjaxResult realName(@RequestBody Member member) {
        return success(memberService.realName(member));
    }

    /**
     * 修改资金
     */
    @PreAuthorize("@ss.hasPermi('member:member:updateAmt')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping("/updateAmt")
    public AjaxResult updateAmt(@RequestBody UpdateAmtVo updateAmtVo) {
        return success(memberService.updateAmt(updateAmtVo));
    }



    /**
     * 修改银行卡
     */
    @PreAuthorize("@ss.hasPermi('member:member:bankCard')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping("/bankCard")
    public AjaxResult bankCard(@RequestBody MemberBank bank) {
        return success(memberService.bankCard(bank));
    }

    /**
     * 银行卡重置
     */
    @PreAuthorize("@ss.hasPermi('member:member:bankCard')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping("/bankCardReset")
    public AjaxResult bankCardReset(@RequestBody MemberBank bank) {
        return success(memberService.bankCardReset(bank));
    }

    /**
     * 持仓用户搜索
     *
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/search/{phone}")
    public AjaxResult getMember(@PathVariable String phone) {
        if (StringUtils.isEmpty(phone)) return AjaxResult.error("手机号不能为空");
        Member member = new Member();
        member.setPhone(phone);
        List<Member> members = memberService.selectMemberList(member);
        if (members.isEmpty()) {
            member = new Member();
            try {
                member.setId(Long.parseLong(phone));
            } catch (NumberFormatException e) {
                return AjaxResult.error("该客户不存在");
            }
            members = memberService.selectMemberList(member);
        }
        if (members.isEmpty()) return AjaxResult.error("该客户不存在");
        return AjaxResult.success(members.get(0));
    }
}
