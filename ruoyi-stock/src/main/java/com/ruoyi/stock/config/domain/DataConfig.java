package com.ruoyi.stock.config.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用配置对象 data_config
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分组
     */
    @Excel(name = "分组")
    @TableField("`group`")
    private String group;

    /**
     * 键值
     */
    @Excel(name = "键值")
    @TableField("`key`")
    private String key;

    /**
     * 值类型
     */
    @Excel(name = "值类型")
    private String type;

    /**
     * 对值
     */
    @Excel(name = "对值")
    private String val;

    /**
     * 对值
     */
    @Excel(name = "备注")
    private String remark;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
