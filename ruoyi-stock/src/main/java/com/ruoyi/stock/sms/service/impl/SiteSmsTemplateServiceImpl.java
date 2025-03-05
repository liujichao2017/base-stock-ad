package com.ruoyi.stock.sms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.sms.mapper.SiteSmsTemplateMapper;
import com.ruoyi.stock.sms.domain.SiteSmsTemplate;
import com.ruoyi.stock.sms.service.ISiteSmsTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 短信模版Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteSmsTemplateServiceImpl implements ISiteSmsTemplateService
{
    @Autowired
    private SiteSmsTemplateMapper siteSmsTemplateMapper;

    /**
     * 查询短信模版
     *
     * @param id 短信模版主键
     * @return 短信模版
     */
    @Override
    public SiteSmsTemplate selectSiteSmsTemplateById(Long id) {
        return siteSmsTemplateMapper.selectById(id);
    }

    /**
     * 查询短信模版列表
     *
     * @param siteSmsTemplate 短信模版
     * @return 短信模版
     */
    @Override
    public List<SiteSmsTemplate> selectSiteSmsTemplateList(SiteSmsTemplate siteSmsTemplate) {
        QueryWrapper<SiteSmsTemplate> ew = new QueryWrapper<>();
        ew.setEntity(siteSmsTemplate);
        ew.orderByDesc("create_time");
        return siteSmsTemplateMapper.selectList(ew);
    }

    /**
     * 新增短信模版
     *
     * @param siteSmsTemplate 短信模版
     * @return 结果
     */
    @Override
    public int insertSiteSmsTemplate(SiteSmsTemplate siteSmsTemplate)
    {
        siteSmsTemplate.setCreateTime(DateUtils.getNowDate());
        return siteSmsTemplateMapper.insert(siteSmsTemplate);
    }

    /**
     * 修改短信模版
     *
     * @param siteSmsTemplate 短信模版
     * @return 结果
     */
    @Override
    public int updateSiteSmsTemplate(SiteSmsTemplate siteSmsTemplate)
    {
        siteSmsTemplate.setUpdateTime(DateUtils.getNowDate());
        return siteSmsTemplateMapper.updateById(siteSmsTemplate);
    }

    /**
     * 批量删除短信模版
     *
     * @param ids 需要删除的短信模版主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsTemplateByIds(List<Long> ids)
    {
        QueryWrapper<SiteSmsTemplate> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteSmsTemplateMapper.delete(ew);
    }

    /**
     * 删除短信模版信息
     *
     * @param id 短信模版主键
     * @return 结果
     */
    @Override
    public int deleteSiteSmsTemplateById(Long id)
    {
        return siteSmsTemplateMapper.deleteById(id);
    }
}
