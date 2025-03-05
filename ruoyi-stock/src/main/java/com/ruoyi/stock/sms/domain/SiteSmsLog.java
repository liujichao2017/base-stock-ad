package com.ruoyi.stock.sms.domain;

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
 * 短信记录对象 site_sms_log
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSmsLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 验证码
     */
    @Excel(name = "验证码")
    private String code;

    /**
     * 短信内容
     */
    @Excel(name = "短信内容")
    private String context;

    /**
     * 接口响应
     */
    @Excel(name = "接口响应")
    private String resBody;

    /**
     * 发送状态
     */
    @Excel(name = "发送状态")
    private Long status;


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
