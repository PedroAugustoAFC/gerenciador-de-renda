package com.nassau.gerenciador_de_renda.financeInfo.dto;

import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class FinanceInfoUpdateDTO {

    @DecimalMin(value = "0.01", inclusive = true, message = "Renda deve ser maior ou igual a 0,01")
    private Double income;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String profission;

    @DecimalMin(value = "0.01", inclusive = true, message = "Patrimônio líquido deve ser maior ou igual a 0,01")
    private Double netWorth;

    public FinanceInfoUpdateDTO() {
    }

    public FinanceInfoUpdateDTO(FinanceInfo entity) {
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.netWorth = entity.getNetWorth();
    }

}
