package com.nassau.gerenciador_de_renda.financeInfo.dto;

import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;

import java.util.List;

public class FinanceInfoDTO {

    private Long id;
    private Double income;
    private String profission;
    private List<String> dependents;
    private Double netWorth;

    public FinanceInfoDTO() {
    }

    public FinanceInfoDTO(FinanceInfo entity) {
        this.id = entity.getId();
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.dependents = entity.getDependents();
        this.netWorth = entity.getNetWorth();
    }
}
