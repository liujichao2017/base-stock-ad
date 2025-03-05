package com.ruoyi.stock.stock.service;

import java.util.List;
import com.ruoyi.stock.stock.domain.ClosingDateConfig;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 节假日Service接口
 *
 * @author ruoyi
 * @date 2024-11-20
 */
public interface IClosingDateConfigService
{
    /**
     * 查询节假日
     *
     * @param id 节假日主键
     * @return 节假日
     */
    public ClosingDateConfig selectClosingDateConfigById(Long id);

    /**
     * 查询节假日列表
     *
     * @param closingDateConfig 节假日
     * @return 节假日集合
     */
    public List<ClosingDateConfig> selectClosingDateConfigList(ClosingDateConfig closingDateConfig);

    /**
     * 新增节假日
     *
     * @param closingDateConfig 节假日
     * @return 结果
     */
    public int insertClosingDateConfig(ClosingDateConfig closingDateConfig);

    /**
     * 修改节假日
     *
     * @param closingDateConfig 节假日
     * @return 结果
     */
    public int updateClosingDateConfig(ClosingDateConfig closingDateConfig);

    /**
     * 批量删除节假日
     *
     * @param ids 需要删除的节假日主键集合
     * @return 结果
     */
    public int deleteClosingDateConfigByIds(List<Long> ids);

    /**
     * 删除节假日信息
     *
     * @param id 节假日主键
     * @return 结果
     */
    public int deleteClosingDateConfigById(Long id);
}
