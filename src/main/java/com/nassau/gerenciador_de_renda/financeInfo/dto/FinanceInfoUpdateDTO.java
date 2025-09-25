package com.nassau.gerenciador_de_renda.financeInfo.dto;

import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class FinanceInfoUpdateDTO {

    @Min(value = 1, message = "Renda deve ser maior ou igual a zero")
    private Double income;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String profission;

    @Min(value = 1, message = "O patrimônio líquido deve ser maior ou igual a zero")
    private Double netWorth;

    public FinanceInfoUpdateDTO() {
    }

    public FinanceInfoUpdateDTO(FinanceInfo entity) {
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.netWorth = entity.getNetWorth();
    }

}
