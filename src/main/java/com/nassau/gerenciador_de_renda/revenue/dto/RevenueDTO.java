package com.nassau.gerenciador_de_renda.revenue.dto;

import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import lombok.Data;

@Data
public class RevenueDTO {

    private Long id;
    private double amount;
    private String dateCreated;
    private String datePaid;
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
