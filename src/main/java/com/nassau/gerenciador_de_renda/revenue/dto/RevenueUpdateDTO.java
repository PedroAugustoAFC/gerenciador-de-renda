package com.nassau.gerenciador_de_renda.revenue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class RevenueUpdateDTO {
    @Column(length = 255)
    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "Valor deve ser maior que zero")
    private double amount;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
}
