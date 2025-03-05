package com.ruoyi.stock.funds.service;

import java.util.List;
import com.ruoyi.stock.funds.domain.MemberFundsConvert;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 兑换记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberFundsConvertService
{
    /**
     * 查询兑换记录
     *
     * @param id 兑换记录主键
     * @return 兑换记录
     */
    public MemberFundsConvert selectMemberFundsConvertById(Long id);

    /**
     * 查询兑换记录列表
     *
     * @param memberFundsConvert 兑换记录
     * @return 兑换记录集合
     */
    public List<MemberFundsConvert> selectMemberFundsConvertList(MemberFundsConvert memberFundsConvert);

    /**
     * 新增兑换记录
     *
     * @param memberFundsConvert 兑换记录
     * @return 结果
     */
    public int insertMemberFundsConvert(MemberFundsConvert memberFundsConvert);

    /**
     * 修改兑换记录
     *
     * @param memberFundsConvert 兑换记录
     * @return 结果
     */
    public int updateMemberFundsConvert(MemberFundsConvert memberFundsConvert);

    /**
     * 批量删除兑换记录
     *
     * @param ids 需要删除的兑换记录主键集合
     * @return 结果
     */
    public int deleteMemberFundsConvertByIds(List<Long> ids);

    /**
     * 删除兑换记录信息
     *
     * @param id 兑换记录主键
     * @return 结果
     */
    public int deleteMemberFundsConvertById(Long id);
}
