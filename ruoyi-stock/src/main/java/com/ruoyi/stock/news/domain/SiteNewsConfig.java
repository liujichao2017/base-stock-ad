package com.ruoyi.stock.news.domain;

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
 * 新闻配置对象 site_news_config
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteNewsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 新闻主键id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 新闻源 */
    @Excel(name = "新闻源")
    private String source;

    /** 参数配置 */
    @Excel(name = "参数配置")
    private String json;

    /** 启用 */
    @Excel(name = "启用")
    private String status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
