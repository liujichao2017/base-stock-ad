package com.ruoyi.stock.fund.service;

import java.util.List;
import com.ruoyi.stock.fund.domain.MemberFundInterestRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金盈利Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IMemberFundInterestRecordService
{
    /**
     * 查询基金盈利
     *
     * @param id 基金盈利主键
     * @return 基金盈利
     */
    public MemberFundInterestRecord selectMemberFundInterestRecordById(Long id);

    /**
     * 查询基金盈利列表
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 基金盈利集合
     */
    public List<MemberFundInterestRecord> selectMemberFundInterestRecordList(MemberFundInterestRecord memberFundInterestRecord);

    /**
     * 新增基金盈利
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 结果
     */
    public int insertMemberFundInterestRecord(MemberFundInterestRecord memberFundInterestRecord);

    /**
     * 修改基金盈利
     *
     * @param memberFundInterestRecord 基金盈利
     * @return 结果
     */
    public int updateMemberFundInterestRecord(MemberFundInterestRecord memberFundInterestRecord);

    /**
     * 批量删除基金盈利
     *
     * @param ids 需要删除的基金盈利主键集合
     * @return 结果
     */
    public int deleteMemberFundInterestRecordByIds(List<Long> ids);

    /**
     * 删除基金盈利信息
     *
     * @param id 基金盈利主键
     * @return 结果
     */
    public int deleteMemberFundInterestRecordById(Long id);
}
