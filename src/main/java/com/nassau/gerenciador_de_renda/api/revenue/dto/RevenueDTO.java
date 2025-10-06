package com.nassau.gerenciador_de_renda.api.revenue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.api.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.api.revenue.model.categoryEnum.RevenueCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RevenueDTO {
    private UUID id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
    private RevenueCategory category;

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
