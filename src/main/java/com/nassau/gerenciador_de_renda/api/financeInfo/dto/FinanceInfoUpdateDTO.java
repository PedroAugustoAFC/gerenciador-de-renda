package com.nassau.gerenciador_de_renda.api.financeInfo.dto;

import com.nassau.gerenciador_de_renda.api.financeInfo.model.FinanceInfo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class FinanceInfoUpdateDTO {

    @DecimalMin(value = "0.01", inclusive = true, message = "Renda deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal income;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String profission;

    @DecimalMin(value = "0.01", inclusive = true, message = "Patrimônio líquido deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal netWorth;

    public FinanceInfoUpdateDTO() {
    }

    public FinanceInfoUpdateDTO(FinanceInfo entity) {
        this.income = entity.getIncome();
        this.profission = entity.getProfission();
        this.netWorth = entity.getNetWorth();
    }

}
