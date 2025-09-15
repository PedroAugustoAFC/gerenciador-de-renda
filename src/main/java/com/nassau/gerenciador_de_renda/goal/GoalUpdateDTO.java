package com.nassau.gerenciador_de_renda.goal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class GoalUpdateDTO {

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String description;

    @Min(value = 1, message = "O patrimônio líquido deve ser maior ou igual a zero")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "O valor alvo deve ser um número válido com até duas casas decimais")
    private double targetAmount;

    @NotBlank(message = "Data alvo não pode ser vazia")
    private String targetDate;

    public GoalUpdateDTO(){
    }

    public GoalUpdateDTO(Goal entity){
        this.description = entity.getDescription();
        this.targetAmount = entity.getTargetAmount();
        this.targetDate = entity.getTargetDate();
    }
}
