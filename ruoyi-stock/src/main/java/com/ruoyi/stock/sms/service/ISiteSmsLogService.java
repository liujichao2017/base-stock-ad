package com.ruoyi.stock.sms.service;

import java.util.List;
import com.ruoyi.stock.sms.domain.SiteSmsLog;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteSmsLogService
{
    /**
     * 查询短信记录
     *
     * @param id 短信记录主键
     * @return 短信记录
     */
    public SiteSmsLog selectSiteSmsLogById(Long id);

    /**
     * 查询短信记录列表
     *
     * @param siteSmsLog 短信记录
     * @return 短信记录集合
     */
    public List<SiteSmsLog> selectSiteSmsLogList(SiteSmsLog siteSmsLog);

    /**
     * 新增短信记录
     *
     * @param siteSmsLog 短信记录
     * @return 结果
     */
    public int insertSiteSmsLog(SiteSmsLog siteSmsLog);

    /**
     * 修改短信记录
     *
     * @param siteSmsLog 短信记录
     * @return 结果
     */
    public int updateSiteSmsLog(SiteSmsLog siteSmsLog);

    /**
     * 批量删除短信记录
     *
     * @param ids 需要删除的短信记录主键集合
     * @return 结果
     */
    public int deleteSiteSmsLogByIds(List<Long> ids);

    /**
     * 删除短信记录信息
     *
     * @param id 短信记录主键
     * @return 结果
     */
    public int deleteSiteSmsLogById(Long id);
}
