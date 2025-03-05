package com.ruoyi.stock.coin.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.coin.domain.CoinSpontaneousRobot;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 机器人Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-21
 */
public interface CoinSpontaneousRobotMapper extends BaseMapper<CoinSpontaneousRobot> {

    @Delete("delete from coin_spontaneous_robot where cs_id = #{csId}")
    int deleteByCsId(@Param("csId") Long csId);
}
