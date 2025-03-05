package com.ruoyi.stock.message.service;

import java.util.List;
import com.ruoyi.stock.message.domain.MemberMessage;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户消息Service接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface IMemberMessageService
{
    /**
     * 查询客户消息
     *
     * @param id 客户消息主键
     * @return 客户消息
     */
    public MemberMessage selectMemberMessageById(Long id);

    /**
     * 查询客户消息列表
     *
     * @param memberMessage 客户消息
     * @return 客户消息集合
     */
    public List<MemberMessage> selectMemberMessageList(MemberMessage memberMessage);

    /**
     * 新增客户消息
     *
     * @param memberMessage 客户消息
     * @return 结果
     */
    public int insertMemberMessage(MemberMessage memberMessage);

    /**
     * 修改客户消息
     *
     * @param memberMessage 客户消息
     * @return 结果
     */
    public int updateMemberMessage(MemberMessage memberMessage);

    /**
     * 批量删除客户消息
     *
     * @param ids 需要删除的客户消息主键集合
     * @return 结果
     */
    public int deleteMemberMessageByIds(List<Long> ids);

    /**
     * 删除客户消息信息
     *
     * @param id 客户消息主键
     * @return 结果
     */
    public int deleteMemberMessageById(Long id);
}
