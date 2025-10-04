package com.nassau.gerenciador_de_renda.expense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import com.nassau.gerenciador_de_renda.expense.model.categoryEnum.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExpenseDTO {
    private UUID id;
    private String description;
    private double amount;
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;
    private ExpenseCategory category;
    private String paymentMethod;
    private String payer;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Expense entity){
        id = entity.getId();
        description = entity.getDescription();
        amount = entity.getAmount();
        dateCreated = entity.getDateCreated();
        datePaid = entity.getDatePaid();
        category = entity.getCategory();
        paymentMethod = entity.getPaymentMethod();
        payer = entity.getPayer();
    }
}
