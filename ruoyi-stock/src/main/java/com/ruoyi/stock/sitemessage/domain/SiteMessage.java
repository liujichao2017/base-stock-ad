package com.ruoyi.stock.sitemessage.domain;

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
 * 公告对象 site_message
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 值类型: richText(富文本),image(图片) */
    @Excel(name = "值类型: richText(富文本),image(图片)")
    private String type;
    @Excel(name = "标题")
    private String title;
    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** 通知 */
    @Excel(name = "通知")
    private Integer status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
