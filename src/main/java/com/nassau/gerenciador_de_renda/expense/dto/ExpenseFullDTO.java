package com.nassau.gerenciador_de_renda.expense.dto;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import lombok.Data;

@Data
public class ExpenseFullDTO {
    private Long id;
    private String description;
    private double amount;
    private String date;
    private String category;
    private String paymentMethod;
    private String familyMemberName;

    public ExpenseFullDTO() {
    }

    public ExpenseFullDTO(Expense entity){
        id = entity.getId();
        description = entity.getDescription();
        amount = entity.getAmount();
        date = entity.getDate();
        category = entity.getCategory();
        paymentMethod = entity.getPaymentMethod();
        familyMemberName = entity.getFamilyMemberName();
    }
}
