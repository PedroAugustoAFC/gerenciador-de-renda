package com.nassau.gerenciador_de_renda.api.goal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.api.goal.model.Goal;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class GoalUpdateDTO {

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String description;

    @DecimalMin(value = "0.01", inclusive = true, message = "O valor alvo deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor alvo deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal targetAmount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate targetDate;

    public GoalUpdateDTO(){
    }

    public GoalUpdateDTO(Goal entity){
        this.description = entity.getDescription();
        this.targetAmount = entity.getTargetAmount();
        this.targetDate = entity.getTargetDate();
    }
}
