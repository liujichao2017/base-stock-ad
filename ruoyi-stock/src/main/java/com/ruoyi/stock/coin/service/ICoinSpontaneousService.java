package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinSpontaneous;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自发币Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinSpontaneousService
{
    /**
     * 查询自发币
     *
     * @param id 自发币主键
     * @return 自发币
     */
    public CoinSpontaneous selectCoinSpontaneousById(Long id);

    /**
     * 查询自发币列表
     *
     * @param coinSpontaneous 自发币
     * @return 自发币集合
     */
    public List<CoinSpontaneous> selectCoinSpontaneousList(CoinSpontaneous coinSpontaneous);

    /**
     * 新增自发币
     *
     * @param coinSpontaneous 自发币
     * @return 结果
     */
    public int insertCoinSpontaneous(CoinSpontaneous coinSpontaneous);

    /**
     * 修改自发币
     *
     * @param coinSpontaneous 自发币
     * @return 结果
     */
    public int updateCoinSpontaneous(CoinSpontaneous coinSpontaneous);

    /**
     * 批量删除自发币
     *
     * @param ids 需要删除的自发币主键集合
     * @return 结果
     */
    public int deleteCoinSpontaneousByIds(List<Long> ids);

    /**
     * 删除自发币信息
     *
     * @param id 自发币主键
     * @return 结果
     */
    public int deleteCoinSpontaneousById(Long id);
}
