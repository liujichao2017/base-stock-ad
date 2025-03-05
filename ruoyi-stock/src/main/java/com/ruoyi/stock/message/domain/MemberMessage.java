package com.ruoyi.stock.message.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户消息对象 member_message
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 消息类型: 1.弹窗, 2.消息列表
     */
    @Excel(name = "消息类型: 1.弹窗, 2.消息列表")
    private String type;

    @Excel(name = "标题")
    private String title;

    /**
     * 消息源: 1.系统, 2.新股
     */
    @Excel(name = "消息源: 1.系统, 2.新股")
    private BigDecimal source;

    /**
     * 消息内容
     */
    @Excel(name = "消息内容")
    private String content;

    /**
     * 阅读状态：1 未阅读，2 已阅读
     */
    @Excel(name = "阅读状态：1 未阅读，2 已阅读")
    private Integer readStatus;


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

    /**
     * 温馨提示
     */
    @Excel(name = "温馨提示")
    private String kindTips;

    /**
     * 推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pushTime;

    /**
     * 源记录id
     */
    private Long productId;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String accountType;

    @TableField(exist = false)
    private String memberRealName;

    @TableField(exist = false)
    @Sensitive
    private String memberPhone;
}
