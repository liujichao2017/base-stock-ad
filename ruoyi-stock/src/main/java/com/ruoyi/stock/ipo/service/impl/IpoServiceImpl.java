package com.ruoyi.stock.ipo.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.ipo.mapper.IpoMapper;
import com.ruoyi.stock.ipo.domain.Ipo;
import com.ruoyi.stock.ipo.service.IIpoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * IPO(新股)Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class IpoServiceImpl implements IIpoService
{
    @Autowired
    private IpoMapper ipoMapper;

    /**
     * 查询IPO(新股)
     *
     * @param id IPO(新股)主键
     * @return IPO(新股)
     */
    @Override
    public Ipo selectIpoById(Long id) {
        return ipoMapper.selectById(id);
    }

    /**
     * 查询IPO(新股)列表
     *
     * @param ipo IPO(新股)
     * @return IPO(新股)
     */
    @Override
    public List<Ipo> selectIpoList(Ipo ipo) {
        QueryWrapper<Ipo> ew = new QueryWrapper<>();
        ew.setEntity(ipo);
        ew.orderByDesc("create_time");
        return ipoMapper.selectList(ew);
    }

    /**
     * 新增IPO(新股)
     *
     * @param ipo IPO(新股)
     * @return 结果
     */
    @Override
    public int insertIpo(Ipo ipo)
    {
        ipo.setCreateTime(DateUtils.getNowDate());
        return ipoMapper.insert(ipo);
    }

    /**
     * 修改IPO(新股)
     *
     * @param ipo IPO(新股)
     * @return 结果
     */
    @Override
    public int updateIpo(Ipo ipo)
    {
        ipo.setUpdateTime(DateUtils.getNowDate());
        return ipoMapper.updateById(ipo);
    }

    /**
     * 批量删除IPO(新股)
     *
     * @param ids 需要删除的IPO(新股)主键
     * @return 结果
     */
    @Override
    public int deleteIpoByIds(List<Long> ids)
    {
        QueryWrapper<Ipo> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return ipoMapper.delete(ew);
    }

    /**
     * 删除IPO(新股)信息
     *
     * @param id IPO(新股)主键
     * @return 结果
     */
    @Override
    public int deleteIpoById(Long id)
    {
        return ipoMapper.deleteById(id);
    }
}
