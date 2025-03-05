package com.ruoyi.stock.otc.service;

import java.util.List;
import com.ruoyi.stock.otc.domain.OtcRecord;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stock.otc.domain.vo.OtcRecordVo;

/**
 * otc(大宗)记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IOtcRecordService
{
    /**
     * 查询otc(大宗)记录
     *
     * @param id otc(大宗)记录主键
     * @return otc(大宗)记录
     */
    public OtcRecord selectOtcRecordById(Long id);

    /**
     * 查询otc(大宗)记录列表
     *
     * @param otcRecord otc(大宗)记录
     * @return otc(大宗)记录集合
     */
    public List<OtcRecordVo> selectOtcRecordList(OtcRecordVo otcRecord);

    /**
     * 新增otc(大宗)记录
     *
     * @param otcRecord otc(大宗)记录
     * @return 结果
     */
    public int insertOtcRecord(OtcRecord otcRecord);

    /**
     * 修改otc(大宗)记录
     *
     * @param otcRecord otc(大宗)记录
     * @return 结果
     */
    public int updateOtcRecord(OtcRecord otcRecord);

    /**
     * 批量删除otc(大宗)记录
     *
     * @param ids 需要删除的otc(大宗)记录主键集合
     * @return 结果
     */
    public int deleteOtcRecordByIds(List<Long> ids);

    /**
     * 删除otc(大宗)记录信息
     *
     * @param id otc(大宗)记录主键
     * @return 结果
     */
    public int deleteOtcRecordById(Long id);

    public int examOtcRecord(OtcRecord otcRecord);
}
