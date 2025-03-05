package com.ruoyi.stock.config.service;

import java.util.List;
import com.ruoyi.stock.config.domain.DataConfig;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通用配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IDataConfigService
{
    /**
     * 查询通用配置
     *
     * @param id 通用配置主键
     * @return 通用配置
     */
    public DataConfig selectDataConfigById(Long id);

    /**
     * 查询通用配置列表
     *
     * @param dataConfig 通用配置
     * @return 通用配置集合
     */
    public List<DataConfig> selectDataConfigList(DataConfig dataConfig);

    /**
     * 新增通用配置
     *
     * @param dataConfig 通用配置
     * @return 结果
     */
    public int insertDataConfig(DataConfig dataConfig);

    /**
     * 修改通用配置
     *
     * @param dataConfig 通用配置
     * @return 结果
     */
    public int updateDataConfig(DataConfig dataConfig);

    /**
     * 批量删除通用配置
     *
     * @param ids 需要删除的通用配置主键集合
     * @return 结果
     */
    public int deleteDataConfigByIds(List<Long> ids);

    /**
     * 删除通用配置信息
     *
     * @param id 通用配置主键
     * @return 结果
     */
    public int deleteDataConfigById(Long id);

    List<String> getGroup();

    String getByStr(DataConfig dataConfig);

}
