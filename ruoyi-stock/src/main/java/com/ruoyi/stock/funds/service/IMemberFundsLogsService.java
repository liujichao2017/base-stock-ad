package com.ruoyi.stock.funds.service;

import java.util.List;
import com.ruoyi.stock.funds.domain.MemberFundsLogs;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 资金动态Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface IMemberFundsLogsService
{
    /**
     * 查询资金动态
     *
     * @param id 资金动态主键
     * @return 资金动态
     */
    public MemberFundsLogs selectMemberFundsLogsById(Long id);

    /**
     * 查询资金动态列表
     *
     * @param memberFundsLogs 资金动态
     * @return 资金动态集合
     */
    public List<MemberFundsLogs> selectMemberFundsLogsList(MemberFundsLogs memberFundsLogs);

    /**
     * 新增资金动态
     *
     * @param memberFundsLogs 资金动态
     * @return 结果
     */
    public int insertMemberFundsLogs(MemberFundsLogs memberFundsLogs);

    /**
     * 修改资金动态
     *
     * @param memberFundsLogs 资金动态
     * @return 结果
     */
    public int updateMemberFundsLogs(MemberFundsLogs memberFundsLogs);

    /**
     * 批量删除资金动态
     *
     * @param ids 需要删除的资金动态主键集合
     * @return 结果
     */
    public int deleteMemberFundsLogsByIds(List<Long> ids);

    /**
     * 删除资金动态信息
     *
     * @param id 资金动态主键
     * @return 结果
     */
    public int deleteMemberFundsLogsById(Long id);
}
