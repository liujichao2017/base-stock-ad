package com.ruoyi.stock.sms.service;

import java.util.List;
import com.ruoyi.stock.sms.domain.SiteSmsConfig;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteSmsConfigService
{
    /**
     * 查询短信配置
     *
     * @param id 短信配置主键
     * @return 短信配置
     */
    public SiteSmsConfig selectSiteSmsConfigById(Long id);

    /**
     * 查询短信配置列表
     *
     * @param siteSmsConfig 短信配置
     * @return 短信配置集合
     */
    public List<SiteSmsConfig> selectSiteSmsConfigList(SiteSmsConfig siteSmsConfig);

    /**
     * 新增短信配置
     *
     * @param siteSmsConfig 短信配置
     * @return 结果
     */
    public int insertSiteSmsConfig(SiteSmsConfig siteSmsConfig);

    /**
     * 修改短信配置
     *
     * @param siteSmsConfig 短信配置
     * @return 结果
     */
    public int updateSiteSmsConfig(SiteSmsConfig siteSmsConfig);

    /**
     * 批量删除短信配置
     *
     * @param ids 需要删除的短信配置主键集合
     * @return 结果
     */
    public int deleteSiteSmsConfigByIds(List<Long> ids);

    /**
     * 删除短信配置信息
     *
     * @param id 短信配置主键
     * @return 结果
     */
    public int deleteSiteSmsConfigById(Long id);
}
