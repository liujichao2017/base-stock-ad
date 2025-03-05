package com.ruoyi.stock.config.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.config.domain.DataConfig;

/**
 * 通用配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface DataConfigMapper extends BaseMapper<DataConfig> {

    List<String> getGroup();
}
