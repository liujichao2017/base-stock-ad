package com.ruoyi.stock.sms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.sms.mapper.SiteSmsConfigMapper;
import com.ruoyi.stock.sms.domain.SiteSmsConfig;
import com.ruoyi.stock.sms.service.ISiteSmsConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 短信配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteSmsConfigServiceImpl implements ISiteSmsConfigService
{
    @Autowired
    private SiteSmsConfigMapper siteSmsConfigMapper;

    /**
     * 查询短信配置
     *
     * @param id 短信配置主键
     * @return 短信配置
     */
    @Override
    public SiteSmsConfig selectSiteSmsConfigById(Long id) {
        return siteSmsConfigMapper.selectById(id);
    }

    /**
     * 查询短信配置列表
     *
     * @param siteSmsConfig 短信配置
     * @return 短信配置
     */
    @Override
    public List<SiteSmsConfig> selectSiteSmsConfigList(SiteSmsConfig siteSmsConfig) {
        QueryWrapper<SiteSmsConfig> ew = new QueryWrapper<>();
        ew.setEntity(siteSmsConfig);
        ew.orderByDesc("create_time");
        return siteSmsConfigMapper.selectList(ew);
    }

    /**
     * 新增短信配置
     *
     * @param siteSmsConfig 短信配置
     * @return 结果
     */
    @Override
    public int insertSiteSmsConfig(SiteSmsConfig siteSmsConfig)
    {
        siteSmsConfig.setCreateTime(DateUtils.getNowDate());
        return siteSmsConfigMapper.insert(siteSmsConfig);
    }

    /**
     * 修改短信配置
     *
     * @param siteSmsConfig 短信配置
     * @return 结果
     */
    @Override
    public int updateSiteSmsConfig(SiteSmsConfig siteSmsConfig)
    {
        siteSmsConfig.setUpdateTime(DateUtils.getNowDate());
        return siteSmsConfigMapper.updateById(siteSmsConfig);
    }

    /**
     * 批量删除短信配置
     *
     * @param ids 需要删除的短信配置主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsConfigByIds(List<Long> ids)
    {
        QueryWrapper<SiteSmsConfig> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteSmsConfigMapper.delete(ew);
    }

    /**
     * 删除短信配置信息
     *
     * @param id 短信配置主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsConfigById(Long id)
    {
        return siteSmsConfigMapper.deleteById(id);
    }
}
