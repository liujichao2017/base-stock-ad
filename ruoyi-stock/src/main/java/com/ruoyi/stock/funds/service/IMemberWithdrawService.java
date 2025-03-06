package com.ruoyi.stock.funds.service;

import java.util.List;

import com.ruoyi.stock.funds.domain.MemberRecharge;
import com.ruoyi.stock.funds.domain.MemberWithdraw;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户提现Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberWithdrawService
{
    /**
     * 查询用户提现
     *
     * @param id 用户提现主键
     * @return 用户提现
     */
    public MemberWithdraw selectMemberWithdrawById(Long id);

    /**
     * 查询用户提现列表
     *
     * @param memberWithdraw 用户提现
     * @return 用户提现集合
     */
    public List<MemberWithdraw> selectMemberWithdrawList(MemberWithdraw memberWithdraw);

    /**
     * 审核客户提现
     *
     * @param memberWithdraw 审核客户提现
     * @return 结果
     */
    public int checkMemberWithdraw(MemberWithdraw memberWithdraw);

    /**
     * 新增用户提现
     *
     * @param memberWithdraw 用户提现
     * @return 结果
     */
    public int insertMemberWithdraw(MemberWithdraw memberWithdraw);

    /**
     * 修改用户提现
     *
     * @param memberWithdraw 用户提现
     * @return 结果
     */
    public int updateMemberWithdraw(MemberWithdraw memberWithdraw);

    /**
     * 批量删除用户提现
     *
     * @param ids 需要删除的用户提现主键集合
     * @return 结果
     */
    public int deleteMemberWithdrawByIds(List<Long> ids);

    /**
     * 删除用户提现信息
     *
     * @param id 用户提现主键
     * @return 结果
     */
    public int deleteMemberWithdrawById(Long id);
}
