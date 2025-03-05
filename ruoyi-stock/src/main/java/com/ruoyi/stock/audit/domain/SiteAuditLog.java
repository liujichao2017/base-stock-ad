package com.ruoyi.stock.audit.domain;

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
 * 客户操作对象 site_audit_log
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    private Long memberId;

    /**
     * 操作接口名称
     */
    @Excel(name = "操作接口名称")
    private String urlName;

    /**
     * 操作接口方法
     */
    @Excel(name = "操作接口方法")
    private String urlMethod;

    /**
     * 接口路径
     */
    @Excel(name = "接口路径")
    private String urlPath;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    private String reqBody;

    /**
     * 响应参数
     */
    @Excel(name = "响应参数")
    private String resBody;

    /**
     * ip地址
     */
    @Excel(name = "ip地址")
    private String addr;

    /**
     * 成功
     */
    @Excel(name = "成功")
    private String status;

    /**
     *耗时: 毫秒
     */
    private Long time;

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
