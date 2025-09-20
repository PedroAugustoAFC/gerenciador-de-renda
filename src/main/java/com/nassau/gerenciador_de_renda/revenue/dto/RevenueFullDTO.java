package com.nassau.gerenciador_de_renda.revenue.dto;

import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import lombok.Data;

@Data
public class RevenueFullDTO {
    private Long id;
    private String description;
    private double amount;
    private String dateCreated;
    private String datePaid;
    private String category;
    private String paymentMethod;
    private String familyMemberName;

    public RevenueFullDTO() {
    }

    public RevenueFullDTO(Revenue entity){
        id = entity.getId();
        description = entity.getDescription();
        amount = entity.getAmount();
        dateCreated = entity.getDateCreated();
        datePaid = entity.getDatePaid();
    }
}
