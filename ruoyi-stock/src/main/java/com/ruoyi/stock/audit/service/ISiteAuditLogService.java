package com.ruoyi.stock.audit.service;

import java.util.List;
import com.ruoyi.stock.audit.domain.SiteAuditLog;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户操作Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteAuditLogService
{
    /**
     * 查询客户操作
     *
     * @param id 客户操作主键
     * @return 客户操作
     */
    public SiteAuditLog selectSiteAuditLogById(Long id);

    /**
     * 查询客户操作列表
     *
     * @param siteAuditLog 客户操作
     * @return 客户操作集合
     */
    public List<SiteAuditLog> selectSiteAuditLogList(SiteAuditLog siteAuditLog);

    /**
     * 新增客户操作
     *
     * @param siteAuditLog 客户操作
     * @return 结果
     */
    public int insertSiteAuditLog(SiteAuditLog siteAuditLog);

    /**
     * 修改客户操作
     *
     * @param siteAuditLog 客户操作
     * @return 结果
     */
    public int updateSiteAuditLog(SiteAuditLog siteAuditLog);

    /**
     * 批量删除客户操作
     *
     * @param ids 需要删除的客户操作主键集合
     * @return 结果
     */
    public int deleteSiteAuditLogByIds(List<Long> ids);

    /**
     * 删除客户操作信息
     *
     * @param id 客户操作主键
     * @return 结果
     */
    public int deleteSiteAuditLogById(Long id);
}
