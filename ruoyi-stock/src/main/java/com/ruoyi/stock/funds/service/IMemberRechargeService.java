package com.ruoyi.stock.funds.service;

import java.util.List;
import com.ruoyi.stock.funds.domain.MemberRecharge;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户充值Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberRechargeService
{
    /**
     * 查询客户充值
     *
     * @param id 客户充值主键
     * @return 客户充值
     */
    public MemberRecharge selectMemberRechargeById(Long id);

    /**
     * 查询客户充值列表
     *
     * @param memberRecharge 客户充值
     * @return 客户充值集合
     */
    public List<MemberRecharge> selectMemberRechargeList(MemberRecharge memberRecharge);

    /**
     * 审核客户充值
     *
     * @param memberRecharge 审核客户充值
     * @return 结果
     */
    public int checkMemberRecharge(MemberRecharge memberRecharge);

    /**
     * 新增客户充值
     *
     * @param memberRecharge 客户充值
     * @return 结果
     */
    public int insertMemberRecharge(MemberRecharge memberRecharge);

    /**
     * 修改客户充值
     *
     * @param memberRecharge 客户充值
     * @return 结果
     */
    public int updateMemberRecharge(MemberRecharge memberRecharge);

    /**
     * 批量删除客户充值
     *
     * @param ids 需要删除的客户充值主键集合
     * @return 结果
     */
    public int deleteMemberRechargeByIds(List<Long> ids);

    /**
     * 删除客户充值信息
     *
     * @param id 客户充值主键
     * @return 结果
     */
    public int deleteMemberRechargeById(Long id);
}
