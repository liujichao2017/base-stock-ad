package com.ruoyi.stock.audit.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.audit.mapper.SiteAuditLogMapper;
import com.ruoyi.stock.audit.domain.SiteAuditLog;
import com.ruoyi.stock.audit.service.ISiteAuditLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 客户操作Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteAuditLogServiceImpl implements ISiteAuditLogService
{
    @Autowired
    private SiteAuditLogMapper siteAuditLogMapper;

    /**
     * 查询客户操作
     *
     * @param id 客户操作主键
     * @return 客户操作
     */
    @Override
    public SiteAuditLog selectSiteAuditLogById(Long id) {
        return siteAuditLogMapper.selectById(id);
    }

    /**
     * 查询客户操作列表
     *
     * @param siteAuditLog 客户操作
     * @return 客户操作
     */
    @Override
    public List<SiteAuditLog> selectSiteAuditLogList(SiteAuditLog siteAuditLog) {
        QueryWrapper<SiteAuditLog> ew = new QueryWrapper<>();
        ew.setEntity(siteAuditLog);
        ew.orderByDesc("create_time");
        return siteAuditLogMapper.selectList(ew);
    }

    /**
     * 新增客户操作
     *
     * @param siteAuditLog 客户操作
     * @return 结果
     */
    @Override
    public int insertSiteAuditLog(SiteAuditLog siteAuditLog)
    {
        siteAuditLog.setCreateTime(DateUtils.getNowDate());
        return siteAuditLogMapper.insert(siteAuditLog);
    }

    /**
     * 修改客户操作
     *
     * @param siteAuditLog 客户操作
     * @return 结果
     */
    @Override
    public int updateSiteAuditLog(SiteAuditLog siteAuditLog)
    {
        siteAuditLog.setUpdateTime(DateUtils.getNowDate());
        return siteAuditLogMapper.updateById(siteAuditLog);
    }

    /**
     * 批量删除客户操作
     *
     * @param ids 需要删除的客户操作主键
     * @return 结果
     */
    @Override
    public int deleteSiteAuditLogByIds(List<Long> ids)
    {
        QueryWrapper<SiteAuditLog> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteAuditLogMapper.delete(ew);
    }

    /**
     * 删除客户操作信息
     *
     * @param id 客户操作主键
     * @return 结果
     */
    @Override
    public int deleteSiteAuditLogById(Long id)
    {
        return siteAuditLogMapper.deleteById(id);
    }
}
