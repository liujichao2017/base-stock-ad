package com.ruoyi.stock.fund.service;

import java.util.List;
import com.ruoyi.stock.fund.domain.MemberFundRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金购买记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IMemberFundRecordService
{
    /**
     * 查询基金购买记录
     *
     * @param id 基金购买记录主键
     * @return 基金购买记录
     */
    public MemberFundRecord selectMemberFundRecordById(Long id);

    /**
     * 查询基金购买记录列表
     *
     * @param memberFundRecord 基金购买记录
     * @return 基金购买记录集合
     */
    public List<MemberFundRecord> selectMemberFundRecordList(MemberFundRecord memberFundRecord);

    /**
     * 新增基金购买记录
     *
     * @param memberFundRecord 基金购买记录
     * @return 结果
     */
    public int insertMemberFundRecord(MemberFundRecord memberFundRecord);

    /**
     * 修改基金购买记录
     *
     * @param memberFundRecord 基金购买记录
     * @return 结果
     */
    public int updateMemberFundRecord(MemberFundRecord memberFundRecord);

    /**
     * 批量删除基金购买记录
     *
     * @param ids 需要删除的基金购买记录主键集合
     * @return 结果
     */
    public int deleteMemberFundRecordByIds(List<Long> ids);

    /**
     * 删除基金购买记录信息
     *
     * @param id 基金购买记录主键
     * @return 结果
     */
    public int deleteMemberFundRecordById(Long id);
}
