package com.nassau.gerenciador_de_renda.expense.dto;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import lombok.Data;

@Data
public class ExpenseDTO {

    private Long id;
    private double amount;
    private String dateCreated;
    private String datePaid;
    private String category;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Expense entity){
        id = entity.getId();
        amount = entity.getAmount();//Valor do pagamento
        dateCreated = entity.getDateCreated();
        datePaid = entity.getDatePaid();//Data em que o pagamento foi efetuado
        category = entity.getCategory();//exemplo: eletronicos, alimentacao, saude, transporte, lazer, outros
    }
}
