package com.ruoyi.stock.stock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.stock.mapper.ClosingDateConfigMapper;
import com.ruoyi.stock.stock.domain.ClosingDateConfig;
import com.ruoyi.stock.stock.service.IClosingDateConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 节假日Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-20
 */
@Service
public class ClosingDateConfigServiceImpl implements IClosingDateConfigService
{
    @Autowired
    private ClosingDateConfigMapper closingDateConfigMapper;

    /**
     * 查询节假日
     *
     * @param id 节假日主键
     * @return 节假日
     */
    @Override
    public ClosingDateConfig selectClosingDateConfigById(Long id) {
        return closingDateConfigMapper.selectById(id);
    }

    /**
     * 查询节假日列表
     *
     * @param closingDateConfig 节假日
     * @return 节假日
     */
    @Override
    public List<ClosingDateConfig> selectClosingDateConfigList(ClosingDateConfig closingDateConfig) {
        QueryWrapper<ClosingDateConfig> ew = new QueryWrapper<>();
        ew.setEntity(closingDateConfig);
        ew.orderByDesc("create_time");
        return closingDateConfigMapper.selectList(ew);
    }

    /**
     * 新增节假日
     *
     * @param closingDateConfig 节假日
     * @return 结果
     */
    @Override
    public int insertClosingDateConfig(ClosingDateConfig closingDateConfig)
    {
        closingDateConfig.setCreateTime(DateUtils.getNowDate());
        return closingDateConfigMapper.insert(closingDateConfig);
    }

    /**
     * 修改节假日
     *
     * @param closingDateConfig 节假日
     * @return 结果
     */
    @Override
    public int updateClosingDateConfig(ClosingDateConfig closingDateConfig)
    {
        closingDateConfig.setUpdateTime(DateUtils.getNowDate());
        return closingDateConfigMapper.updateById(closingDateConfig);
    }

    /**
     * 批量删除节假日
     *
     * @param ids 需要删除的节假日主键
     * @return 结果
     */
    @Override
    public int deleteClosingDateConfigByIds(List<Long> ids)
    {
        QueryWrapper<ClosingDateConfig> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return closingDateConfigMapper.delete(ew);
    }

    /**
     * 删除节假日信息
     *
     * @param id 节假日主键
     * @return 结果
     */
    @Override
    public int deleteClosingDateConfigById(Long id)
    {
        return closingDateConfigMapper.deleteById(id);
    }
}
