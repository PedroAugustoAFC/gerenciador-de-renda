package com.nassau.gerenciador_de_renda.financeInfo.dto;

import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import lombok.Data;

@Data
public class FinanceInfoUpdateDTO {

    private Double income;
    private String profission;
    private Double netWorth;

    public FinanceInfoUpdateDTO() {
    }

    public FinanceInfoUpdateDTO(FinanceInfo entity) {
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.netWorth = entity.getNetWorth();
    }

}
