package com.nassau.gerenciador_de_renda.revenue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RevenueDTO {

    private Long id;
    private double amount;
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
    private String category;

    public RevenueDTO() {
    }

    public RevenueDTO(Revenue entity){
        id = entity.getId();
        amount = entity.getAmount();//Valor do pagamento
        dateCreated = entity.getDateCreated();
        datePaid = entity.getDatePaid();//Data em que o pagamento foi efetuado
    }
}
