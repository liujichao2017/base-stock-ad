package com.ruoyi.stock.sms.service;

import java.util.List;
import com.ruoyi.stock.sms.domain.SiteSmsTemplate;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信模版Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteSmsTemplateService
{
    /**
     * 查询短信模版
     *
     * @param id 短信模版主键
     * @return 短信模版
     */
    public SiteSmsTemplate selectSiteSmsTemplateById(Long id);

    /**
     * 查询短信模版列表
     *
     * @param siteSmsTemplate 短信模版
     * @return 短信模版集合
     */
    public List<SiteSmsTemplate> selectSiteSmsTemplateList(SiteSmsTemplate siteSmsTemplate);

    /**
     * 新增短信模版
     *
     * @param siteSmsTemplate 短信模版
     * @return 结果
     */
    public int insertSiteSmsTemplate(SiteSmsTemplate siteSmsTemplate);

    /**
     * 修改短信模版
     *
     * @param siteSmsTemplate 短信模版
     * @return 结果
     */
    public int updateSiteSmsTemplate(SiteSmsTemplate siteSmsTemplate);

    /**
     * 批量删除短信模版
     *
     * @param ids 需要删除的短信模版主键集合
     * @return 结果
     */
    public int deleteSiteSmsTemplateByIds(List<Long> ids);

    /**
     * 删除短信模版信息
     *
     * @param id 短信模版主键
     * @return 结果
     */
    public int deleteSiteSmsTemplateById(Long id);
}
