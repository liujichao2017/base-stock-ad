package com.ruoyi.stock.member.service;

import java.util.List;
import com.ruoyi.stock.member.domain.Member;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.stock.member.vo.SendSmsVo;
import com.ruoyi.stock.member.vo.UpdateAmtVo;

/**
 * 会员Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberService
{
    /**
     * 查询会员
     *
     * @param id 会员主键
     * @return 会员
     */
    public Member selectMemberById(Long id);

    /**
     * 查询会员列表
     *
     * @param member 会员
     * @return 会员集合
     */
    public List<Member> selectMemberList(Member member);

    /**
     * 新增会员
     *
     * @param member 会员
     * @return 结果
     */
    public int insertMember(Member member);

    /**
     * 修改会员
     *
     * @param member 会员
     * @return 结果
     */
    public int updateMember(Member member);

    /**
     * 批量删除会员
     *
     * @param ids 需要删除的会员主键集合
     * @return 结果
     */
    public int deleteMemberByIds(List<Long> ids);

    /**
     * 删除会员信息
     *
     * @param id 会员主键
     * @return 结果
     */
    public int deleteMemberById(Long id);

    boolean realName(Member member);

    boolean updateAmt(UpdateAmtVo updateAmtVo);

    boolean bankCard(MemberBank bank);

    boolean bankCardReset(MemberBank bank);

    String sendSms(SendSmsVo body);
}
