package com.nassau.gerenciador_de_renda.expense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import com.nassau.gerenciador_de_renda.expense.model.categoryEnum.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpenseDTO {

    private Long id;
    private double amount;
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
    private ExpenseCategory category;

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
