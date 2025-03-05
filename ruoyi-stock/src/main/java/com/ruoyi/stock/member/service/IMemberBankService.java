package com.ruoyi.stock.member.service;

import java.util.List;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户银行卡Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberBankService
{
    /**
     * 查询用户银行卡
     *
     * @param id 用户银行卡主键
     * @return 用户银行卡
     */
    public MemberBank selectMemberBankById(Long id);

    /**
     * 查询用户银行卡列表
     *
     * @param memberBank 用户银行卡
     * @return 用户银行卡集合
     */
    public List<MemberBank> selectMemberBankList(MemberBank memberBank);

    /**
     * 新增用户银行卡
     *
     * @param memberBank 用户银行卡
     * @return 结果
     */
    public int insertMemberBank(MemberBank memberBank);

    /**
     * 修改用户银行卡
     *
     * @param memberBank 用户银行卡
     * @return 结果
     */
    public int updateMemberBank(MemberBank memberBank);

    /**
     * 批量删除用户银行卡
     *
     * @param ids 需要删除的用户银行卡主键集合
     * @return 结果
     */
    public int deleteMemberBankByIds(List<Long> ids);

    /**
     * 删除用户银行卡信息
     *
     * @param id 用户银行卡主键
     * @return 结果
     */
    public int deleteMemberBankById(Long id);
}
