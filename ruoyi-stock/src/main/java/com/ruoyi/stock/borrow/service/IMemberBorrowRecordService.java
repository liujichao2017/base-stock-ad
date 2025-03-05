package com.ruoyi.stock.borrow.service;

import java.util.List;
import com.ruoyi.stock.borrow.domain.MemberBorrowRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 借劵记录Service接口
 *
 * @author ruoyi
 * @date 2024-12-11
 */
public interface IMemberBorrowRecordService
{
    /**
     * 查询借劵记录
     *
     * @param id 借劵记录主键
     * @return 借劵记录
     */
    public MemberBorrowRecord selectMemberBorrowRecordById(Long id);

    /**
     * 查询借劵记录列表
     *
     * @param memberBorrowRecord 借劵记录
     * @return 借劵记录集合
     */
    public List<MemberBorrowRecord> selectMemberBorrowRecordList(MemberBorrowRecord memberBorrowRecord);

    /**
     * 新增借劵记录
     *
     * @param memberBorrowRecord 借劵记录
     * @return 结果
     */
    public int insertMemberBorrowRecord(MemberBorrowRecord memberBorrowRecord);

    /**
     * 修改借劵记录
     *
     * @param memberBorrowRecord 借劵记录
     * @return 结果
     */
    public int updateMemberBorrowRecord(MemberBorrowRecord memberBorrowRecord);

    /**
     * 批量删除借劵记录
     *
     * @param ids 需要删除的借劵记录主键集合
     * @return 结果
     */
    public int deleteMemberBorrowRecordByIds(List<Long> ids);

    /**
     * 删除借劵记录信息
     *
     * @param id 借劵记录主键
     * @return 结果
     */
    public int deleteMemberBorrowRecordById(Long id);
}
