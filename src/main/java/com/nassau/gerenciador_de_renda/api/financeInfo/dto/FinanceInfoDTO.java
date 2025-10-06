package com.nassau.gerenciador_de_renda.api.financeInfo.dto;

import com.nassau.gerenciador_de_renda.api.financeInfo.model.FinanceInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class FinanceInfoDTO {

    private UUID id;
    private BigDecimal income;
    private String profission;
    private BigDecimal netWorth;

    public FinanceInfoDTO() {
    }

    public FinanceInfoDTO(FinanceInfo entity) {
        this.id = entity.getId();
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.netWorth = entity.getNetWorth();
    }
}
