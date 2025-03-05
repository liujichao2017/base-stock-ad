package com.ruoyi.stock.ipo.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.ipo.domain.IpoRecord;
import com.ruoyi.stock.ipo.domain.IpoRecordDTO;
import com.ruoyi.stock.ipo.domain.vo.IpoRecordVo;

import java.util.List;

/**
 * IPO(新股)申购记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IIpoRecordService
{
    /**
     * 查询IPO(新股)申购记录
     *
     * @param id IPO(新股)申购记录主键
     * @return IPO(新股)申购记录
     */
    public IpoRecord selectIpoRecordById(Long id);

    /**
     * 查询IPO(新股)申购记录列表
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return IPO(新股)申购记录集合
     */
    public List<IpoRecordVo> selectIpoRecordList(IpoRecordVo ipoRecord);

    /**
     * 新增IPO(新股)申购记录
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return 结果
     */
    public int insertIpoRecord(IpoRecord ipoRecord);

    /**
     * 修改IPO(新股)申购记录
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return 结果
     */
    public int updateIpoRecord(IpoRecord ipoRecord);

    /**
     * 批量删除IPO(新股)申购记录
     *
     * @param ids 需要删除的IPO(新股)申购记录主键集合
     * @return 结果
     */
    public int deleteIpoRecordByIds(List<Long> ids);

    /**
     * 删除IPO(新股)申购记录信息
     *
     * @param id IPO(新股)申购记录主键
     * @return 结果
     */
    public int deleteIpoRecordById(Long id);

    /**
     * 审批
     */
    public int examIpoRecord(IpoRecordDTO ipoRecord);

    /**
     * 划转
     */
    public AjaxResult transfer(Long id);

    /**
     * 转持仓
     */
    public AjaxResult traPos(Long id);

    /**
     * 退款
     */
    public AjaxResult refund(Long id);
}
