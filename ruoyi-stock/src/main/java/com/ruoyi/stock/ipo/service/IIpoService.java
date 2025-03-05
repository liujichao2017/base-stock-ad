package com.ruoyi.stock.ipo.service;

import java.util.List;
import com.ruoyi.stock.ipo.domain.Ipo;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * IPO(新股)Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IIpoService
{
    /**
     * 查询IPO(新股)
     *
     * @param id IPO(新股)主键
     * @return IPO(新股)
     */
    public Ipo selectIpoById(Long id);

    /**
     * 查询IPO(新股)列表
     *
     * @param ipo IPO(新股)
     * @return IPO(新股)集合
     */
    public List<Ipo> selectIpoList(Ipo ipo);

    /**
     * 新增IPO(新股)
     *
     * @param ipo IPO(新股)
     * @return 结果
     */
    public int insertIpo(Ipo ipo);

    /**
     * 修改IPO(新股)
     *
     * @param ipo IPO(新股)
     * @return 结果
     */
    public int updateIpo(Ipo ipo);

    /**
     * 批量删除IPO(新股)
     *
     * @param ids 需要删除的IPO(新股)主键集合
     * @return 结果
     */
    public int deleteIpoByIds(List<Long> ids);

    /**
     * 删除IPO(新股)信息
     *
     * @param id IPO(新股)主键
     * @return 结果
     */
    public int deleteIpoById(Long id);
}
