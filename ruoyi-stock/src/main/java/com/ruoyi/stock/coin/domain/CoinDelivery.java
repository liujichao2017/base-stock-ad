package com.ruoyi.stock.coin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 虚拟币交割方案对象 coin_delivery
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinDelivery implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 交割方案名 */
    @Excel(name = "交割方案名")
    private String name;

    /** 时间单位 */
    @Excel(name = "时间单位")
    private String unit;

    /** 时间数 */
    @Excel(name = "时间数")
    private Long time;

    /** 启用 */
    @Excel(name = "启用")
    private Long status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
