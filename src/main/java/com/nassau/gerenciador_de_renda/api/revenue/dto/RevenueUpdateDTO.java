package com.nassau.gerenciador_de_renda.api.revenue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.api.revenue.model.Revenue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RevenueUpdateDTO {

    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal amount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;

    public RevenueUpdateDTO(){
    }

    public RevenueUpdateDTO(Revenue entity){
        this.description = entity.getDescription();
        this.amount = entity.getAmount();
        this.datePaid = entity.getDatePaid();
    }
}
