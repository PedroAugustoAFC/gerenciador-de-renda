package com.nassau.gerenciador_de_renda.expense.dto;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import lombok.Data;

@Data
public class ExpenseDTO {

    private Long id;
    private double amount;
    private String date;
    private String category;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Expense entity){
        id = entity.getId();
        amount = entity.getAmount();//Valor do pagamento
        date = entity.getDate();//Data do pagamento
        category = entity.getCategory();//exemplo: eletronicos, alimentacao, saude, transporte, lazer, outros
    }
}
