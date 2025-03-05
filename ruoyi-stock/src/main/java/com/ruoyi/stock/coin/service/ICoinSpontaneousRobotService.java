package com.ruoyi.stock.coin.service;

import java.util.List;
import com.ruoyi.stock.coin.domain.CoinSpontaneousRobot;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机器人Service接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface ICoinSpontaneousRobotService
{
    /**
     * 查询机器人
     *
     * @param id 机器人主键
     * @return 机器人
     */
    public CoinSpontaneousRobot selectCoinSpontaneousRobotById(Long id);

    /**
     * 查询机器人列表
     *
     * @param coinSpontaneousRobot 机器人
     * @return 机器人集合
     */
    public List<CoinSpontaneousRobot> selectCoinSpontaneousRobotList(CoinSpontaneousRobot coinSpontaneousRobot);

    /**
     * 新增机器人
     *
     * @param coinSpontaneousRobot 机器人
     * @return 结果
     */
    public int insertCoinSpontaneousRobot(CoinSpontaneousRobot coinSpontaneousRobot);

    /**
     * 修改机器人
     *
     * @param coinSpontaneousRobot 机器人
     * @return 结果
     */
    public int updateCoinSpontaneousRobot(CoinSpontaneousRobot coinSpontaneousRobot);

    /**
     * 批量删除机器人
     *
     * @param ids 需要删除的机器人主键集合
     * @return 结果
     */
    public int deleteCoinSpontaneousRobotByIds(List<Long> ids);

    /**
     * 删除机器人信息
     *
     * @param id 机器人主键
     * @return 结果
     */
    public int deleteCoinSpontaneousRobotById(Long id);
}
