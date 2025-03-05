package com.ruoyi.stock.otc.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.otc.domain.Otc;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * otc(大宗)Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IOtcService
{
    /**
     * 查询otc(大宗)
     *
     * @param id otc(大宗)主键
     * @return otc(大宗)
     */
    public Otc selectOtcById(Long id);

    /**
     * 查询otc(大宗)列表
     *
     * @param otc otc(大宗)
     * @return otc(大宗)集合
     */
    public List<Otc> selectOtcList(Otc otc);

    /**
     * 新增otc(大宗)
     *
     * @param otc otc(大宗)
     * @return 结果
     */
    public AjaxResult insertOtc(Otc otc);

    /**
     * 修改otc(大宗)
     *
     * @param otc otc(大宗)
     * @return 结果
     */
    public AjaxResult updateOtc(Otc otc);

    /**
     * 批量删除otc(大宗)
     *
     * @param ids 需要删除的otc(大宗)主键集合
     * @return 结果
     */
    public int deleteOtcByIds(List<Long> ids);

    /**
     * 删除otc(大宗)信息
     *
     * @param id otc(大宗)主键
     * @return 结果
     */
    public int deleteOtcById(Long id);
}
