package com.ruoyi.stock.coin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.coin.mapper.CoinSpontaneousRobotMapper;
import com.ruoyi.stock.coin.domain.CoinSpontaneousRobot;
import com.ruoyi.stock.coin.service.ICoinSpontaneousRobotService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 机器人Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Service
public class CoinSpontaneousRobotServiceImpl implements ICoinSpontaneousRobotService
{
    @Autowired
    private CoinSpontaneousRobotMapper coinSpontaneousRobotMapper;

    /**
     * 查询机器人
     *
     * @param id 机器人主键
     * @return 机器人
     */
    @Override
    public CoinSpontaneousRobot selectCoinSpontaneousRobotById(Long id) {
        return coinSpontaneousRobotMapper.selectById(id);
    }

    /**
     * 查询机器人列表
     *
     * @param coinSpontaneousRobot 机器人
     * @return 机器人
     */
    @Override
    public List<CoinSpontaneousRobot> selectCoinSpontaneousRobotList(CoinSpontaneousRobot coinSpontaneousRobot) {
        QueryWrapper<CoinSpontaneousRobot> ew = new QueryWrapper<>();
        ew.setEntity(coinSpontaneousRobot);
        ew.orderByDesc("create_time");
        return coinSpontaneousRobotMapper.selectList(ew);
    }

    /**
     * 新增机器人
     *
     * @param coinSpontaneousRobot 机器人
     * @return 结果
     */
    @Override
    public int insertCoinSpontaneousRobot(CoinSpontaneousRobot coinSpontaneousRobot)
    {
        coinSpontaneousRobot.setCreateTime(DateUtils.getNowDate());
        return coinSpontaneousRobotMapper.insert(coinSpontaneousRobot);
    }

    /**
     * 修改机器人
     *
     * @param coinSpontaneousRobot 机器人
     * @return 结果
     */
    @Override
    public int updateCoinSpontaneousRobot(CoinSpontaneousRobot coinSpontaneousRobot)
    {
        coinSpontaneousRobot.setUpdateTime(DateUtils.getNowDate());
        return coinSpontaneousRobotMapper.updateById(coinSpontaneousRobot);
    }

    /**
     * 批量删除机器人
     *
     * @param ids 需要删除的机器人主键
     * @return 结果
     */
    @Override
    public int deleteCoinSpontaneousRobotByIds(List<Long> ids)
    {
        QueryWrapper<CoinSpontaneousRobot> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return coinSpontaneousRobotMapper.delete(ew);
    }

    /**
     * 删除机器人信息
     *
     * @param id 机器人主键
     * @return 结果
     */
    @Override
    public int deleteCoinSpontaneousRobotById(Long id)
    {
        return coinSpontaneousRobotMapper.deleteById(id);
    }
}
