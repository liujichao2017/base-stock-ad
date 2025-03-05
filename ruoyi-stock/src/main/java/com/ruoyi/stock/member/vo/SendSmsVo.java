package com.ruoyi.stock.member.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendSmsVo {

    private Long memberId;
    @NotBlank
    private String imitation;

    private String type;

    private Long smsId;

    private String phone;

}
