package com.ruoyi.stock.loan.service;

import java.util.List;
import com.ruoyi.stock.loan.domain.MemberLoanRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户贷款申请Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IMemberLoanRecordService
{
    /**
     * 查询用户贷款申请
     *
     * @param id 用户贷款申请主键
     * @return 用户贷款申请
     */
    public MemberLoanRecord selectMemberLoanRecordById(Long id);

    /**
     * 查询用户贷款申请列表
     *
     * @param memberLoanRecord 用户贷款申请
     * @return 用户贷款申请集合
     */
    public List<MemberLoanRecord> selectMemberLoanRecordList(MemberLoanRecord memberLoanRecord);

    /**
     * 新增用户贷款申请
     *
     * @param memberLoanRecord 用户贷款申请
     * @return 结果
     */
    public int insertMemberLoanRecord(MemberLoanRecord memberLoanRecord);

    /**
     * 修改用户贷款申请
     *
     * @param memberLoanRecord 用户贷款申请
     * @return 结果
     */
    public int updateMemberLoanRecord(MemberLoanRecord memberLoanRecord);

    /**
     * 批量删除用户贷款申请
     *
     * @param ids 需要删除的用户贷款申请主键集合
     * @return 结果
     */
    public int deleteMemberLoanRecordByIds(List<Long> ids);

    /**
     * 删除用户贷款申请信息
     *
     * @param id 用户贷款申请主键
     * @return 结果
     */
    public int deleteMemberLoanRecordById(Long id);
}
