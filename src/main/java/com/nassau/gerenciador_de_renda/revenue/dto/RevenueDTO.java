package com.nassau.gerenciador_de_renda.revenue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RevenueDTO {
    private Long id;
    private String description;
    private double amount;
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
    private String category;
    private String paymentMethod;
    private String familyMemberName;

    public RevenueDTO() {
    }

    public RevenueDTO(Revenue entity){
        id = entity.getId();
        description = entity.getDescription();
        amount = entity.getAmount();
        dateCreated = entity.getDateCreated();
        datePaid = entity.getDatePaid();
    }
}
