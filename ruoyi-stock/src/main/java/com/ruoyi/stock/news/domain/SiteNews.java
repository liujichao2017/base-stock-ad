package com.ruoyi.stock.news.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻资讯对象 site_news
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteNews implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 新闻类型
     */
    @Excel(name = "新闻类型")
    private Long type;

    /**
     * 新闻标题
     */
    @Excel(name = "新闻标题")
    private String title;

    /**
     * 来源id
     */
    @Excel(name = "来源id")
    private String sourceId;

    /**
     * 新闻源
     */
    @Excel(name = "新闻源")
    private String source;

    /**
     * 浏览量
     */
    @Excel(name = "浏览量")
    private Long views;

    /**
     * 显示时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "显示时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date showTime;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String imgurl;

    /**
     * 新闻内容
     */
    @Excel(name = "新闻内容")
    private String content;

    /**
     * 展示
     */
    @Excel(name = "展示")
    private String status;


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
