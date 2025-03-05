package com.ruoyi.stock.wite.service;

import java.util.List;
import com.ruoyi.stock.wite.domain.WithinDaysRecord;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stock.wite.domain.dto.CreateWareHouseDto;
import com.ruoyi.stock.wite.domain.vo.WithinDaysRecordVo;

/**
 * 用户日内交易记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IWithinDaysRecordService
{
    /**
     * 查询用户日内交易记录
     *
     * @param id 用户日内交易记录主键
     * @return 用户日内交易记录
     */
    public WithinDaysRecord selectWithinDaysRecordById(Long id);

    /**
     * 查询用户日内交易记录列表
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 用户日内交易记录集合
     */
    public List<WithinDaysRecordVo> selectWithinDaysRecordList(WithinDaysRecordVo withinDaysRecord);

    /**
     * 新增用户日内交易记录
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 结果
     */
    public int insertWithinDaysRecord(WithinDaysRecord withinDaysRecord);

    /**
     * 修改用户日内交易记录
     *
     * @param withinDaysRecord 用户日内交易记录
     * @return 结果
     */
    public int updateWithinDaysRecord(WithinDaysRecord withinDaysRecord);

    /**
     * 批量删除用户日内交易记录
     *
     * @param ids 需要删除的用户日内交易记录主键集合
     * @return 结果
     */
    public int deleteWithinDaysRecordByIds(List<Long> ids);

    /**
     * 删除用户日内交易记录信息
     *
     * @param id 用户日内交易记录主键
     * @return 结果
     */
    public int deleteWithinDaysRecordById(Long id);

    public int createHouse(CreateWareHouseDto createWareHouseDto);
}
