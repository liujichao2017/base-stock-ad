package com.ruoyi.stock.wite.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWareHouseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String stockGid;
    private Integer mode;
    private Date buyOrderTim;
    private Long lever;
    private Long num;
    private BigDecimal buyOrderPrice;
    private String direction;
    private Long witeId;
}
