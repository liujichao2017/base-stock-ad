package com.ruoyi.stock.ai.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AiTradeVo {

    @NotEmpty
    private List<Long> aiRecordIds;

    @NotEmpty
    @Valid
    private List<StockInfo> stocks;


    @Data
    public static class StockInfo {
        @NotBlank
        private String stockGid;
        @NotNull
        private BigDecimal buyAmt;
        @NotNull
        private BigDecimal sellAmt;
        @NotNull
        private Long num;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @NotNull
        private Date buyTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @NotNull
        private Date sellTime;
    }

}
