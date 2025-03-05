package com.ruoyi.stock.sms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.sms.mapper.SiteSmsLogMapper;
import com.ruoyi.stock.sms.domain.SiteSmsLog;
import com.ruoyi.stock.sms.service.ISiteSmsLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 短信记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteSmsLogServiceImpl implements ISiteSmsLogService
{
    @Autowired
    private SiteSmsLogMapper siteSmsLogMapper;

    /**
     * 查询短信记录
     *
     * @param id 短信记录主键
     * @return 短信记录
     */
    @Override
    public SiteSmsLog selectSiteSmsLogById(Long id) {
        return siteSmsLogMapper.selectById(id);
    }

    /**
     * 查询短信记录列表
     *
     * @param siteSmsLog 短信记录
     * @return 短信记录
     */
    @Override
    public List<SiteSmsLog> selectSiteSmsLogList(SiteSmsLog siteSmsLog) {
        QueryWrapper<SiteSmsLog> ew = new QueryWrapper<>();
        ew.like(LogicUtils.isNotBlank(siteSmsLog.getPhone()), "phone", siteSmsLog.getPhone());
        ew.like(LogicUtils.isNotBlank(siteSmsLog.getCode()), "code", siteSmsLog.getCode());
        ew.like(LogicUtils.isNotNull(siteSmsLog.getStatus()), "status", siteSmsLog.getStatus());
        ew.orderByDesc("create_time");
        return siteSmsLogMapper.selectList(ew);
    }

    /**
     * 新增短信记录
     *
     * @param siteSmsLog 短信记录
     * @return 结果
     */
    @Override
    public int insertSiteSmsLog(SiteSmsLog siteSmsLog)
    {
        siteSmsLog.setCreateTime(DateUtils.getNowDate());
        return siteSmsLogMapper.insert(siteSmsLog);
    }

    /**
     * 修改短信记录
     *
     * @param siteSmsLog 短信记录
     * @return 结果
     */
    @Override
    public int updateSiteSmsLog(SiteSmsLog siteSmsLog)
    {
        siteSmsLog.setUpdateTime(DateUtils.getNowDate());
        return siteSmsLogMapper.updateById(siteSmsLog);
    }

    /**
     * 批量删除短信记录
     *
     * @param ids 需要删除的短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsLogByIds(List<Long> ids)
    {
        QueryWrapper<SiteSmsLog> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteSmsLogMapper.delete(ew);
    }

    /**
     * 删除短信记录信息
     *
     * @param id 短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsLogById(Long id)
    {
        return siteSmsLogMapper.deleteById(id);
    }
}
