package com.ruoyi.stock.config.service.impl;

import java.util.Collections;
import java.util.List;

import com.ruoyi.common.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.config.mapper.DataConfigMapper;
import com.ruoyi.stock.config.domain.DataConfig;
import com.ruoyi.stock.config.service.IDataConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 通用配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class DataConfigServiceImpl implements IDataConfigService
{
    @Autowired
    private DataConfigMapper dataConfigMapper;

    /**
     * 查询通用配置
     *
     * @param id 通用配置主键
     * @return 通用配置
     */
    @Override
    public DataConfig selectDataConfigById(Long id) {
        return dataConfigMapper.selectById(id);
    }

    /**
     * 查询通用配置列表
     *
     * @param query 通用配置
     * @return 通用配置
     */
    @Override
    public List<DataConfig> selectDataConfigList(DataConfig query) {
        QueryWrapper<DataConfig> ew = new QueryWrapper<>();
        ew.like(LogicUtils.isNotBlank(query.getGroup()), "`group`", query.getGroup());
        ew.like(LogicUtils.isNotBlank(query.getKey()), "`key`", query.getKey());
        ew.eq(LogicUtils.isNotBlank(query.getType()), "`type`", query.getType());
        ew.orderByDesc("create_time");
        return dataConfigMapper.selectList(ew);
    }

    /**
     * 新增通用配置
     *
     * @param dataConfig 通用配置
     * @return 结果
     */
    @Override
    public int insertDataConfig(DataConfig dataConfig)
    {
        return dataConfigMapper.insert(dataConfig);
    }

    /**
     * 修改通用配置
     *
     * @param dataConfig 通用配置
     * @return 结果
     */
    @Override
    public int updateDataConfig(DataConfig dataConfig)
    {
        return dataConfigMapper.updateById(dataConfig);
    }

    /**
     * 批量删除通用配置
     *
     * @param ids 需要删除的通用配置主键
     * @return 结果
     */
    @Override
    public int deleteDataConfigByIds(List<Long> ids)
    {
        QueryWrapper<DataConfig> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return dataConfigMapper.delete(ew);
    }

    /**
     * 删除通用配置信息
     *
     * @param id 通用配置主键
     * @return 结果
     */
    @Override
    public int deleteDataConfigById(Long id)
    {
        return dataConfigMapper.deleteById(id);
    }

    @Override
    public List<String> getGroup() {
        return dataConfigMapper.getGroup();
    }

    @Override
    public String getByStr(DataConfig defaultValue) {
        QueryWrapper<DataConfig> ew = new QueryWrapper<>();
        ew.eq("`key`", defaultValue.getKey());
        ew.last("limit 1");
        DataConfig dataConfig = dataConfigMapper.selectOne(ew);
        if (LogicUtils.isNull(dataConfig)) {
            dataConfigMapper.insert(defaultValue);
            return defaultValue.getVal();
        }
        return dataConfig.getVal();
    }
}
