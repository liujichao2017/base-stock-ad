package com.ruoyi.stock.member.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import com.ruoyi.stock.member.vo.MemberFundsVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员对象 member
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @Sensitive
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String phone;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 提现密码
     */
    @Excel(name = "提现密码")
    private String withPwd;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realName;

    /**
     * 头像照片
     */
    @Excel(name = "头像照片")
    private String avatarImg;

    /**
     * 实盘
     */
    @Excel(name = "实盘")
    private String accountType;

    /**
     * 证件号
     */
    @Excel(name = "证件号")
    private String idCard;

    /**
     * 实名状态
     */
    @Excel(name = "实名状态")
    private String isActive;

    /**
     * 实名状态
     */
    @Excel(name = "视频认证状态: 0 未认证, 1: 已认证")
    private String videoActive;

    /**
     * 实名不通过,原因
     */
    @Excel(name = "实名不通过,原因")
    private String activeMsg;

    /**
     * 备用, 证件照片
     */
    @Excel(name = "备用, 证件照片")
    private String img1Key;

    /**
     * 备用, 证件照片
     */
    @Excel(name = "备用, 证件照片")
    private String img2Key;

    /**
     * 备用, 证件照片
     */
    @Excel(name = "备用, 证件照片")
    private String img3Key;

    /**
     * 允许登录
     */
    @Excel(name = "允许登录")
    private String isLogin;

    /**
     * 股票交易
     */
    @Excel(name = "股票交易")
    private String isStock;

    /**
     * 杠杆交易
     */
    @Excel(name = "杠杆交易")
    private String isLever;

    /**
     * 杠杆权限
     */
    @Excel(name = "杠杆权限")
    private String levelItem;

    /**
     * 贷款额度
     */
    @Excel(name = "贷款额度")
    private BigDecimal loanAmt;


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

    @TableField(exist = false)
    private MemberBank bank;

    @TableField(exist = false)
    private List<MemberFundsVo> funds;

    @TableField(exist = false)
    private String userName;
}
